
package com.maywide.biz.inter.service;

import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.entity.Rule;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.core.servlet.SpringBeanUtil;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.manager.UserTargetManager;
import com.maywide.biz.inter.pojo.bizGridData.*;
import com.maywide.biz.inter.pojo.dataReport.GridInfoResp;
import com.maywide.biz.inter.pojo.dataReport.TargetListReq;
import com.maywide.biz.inter.pojo.dataReport.TargetListResp;
import com.maywide.biz.inter.pojo.queAchievementData.AchievementData;
import com.maywide.biz.inter.pojo.queAchievementData.QueAchievementDataReq;
import com.maywide.biz.inter.pojo.queAchievementData.QueAchievementDataResp;
import com.maywide.biz.inter.pojo.queCLnum.*;
import com.maywide.biz.inter.pojo.queCityServiceTag.CityServiceTag;
import com.maywide.biz.inter.pojo.queCityServiceTag.QueCityServiceTagReq;
import com.maywide.biz.inter.pojo.queCityServiceTag.QueCityServiceTagResp;
import com.maywide.biz.inter.pojo.queCityTimeList.CityServiceTimeTag;
import com.maywide.biz.inter.pojo.queCityTimeList.QueCityTimeListReq;
import com.maywide.biz.inter.pojo.queCityTimeList.QueCityTimeListResp;
import com.maywide.biz.inter.pojo.queCustExpiriList.CustExpiriBean;
import com.maywide.biz.inter.pojo.queCustExpiriList.QueCustExpiriListReq;
import com.maywide.biz.inter.pojo.queCustExpiriList.QueCustExpiriListResp;
import com.maywide.biz.inter.pojo.queCustExpiringList.CustExpiriTag;
import com.maywide.biz.inter.pojo.queCustExpiringList.QueCustExpiringListReq;
import com.maywide.biz.inter.pojo.queCustExpiringList.QueCustExpiringListResp;
import com.maywide.biz.inter.pojo.queIncomeTag.QueIncomeTagResp;
import com.maywide.biz.inter.pojo.queIncomeTag.TagMonth;
import com.maywide.biz.inter.pojo.queIncomeTagList.IncomeTag;
import com.maywide.biz.inter.pojo.queIncomeTagList.QueIncomeTagListReq;
import com.maywide.biz.inter.pojo.queIncomeTagList.QueIncomeTagListResp;
import com.maywide.biz.inter.pojo.queSatis.QueSatisDetailResp;
import com.maywide.biz.inter.pojo.queSatis.QueSatisResp;
import com.maywide.biz.inter.pojo.queSatis.SatisInfo;
import com.maywide.biz.inter.pojo.queSatis.SatisMonthDetailInfo;
import com.maywide.biz.inter.pojo.queSystemMonth.QueSystemMonthResp;
import com.maywide.biz.inter.pojo.queTargetDetail.QueTargetDetailReq;
import com.maywide.biz.inter.pojo.queTargetDetail.QueTargetDetailResp;
import com.maywide.biz.inter.pojo.reportDetail.ReportDetailReq;
import com.maywide.biz.inter.pojo.reportDetail.ReportDetailResp;
import com.maywide.biz.manage.targer.entity.SysKpiConfig;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.dao.support.Page;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.CheckUtils;
import com.maywide.core.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Map.Entry;

@Service
public class InterPrdDataService extends CommonService {

	@Autowired
	ParamService paramService;

	@Autowired
	RuleService ruleService;

	@Autowired
	private SalaryCentsService salaryCentsService;

	private static final String VALID_LEADER = "S0100201202100";

	private static final String BROADBAND = "S0100201300000";

	private static final String HD_TWOWAY = "高清双向";

	private static final String iMantissa = "1";

	private static final String oMantissa = "2";

	private static final String aMantissa = "3";

	public ReturnInfo queGridList(ArrayList<GridType> grids) throws Exception {
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		List params = new ArrayList();
		params.add(loginInfo.getCity());
		params.add(loginInfo.getOperid());
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT DISTINCT (A.GRIDCODE) grid,A.GRIDNAME gridName");
		sb.append("		FROM BIZ_GRID_MANAGER_FS A");
		sb.append("		WHERE A.CITY = ?");
		sb.append("		AND A.OPERID = ?");
		List<GridType> datas = getDAO().find(sb.toString(), GridType.class, params.toArray());
		if (datas != null && !datas.isEmpty()) {
			grids.addAll(datas);
		}
		return returnInfo;
	}

	public ReturnInfo queExponetList(QueGridDataListReq req, ArrayList<QueGridDataResp> resp) throws Exception {
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		CheckUtils.checkEmpty(req.getGrid(), "网格id不能为空");

		List params = new ArrayList();
		params.add(loginInfo.getCity());

		StringBuffer sb = new StringBuffer();
		sb.append(
				"		SELECT DISTINCT (W.STADATE) stadate FROM DM.DM_CMMS_RPT W WHERE W.CITY = ? order by W.Stadate desc");

		List<TmpClass> times = SpringBeanUtil.getPersistentService().find(sb.toString(), TmpClass.class,
				params.toArray());
		if (times == null || times.isEmpty()) {
			throw new BusinessException("查询不到当前网格日期数据");
		}
		sb = new StringBuffer();
		params.clear();
		params.add(times.get(0).getStadate());
		params.add(req.getGrid());
		params.add(loginInfo.getCity());

		sb.append("		SELECT A.KPIID KPIID,A.CITY CITY,A.DATE_TYPE DTYPE,A.STADATE STADATE,");
		sb.append("		A.AREAID AREAID,A.GRID GRID,A.STAVALUE STAVALUE");
		sb.append("		FROM DM.DM_CMMS_RPT  A");
		sb.append("		WHERE 1 = 1");
		sb.append("		AND A.STADATE = ?");
		sb.append("		AND A.GRID = ?");
		sb.append("		AND A.CITY = ?");
		sb.append("		ORDER BY A.STADATE DESC");

		List<QueGridListChildResp> childs = SpringBeanUtil.getPersistentService().find(sb.toString(),
				QueGridListChildResp.class, params.toArray());
		if (childs != null && !childs.isEmpty()) {
			handler(resp, childs, getDataType());
		}
		return returnInfo;
	}

	private void handler(ArrayList<QueGridDataResp> resp, List<QueGridListChildResp> childs, List<DataType> types) {
		List<QueGridListChildResp> leaders = new ArrayList<QueGridListChildResp>();
		List<QueGridListChildResp> broadbands = new ArrayList<QueGridListChildResp>();
		List<QueGridListChildResp> hds = new ArrayList<QueGridListChildResp>();
		for (QueGridListChildResp child : childs) {
			if (child.getKpiId().contains(VALID_LEADER)) {
				leaders.add(child);
			} else if (child.getKpiId().contains(BROADBAND)) {
				broadbands.add(child);
			} else if (child.getKpiId().contains(HD_TWOWAY)) {
				hds.add(child);
			}
		}
		handlerTmpData(leaders, VALID_LEADER);
		handlerTmpData(broadbands, BROADBAND);
		handlerTmpData(hds, HD_TWOWAY);
		addNameToData(leaders, types);
		addNameToData(broadbands, types);
		addNameToData(hds, types);
		handler(resp, leaders);
		handler(resp, broadbands);
		handler(resp, hds);
	}

	private void addNameToData(List<QueGridListChildResp> childs, List<DataType> types) {

		if (!types.isEmpty() && !childs.isEmpty()) {
			for (QueGridListChildResp child : childs) {
				for (DataType type : types) {
					if (child.getKpiId().equals(type.getKpiid())) {
						child.setName(type.getKpiName());
						break;
					}
				}
			}
		}
	}

	private void handlerTmpData(List<QueGridListChildResp> childs, String groupTitle) {

		if (childs.isEmpty())
			return;

		if (childs.size() >= 4)
			return;

		QueGridListChildResp increaseTmp = null;

		QueGridListChildResp decreasingTmp = null;

		QueGridListChildResp netTmp = null;

		if (childs.size() < 4) {
			for (QueGridListChildResp tmp : childs) {
				String kpiid = tmp.getKpiId();
				if (kpiid.equals(groupTitle + iMantissa)) {
					increaseTmp = tmp;
				} else if (kpiid.equals(groupTitle + oMantissa)) {
					decreasingTmp = tmp;
				} else if (kpiid.equals(groupTitle + aMantissa)) {
					netTmp = tmp;
				}
			}
		}

		if (increaseTmp == null) {
			increaseTmp = copyQueGridResp(childs.get(0), groupTitle + iMantissa);
			childs.add(increaseTmp);
		}
		if (decreasingTmp == null) {
			decreasingTmp = copyQueGridResp(childs.get(0), groupTitle + oMantissa);
			childs.add(decreasingTmp);
		}
		// 该变量需要另外计算值
		if (netTmp == null) {
			netTmp = copyQueGridResp(childs.get(0), groupTitle + aMantissa);
			netTmp.setStaValue(increaseTmp.getStaValue() - decreasingTmp.getStaValue());
			childs.add(netTmp);
		}
	}

	private QueGridListChildResp copyQueGridResp(QueGridListChildResp resource, String kpiid) {
		QueGridListChildResp tmp = new QueGridListChildResp();
		tmp.setAreaid(resource.getAreaid());
		tmp.setCity(resource.getCity());
		tmp.setdType(resource.getdType());
		tmp.setGrid(resource.getGrid());
		tmp.setKpiId(kpiid);
		tmp.setStadate(resource.getStadate());
		tmp.setStaValue(0l);
		return tmp;
	}

	private void handler(ArrayList<QueGridDataResp> resp, List<QueGridListChildResp> childs) {

		if (!childs.isEmpty()) {
			QueGridDataResp data = new QueGridDataResp();
			data.setDatas(childs);
			resp.add(data);
		}
	}

	private List<DataType> getDataType() throws Exception {
		List<DataType> datas = new ArrayList<DataType>();
		List<PrvSysparam> params = paramService.getData("DATA_CODE");
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				PrvSysparam sysparam = params.get(i);
				DataType dataType = new DataType();
				dataType.setKpiid(sysparam.getData());
				dataType.setKpiName(sysparam.getMname());
				datas.add(dataType);
			}
		}
		return datas;
	}

	/**
	 * 获取网格日期维度接口
	 * 
	 * @param resps
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queGridDateType(ArrayList<DataType> resps) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		PrvSysparam sysparam = new PrvSysparam();
		sysparam.setGcode("DATA_TYPE");
		sysparam.setMcode(loginInfo.getCity());
		List<PrvSysparam> datas = paramService.getData(sysparam);
		if (datas != null && !datas.isEmpty()) {
			sysparam = datas.get(0);
			String data = sysparam.getData();
			handlerDateType(resps, data);
		}
		return returnInfo;
	}

	private void handlerDateType(ArrayList<DataType> resps, String data) {
		String[] types = data.split(";");
		for (int i = 0; i < types.length; i++) {
			String[] content = types[i].split("~");
			DataType type = new DataType();
			type.setKpiid(content[0]);
			type.setKpiName(content[1]);
			resps.add(type);
		}
	}

	/**
	 * 查看网格信息详情接口
	 * 
	 * @param req
	 * @param resps
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queDetailGridInfo(GridDetailReq req, ArrayList<GridDetailResp> resps) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req.getKpiids(), "请求类型不能为空");
		CheckUtils.checkEmpty(req.getGridId(), "网格id不能为空");
		CheckUtils.checkEmpty(req.getStime(), "请求开始时间段不能为空");
		CheckUtils.checkEmpty(req.getEtime(), "请求结束时间段不能为空");

		List params = new ArrayList();
		params.add(req.getGridId());
		if (StringUtils.isNotBlank(req.getTimeType())) {
			params.add(req.getTimeType());
		}
		params.add(req.getStime());
		params.add(req.getEtime());

		String group = "";
		if (req.getKpiids().get(0).contains(BROADBAND)) {
			group = BROADBAND;
		} else if (req.getKpiids().get(0).contains(HD_TWOWAY)) {
			group = HD_TWOWAY;
		} else {
			group = VALID_LEADER;
		}

		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT * FROM (");
		sb.append("		SELECT A.STADATE stime,A.KPIID kpiid,");
		sb.append("		A.GRID gridid,A.STAVALUE staValue");
		sb.append("		FROM DM.DM_CMMS_RPT  A");
		sb.append("		WHERE 1 = 1 ");
		sb.append("		AND A.GRID = ? ");
		if (StringUtils.isNotBlank(req.getTimeType())) {
			sb.append("		AND A.DATE_TYPE = ?");
		}
		sb.append("		AND A.STADATE BETWEEN ? AND ?");
		sb.append("		AND  A.KPIID IN (");
		for (int i = 0; i < req.getKpiids().size(); i++) {
			sb.append("	?");
			params.add(req.getKpiids().get(i));
			if (i != req.getKpiids().size() - 1) {
				sb.append(",");
			}
		}
		sb.append("		)");
		sb.append("		AND A.CITY = ?");
		sb.append(" 	ORDER BY A.STADATE");
		sb.append("		) v");
		params.add(loginInfo.getCity());
		String sql = "SELECT B.GRIDNAME gridName FROM BIZ_GRID_INFO B where 1 = 1 AND B.GRIDCODE = ?";
		List<GridDetailListResp> names = getDAO().find(sql, GridDetailListResp.class, req.getGridId());
		List<GridDetailListResp> datas = SpringBeanUtil.getPersistentService().find(sb.toString(),
				GridDetailListResp.class, params.toArray());
		if (names == null || names.isEmpty()) {
			throw new BusinessException("查询不到当前网格名称");
		}
		String name = names.get(0).getGridName();
		for (GridDetailListResp data : datas) {
			data.setGridName(name);
		}
		if (datas != null && !datas.isEmpty()) {
			handlerData(datas, resps, getDataType(), group);
		}

		return returnInfo;
	}

	private GridDetailListResp copyGridResp(GridDetailListResp data, String group, String mantissa) {
		GridDetailListResp tmp = new GridDetailListResp();

		tmp.setGridId(data.getGridId());
		tmp.setGridName(data.getGridName());
		tmp.setKpiid(group + mantissa);
		tmp.setStime(data.getStime());
		tmp.setStaValue(0l);

		return tmp;
	}

	private void handlerData(List<GridDetailListResp> datas, ArrayList<GridDetailResp> resps, List<DataType> dateTypes,
			String group) throws Exception {

		Map<Long, List<GridDetailListResp>> dataMap = new HashMap<Long, List<GridDetailListResp>>();
		for (int i = 0; i < datas.size(); i++) {
			GridDetailListResp child = datas.get(i);
			List<GridDetailListResp> detailes = dataMap.get(child.getStime());
			if (detailes == null) {
				detailes = new ArrayList<GridDetailListResp>();
				detailes.add(child);
				dataMap.put(child.getStime(), detailes);
			} else {
				detailes.add(child);
			}
		}
		handlerDetailData(dataMap, dateTypes, group);

		Iterator<Entry<Long, List<GridDetailListResp>>> iterator = dataMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Long, List<GridDetailListResp>> entry = iterator.next();
			GridDetailResp resp = new GridDetailResp();
			resp.setTime(entry.getKey());
			resp.setList(entry.getValue());
			for (int i = 0; i < entry.getValue().size(); i++) {
				GridDetailListResp tmp = entry.getValue().get(i);
				if (StringUtils.isBlank(tmp.getGridName())) {
					log.error(tmp.getGridId());
				}
			}
			resps.add(resp);
		}
		Collections.sort(resps, comparator);
	}

	private void handlerDetailData(Map<Long, List<GridDetailListResp>> dataMap, List<DataType> dateTypes,
			String group) {
		Iterator<Entry<Long, List<GridDetailListResp>>> iterator = dataMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Long, List<GridDetailListResp>> entry = iterator.next();
			handlerList(entry.getValue(), dateTypes, group);
		}
	}

	private void handlerList(List<GridDetailListResp> datas, List<DataType> dateTypes, String group) {

		GridDetailListResp increaseTmp = null;

		GridDetailListResp decreasingTmp = null;

		GridDetailListResp netTmp = null;

		for (GridDetailListResp resp : datas) {
			if (resp.getKpiid().endsWith(aMantissa)) {
				netTmp = resp;
			} else if (resp.getKpiid().endsWith(iMantissa)) {
				increaseTmp = resp;
			} else if (resp.getKpiid().endsWith(oMantissa)) {
				decreasingTmp = resp;
			}
		}
		if (increaseTmp == null) {
			increaseTmp = copyGridResp(datas.get(0), group, iMantissa);
			datas.add(increaseTmp);
		}
		if (decreasingTmp == null) {
			decreasingTmp = copyGridResp(datas.get(0), group, oMantissa);
			datas.add(decreasingTmp);
		}
		// 需要另外计算
		if (netTmp == null) {
			netTmp = copyGridResp(datas.get(0), group, aMantissa);
			netTmp.setStaValue(increaseTmp.getStaValue() - decreasingTmp.getStaValue());
			datas.add(netTmp);
		}

		for (GridDetailListResp child : datas) {
			for (DataType dataType : dateTypes) {
				if (child.getKpiid().equals(dataType.getKpiid())) {
					child.setKpiidName(dataType.getKpiName());
					break;
				}
			}
		}
	}

	private Comparator<GridDetailResp> comparator = new Comparator<GridDetailResp>() {

		@Override
		public int compare(GridDetailResp lhs, GridDetailResp rhs) {
			if (lhs.getTime() > rhs.getTime()) {
				return 1;
			}
			if (lhs.getTime() < rhs.getTime()) {
				return -1;
			}
			return 0;
		}

	};

	/**
	 * 获取中山试点操作员默认网格
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queZsMainGridDataInfo(GridInfoResp resp) throws Exception {
		ReturnInfo returnInfo = initReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		BizGridInfo grid = salaryCentsService.getGrids();
		resp.setGridId(grid.getId());
		resp.setGridCode(grid.getGridcode());
		resp.setGridName(grid.getGridname());
		resp.setgType(String.valueOf(grid.getGtype()));
		return returnInfo;
	}

	/**
	 * 获取当前操作员网格信息
	 * 
	 * @param resps
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queGridDataInfo(ArrayList<GridInfoResp> resps) throws Exception {
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		//Rule rule = ruleService.getRule("*", "SPE_GRID_CITY");
		boolean exist = false;
//		if (rule != null) {
//			String[] citys = rule.getValue().split(",");
//			for (String str : citys) {
//				if (loginInfo.getCity().equalsIgnoreCase(str)) {
//					exist = true;
//					break;
//				}
//			}
//		}
		List params = new ArrayList();
		StringBuffer sb = new StringBuffer();
		if (exist) {
			String tableName = "biz_grid_manager_" + loginInfo.getCity().toLowerCase();
			sb.append("		SELECT  T.GRIDCODE gridCode,T.GRIDNAME gridName FROM ");
			sb.append(tableName);
			sb.append("		T");
			sb.append("		WHERE 1 = 1 AND");
			sb.append("		OPERID = ? and");
			sb.append("		CITY = ?");
		} else {
			sb.append("		SELECT A.GRIDID gridId, A.GRIDCODE gridCode,A.GRIDNAME gridName,A.GTYPE gType");
			sb.append("		FROM BIZ_GRID_INFO A ,BIZ_GRID_MANAGER B");
			sb.append("		WHERE 1 = 1 AND ");
			sb.append("		A.GRIDID = B.GRIDID AND ");
			sb.append("		B.OPERID = ? AND");
			sb.append("		A.CITY = ? ");
		}
		params.add(loginInfo.getOperid());
		params.add(loginInfo.getCity());
		if (!exist) {
			sb.append("		AND ( A.GTYPE = ? ");
			sb.append("		OR A.GTYPE = ? )");
			params.add(BizConstant.BizGridObjObjtype.PATCH);
			params.add(BizConstant.BizGridObjObjtype.ADDR);
		}
		List<GridInfoResp> datas = getDAO().find(sb.toString(), GridInfoResp.class, params.toArray());
		Set<GridInfoResp> tmpDatas = new HashSet<GridInfoResp>(datas);
		if (datas == null || datas.isEmpty())
			CheckUtils.checkNull(null, "查询不到当前操作员网格信息");
		if (!exist) {
			hanlderManagerGrid(tmpDatas, loginInfo);
		}
		resps.addAll(tmpDatas);
		return returnInfo;
	}

	private void hanlderManagerGrid(Set<GridInfoResp> datas, LoginInfo loginInfo) throws Exception {
		Set<GridInfoResp> mgDatas = new HashSet<GridInfoResp>();
		for (GridInfoResp child : datas) {
			if (child.getgType().equals(BizConstant.BizGridObjObjtype.PATCH)) {
				mgDatas.add(child);
			}
		}
		if (mgDatas.size() > 0) {
			Set<GridInfoResp> childDatas = new HashSet<GridInfoResp>();
			for (GridInfoResp child : mgDatas) {
				BizGridInfo bizInfo = new BizGridInfo();
				bizInfo.setCity(loginInfo.getCity());
				bizInfo.setPrevid(child.getGridId());
				List<BizGridInfo> mChilds = getDAO().find(bizInfo);
				if (mChilds != null && !mChilds.isEmpty()) {
					childDatas.addAll(getGridInfoResp4BizGridInfo(mChilds));
					hanlderManagerGrid(childDatas, loginInfo);
				}
			}
			datas.addAll(childDatas);
		}
		datas.removeAll(mgDatas);
	}

	private List<GridInfoResp> getGridInfoResp4BizGridInfo(List<BizGridInfo> mChilds) {
		List<GridInfoResp> datas = new ArrayList<GridInfoResp>();
		for (BizGridInfo info : mChilds) {
			GridInfoResp resp = new GridInfoResp();
			resp.setGridCode(info.getGridcode());
			resp.setGridId(info.getId());
			resp.setGridName(info.getGridname());
			resp.setgType(Long.toString(info.getGtype()));
			datas.add(resp);
		}
		return datas;
	}

	/**
	 * 展示当前最新数据下不同数据指标列表(用户类指标)
	 * 
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queTargetDataTypeList(TargetListReq req, TargetListResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getGridCode(), "当前网格编码不能为空");
		CheckUtils.checkEmpty(req.getDate(), "日期纬度不能为空");

		Date nowdate = new Date();
		Date date = null;
		String format = "";
		// 这里如果取日纬度 则取前一天的 月纬度则取上一个月的
		if (req.getDate().equalsIgnoreCase("M")) {
			date = getPreMonth(nowdate);
			format = DateUtils.FORMAT_YYYYMM;
		} else {
			date = getPreDay(nowdate);
			format = DateUtils.FORMAT_YYYYMMDD;
		}

		String time = DateUtils.formatDate(date, format);

		UserTargetManager.getInstance().queUserKpis(loginInfo.getCity(), req.getGridCode(), req.getDate(), time,
				req.getKpiType(), getDAO(), resp);

		resp.setTime(time);
		
		return returnInfo;
	}

	private Date getPreMonth(Date date) {
		Calendar curlCalendar = Calendar.getInstance();
		curlCalendar.setTime(date);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, curlCalendar.get(Calendar.YEAR));
		calendar.set(Calendar.MONTH, curlCalendar.get(Calendar.MONTH) - 1);
		return calendar.getTime();
	}

	private Date getPreDay(Date date) {
		Calendar curlCalendar = Calendar.getInstance();
		curlCalendar.setTime(date);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, curlCalendar.get(Calendar.YEAR));
		calendar.set(Calendar.MONTH, curlCalendar.get(Calendar.MONTH));
		calendar.set(Calendar.DAY_OF_MONTH, curlCalendar.get(Calendar.DAY_OF_MONTH) - 1);
		return calendar.getTime();
	}

	/**
	 * 查询周期段内网格报表数据列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queReportDetailInfo(ReportDetailReq req, ReportDetailResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getStartTime(), "开始时间不能为空");
		CheckUtils.checkEmpty(req.getEndTime(), "结束时间不能为空");
		CheckUtils.checkEmpty(req.getGridCode(), "网格编码不能为空");
		CheckUtils.checkEmpty(req.getFlag(), "指标类型不能为空");
		CheckUtils.checkEmpty(req.getDate(), "日期纬度格式不能为空");
		UserTargetManager.getInstance().queUserTagDetailList(loginInfo, req.getGridCode(), req.getStartTime(),
				req.getEndTime(), req.getFlag(), req.getDate(), resp);
		return returnInfo;
	}

	public ReturnInfo queDetailInfo(QueTargetDetailReq req, QueTargetDetailResp resp) throws Exception {

		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getFlag(), "查询的指标类型不能为空");
		CheckUtils.checkEmpty(req.getGridcode(), "网格信息不能为空");
		CheckUtils.checkEmpty(req.getDateStr(), "日期信息不能为空");

		UserTargetManager.getInstance().queAddLossDetailInfo(loginInfo.getCity(), req.getDateStr(), req.getGridcode(),
				resp, req.getFlag(), req.getType(), req.getIndex(), req.getPageSize(), DAO);

		return returnInfo;
	}

	/**
	 * 查询最新的满意度列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queSatisList(TargetListReq req, QueSatisResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getGridCode(), "当前网格编码不能为空");
		CheckUtils.checkEmpty(req.getDate(), "日期纬度不能为空");

		Date nowdate = new Date();
		Date date = null;
		String format = "";
		String dimension = "";
		if (req.getDate().equalsIgnoreCase("M")) {
			date = getPreMonth(nowdate);
			format = DateUtils.FORMAT_YYYYMM;
			dimension = "2";
		} else {
			date = getPreDay(nowdate);
			format = DateUtils.FORMAT_YYYYMMDD;
			dimension = "0";
		}

		String time = DateUtils.formatDate(date, format);

		List params = new ArrayList();
		params.add(time);
		params.add(req.getGridCode());
		params.add(loginInfo.getCity());
		params.add(dimension);

		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT etl.code2name(A.CITY,'PRV_CITY')CITY, A.WHGRIDCODE gridCode,");
		sb.append("		A.WHGRIDNAME,A.Flag flag,etl.code2name(A.FLAG,'REPORT_GRIDRATE') flagName,");
		sb.append("		DECODE(SUM(A.ANUMS),0,0,");
		sb.append("		ROUND(100*SUM(A.NUMS)/SUM(A.ANUMS),2)) scale");
		sb.append("		FROM NEDS.TW2_GRID_RATE A");
		sb.append("		 WHERE  A.TMONTH = ? AND");
		sb.append("		A.WHGRIDCODE = ? AND");
		sb.append("		A.CITY =  ? AND");
		sb.append("		A.STATIDIMENSION = ?");
		sb.append("		GROUP BY A.CITY,A.FLAG, A.WHGRIDCODE,A.WHGRIDNAME");
		sb.append("		ORDER BY A.CITY, A.WHGRIDCODE,A.WHGRIDNAME,A.Flag");
		List<SatisInfo> datas = SpringBeanUtil.getDBPersistentService().find(sb.toString(), SatisInfo.class,
				params.toArray());
		if (datas == null || datas.isEmpty())
			CheckUtils.checkNull(null, "暂无当前网格的最新数据");
		resp.setTime(time);
		params.clear();

		if (req.getDate().equalsIgnoreCase("M")) {
			date = getPreMonth(getPreMonth(nowdate));
		} else {
			date = getPreDay(getPreDay(nowdate));
		}
		time = DateUtils.formatDate(date, format);
		params.add(time);
		params.add(req.getGridCode());
		params.add(loginInfo.getCity());
		params.add(dimension);
		List<SatisInfo> predatas = SpringBeanUtil.getDBPersistentService().find(sb.toString(), SatisInfo.class,
				params.toArray());
		if (predatas != null && !predatas.isEmpty()) {
			for (SatisInfo target : datas) {
				for (SatisInfo preTarget : predatas) {
					if (target.getFlag().equals(preTarget.getFlag())) {
						target.setMargin(target.getScale() - preTarget.getScale());
						break;
					}
				}
			}
		}

		resp.setDatas(datas);
		return returnInfo;
	}

	/**
	 * 查询某个时间段内满意度率信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queSatisDetailInfo(ReportDetailReq req, QueSatisDetailResp resp) throws Exception {

		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getStartTime(), "开始时间不能为空");
		CheckUtils.checkEmpty(req.getEndTime(), "结束时间不能为空");
		CheckUtils.checkEmpty(req.getGridCode(), "网格编码不能为空");
		CheckUtils.checkEmpty(req.getFlag(), "类型不能为空");

		List params = new ArrayList();
		params.add(req.getStartTime());
		params.add(req.getEndTime());
		params.add(req.getGridCode());
		params.add(req.getFlag());
		params.add(loginInfo.getCity());
		String dimension = "";
		if (req.getDate().equals("M")) {
			dimension = "2";
		} else {
			dimension = "0";
		}
		params.add(dimension);
		StringBuffer sb = new StringBuffer();
		sb.append(
				"		SELECT etl.code2name(A.CITY,'PRV_CITY')CITY, A.WHGRIDCODE gridCode,A.WHGRIDNAME,A.Flag flag,A.Tmonth month,");
		sb.append("		etl.code2name(A.FLAG,'REPORT_GRIDRATE') flagName,");
		sb.append("		DECODE(SUM(A.ANUMS),0,0,");
		sb.append("		ROUND(100*SUM(A.NUMS)/SUM(A.ANUMS),2)) scale");
		sb.append("		FROM NEDS.TW2_GRID_RATE A WHERE");
		sb.append("		A.TMONTH>= ? AND");
		sb.append("		A.TMONTH<= ? AND");
		sb.append("		A.WHGRIDCODE = ? AND");
		sb.append("		A.Flag = ? AND");
		sb.append("		A.CITY = ? AND");
		sb.append("		A.STATIDIMENSION = ?");
		sb.append("		GROUP BY A.CITY,A.FLAG, A.WHGRIDCODE,A.WHGRIDNAME,A.TMONTH");
		sb.append("		ORDER BY A.CITY, A.WHGRIDCODE,A.WHGRIDNAME,A.Flag,A.TMONTH");

		List<SatisMonthDetailInfo> datas = SpringBeanUtil.getDBPersistentService().find(sb.toString(),
				SatisMonthDetailInfo.class, params.toArray());
		if (datas == null || datas.isEmpty())
			CheckUtils.checkNull(null, "当前时间段内暂无数据信息");
		resp.setDatas(datas);
		return returnInfo;
	}

	/**
	 * 查询各个指标数量
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queLetterControlNum(QueControlNumReq req, QueControlResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getGridCode(), "查询网格不能为空");

		Rule rule = ruleService.getRule(loginInfo.getCity(), "CITY_CRECALL_TIME");
		String[] gridcodes = req.getGridCode().split(",");
		List params = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append(
				"		SELECT A.OPTYPE opType, ETL.CODE2NAME(A.CITY,'PRV_CITY') city,etl.code2name(a.optype,'SYS_CONTYPE') typeName,A.WHGRIDCODE gridCode,A.TJDATE tjDate,sum(A.SUMS) nums");
		sql.append("		FROM NEDS.TW1_WG_CRECALL_DG A ");
		sql.append("		WHERE A.WHGRIDCODE in (");
		for(int i = 0 ;i <gridcodes.length ;i++){
			sql.append("?");
			params.add(gridcodes[i]);
			if(i != gridcodes.length -1){
				sql.append(",");
			}
		}
		sql.append(")");
		sql.append("		AND A.CITY = ?");
		params.add(loginInfo.getCity());
		if(rule != null && StringUtils.isNotBlank(rule.getValue())){
			sql.append("		AND A.TJDATE >= ?");
			Date date = DateUtils.getBeforeNumberDate(Integer.parseInt(rule.getValue()));
			params.add(DateUtils.formatDate(date, DateUtils.FORMAT_YYYYMM));
		}else{
			sql.append("		AND A.TJDATE = (");
			sql.append("		SELECT MAX(T.TJDATE) FROM NEDS.TW1_WG_CRECALL_DG T");
			sql.append("		)");
		}
		
		sql.append("		GROUP BY A.TJDATE,A.OPTYPE,A.CITY,A.WHGRIDCODE");
		sql.append("		ORDER BY A.TJDATE desc,A.OPTYPE,A.CITY,A.WHGRIDCODE");

		List<QueControlBean> datas = SpringBeanUtil.getPersistentService().find(sql.toString(), QueControlBean.class,
				params.toArray());
		if (datas != null && !datas.isEmpty()) {
			resp.setDatas(datas);
		}
		return returnInfo;
	}
	
	/**
	 * 获取各个信控里的月份
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queControlMonth(QueControlMonthReq req, QueControlMonthResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getGridCode(), "当前查询的网格不能为空");
		CheckUtils.checkEmpty(req.getOpType(), "当前查询的信控类型不能为空");

		Rule rule = ruleService.getRule(loginInfo.getCity(), "CITY_CRECALL_TIME");
		
		List params = new ArrayList();
		params.add(req.getGridCode());
		params.add(loginInfo.getCity());
		params.add(req.getOpType());
		
		if(rule != null && StringUtils.isNotBlank(rule.getValue())){
			Date date = DateUtils.getBeforeNumberDate(Integer.parseInt(rule.getValue()));
			params.add(DateUtils.formatDate(date, "yyyyMM"));
		}
		
		String sqlStr = getQuerySql(loginInfo.getCity(), req.getOpType(), rule);
		
		List<ControlMonthBean> datas = SpringBeanUtil.getPersistentService().find(sqlStr, ControlMonthBean.class,
				params.toArray());
		handlerTimeCondition(loginInfo.getCity(),datas);
		if (datas != null && !datas.isEmpty()) {
			resp.setDatas(datas);
		}
		return returnInfo;
	}
	
	
	private void handlerTimeCondition(String city,List<ControlMonthBean> datas) throws Exception{
		if(datas == null || datas.isEmpty()) return;
		Rule rule = ruleService.getRule(city, "CONTR_LETTER_DATE");
		if(rule == null || StringUtils.isBlank(rule.getValue())) return;
		try {
			List<ControlMonthBean> tmp = new ArrayList<ControlMonthBean>();
			int num = Integer.parseInt(rule.getValue());
			String month = DateUtils.getPreNMonth(-num, new Date(), DateUtils.FORMAT_YYYYMM);
			int monNum = Integer.parseInt(month);
			for(ControlMonthBean bean:datas){
				int beanNum = Integer.parseInt(bean.getOpMonth());
				if(monNum  > beanNum){
					tmp.add(bean);
				}
			}
			datas.removeAll(tmp);
		} catch (Exception e) {
		}
	}
	
	private String getQuerySql(String city,String optype,Rule rule) throws Exception{
		String timeStr = "";
		if("0".equals(optype)){
			timeStr = "A.SMONTH";
		}else{
			timeStr = "A.OPMONTH";
		}
		StringBuffer sql = new StringBuffer();
		sql.append("		SELECT A.OPTYPE opType,A.WHGRIDCODE gridCode,");
		sql.append(timeStr+" opMonth");
		sql.append("		FROM NEDS.TW1_WG_CRECALL_DG A ");
		sql.append("		WHERE A.WHGRIDCODE = ?");
		sql.append("		AND A.CITY = ?");
		sql.append("		AND A.OPTYPE = ?");
		
		if(rule != null && StringUtils.isNotBlank(rule.getValue())){
			sql.append("	AND "+timeStr+">=?");
		}
		sql.append("		GROUP BY A.OPTYPE,A.WHGRIDCODE,"+timeStr);
		sql.append("		ORDER BY A.OPTYPE,A.WHGRIDCODE ASC,"+timeStr+" desc");
		return sql.toString();
	}
	

	/**
	 * 查询信控月份里的客户详情
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queLetterControlDetail(QueControlDetailReq req, QueControlDetailResp resp) throws Exception {

		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getGridCode(), "当前查询的网格不能为空");
		CheckUtils.checkEmpty(req.getOpType(), "当前查询的信控类型不能为空");
		CheckUtils.checkEmpty(req.getOpMonth(), "当前查询的信控月份不能为空");

		List params = new ArrayList();

		StringBuffer sql = new StringBuffer();
		sql.append(
				"		select distinct(a.custid) custid ,a.optype opType,etl.code2name(a.optype,'SYS_CONTYPE') typeName,a.whgridcode gridCode,a.opmonth opMonth,");
		sql.append("		a.custname,a.fees,a.linkphone,a.linkaddr,a.markno,a.sums,a.zjsum,a.fjsum ");
		sql.append("		from NEDS.TW1_WG_CRECALL_DG a ");
		sql.append("		where a.whgridcode = ?");
		sql.append("		and a.city = ?");
		sql.append("		and a.optype = ?");
		sql.append("		and a.opmonth = ?");
		sql.append("		AND a.TJDATE = (SELECT MAX(T.TJDATE) FROM NEDS.TW1_WG_CRECALL_DG T) ");
		sql.append("		AND a.custtype = 0 "); // 个人客户
		sql.append("		order by ");
		sql.append("a.sums desc,");
		sql.append("a.optype,a.opmonth,a.whgridcode,a.fees,a.custname,a.custid,a.linkphone,a.linkaddr,a.markno");
		params.add(req.getGridCode());
		params.add(loginInfo.getCity());
		params.add(req.getOpType());
		params.add(req.getOpMonth());

		Page page = new Page();
		int size = StringUtils.isBlank(req.getPageSize()) ? 10 : Integer.parseInt(req.getPageSize());
		if (StringUtils.isNotBlank(req.getCurrentPage())) {
			page.setPageNo(Integer.parseInt(req.getCurrentPage()));
		}
		page.setPageSize(size);
		String sqlStr = sql.toString();
		if ("0".equals(req.getOpType())) { // 在用欠费用户
			sqlStr = sqlStr.replace("a.opmonth", "a.smonth");
		}
		page = SpringBeanUtil.getPersistentService().find(page, sqlStr, LetterControlDetailBean.class,
				params.toArray());
		List<LetterControlDetailBean> datas = page.getResult();
		if (datas != null && !datas.isEmpty()) {
			resp.setDatas(datas);
		}
		return returnInfo;
	}

	// ================================================================

	/**
	 * 查询月份列表
	 * 
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queIncomeMonth(QueIncomeTagResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		StringBuffer sb = new StringBuffer();
		sb.append(
				"		select distinct(a.STAMONTH) tagMonth from EDS.TW_BANKFEE_MONTH_GRID_ZS_V a order by a.STAMONTH desc");
		List<TagMonth> months = SpringBeanUtil.getPersistentService().find(sb.toString(), TagMonth.class);
		resp.setMonthList(months);
		return returnInfo;
	}

	/**
	 * 查询中山收入类指标列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queIncomeTypeTagList(QueIncomeTagListReq req, QueIncomeTagListResp resp) throws Exception {

		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		SysKpiConfig config = new SysKpiConfig();
		config.setCity(loginInfo.getCity());
		config.setIsShow("Y");
		config.setKpiType("3");

		List<SysKpiConfig> configs = getDAO().find(config);
		if (configs == null || configs.isEmpty()) {
			CheckUtils.checkNull(null, "查询不到该地市的特色指标");
		}
		config = configs.get(0);

		List params = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append("		select distinct(a.STAMONTH) tagMonth,a.AREAID areaid,a.GRID gridCode,a.PERCENTS percents");
		sb.append("		from EDS.TW_BANKFEE_MONTH_GRID_ZS_V a");
		sb.append("		where a.GRID = ?");
		params.add(req.getGridCode());
		if (StringUtils.isNotBlank(req.getStartTime()) && StringUtils.isNotBlank(req.getEndTime())) {
			sb.append("		and a.STAMONTH >= ?");
			params.add(req.getStartTime());
			sb.append("		and a.STAMONTH <= ?");
			params.add(req.getEndTime());

		} else {
			sb.append("		and a.STAMONTH = ?");
			params.add(req.getMonth());
		}
		sb.append("		order by a.STAMONTH desc");
		List<IncomeTag> datas = SpringBeanUtil.getPersistentService().find(sb.toString(), IncomeTag.class,
				params.toArray());
		if (datas == null || datas.isEmpty()) {
			CheckUtils.checkNull(null, "查询不到当前网格或月份数据");
		}
		for (IncomeTag tag : datas) {
			tag.setTagCode(config.getKpiCode());
			tag.setTagName(config.getKpiName());
		}
		resp.setDatas(datas);
		return returnInfo;
	}

	/**
	 * 查询中山特色服务类指标列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queCityServiceTag(QueCityServiceTagReq req, QueCityServiceTagResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		List params = new ArrayList();

		StringBuffer sb = new StringBuffer();
		sb.append("		select '安装工单及时率' flagName,to_char(a.ONTIMERATE) flagValue,a.ORG gridCode,a.GRIDNAME gridName");
		sb.append("		,a.STAMONTH, 'ONTIMERATE' flagCode,'DM.TM_REPFAULT_MONTH_CMMS_V' tabName,'安装单' flagType");
		sb.append("		from DM.TM_REPFAULT_MONTH_CMMS_V a");
		sb.append("		where a.ORG = ?");
		params.add(req.getGridCode());
		sb.append("		and a.STAMONTH = ?");
		params.add(req.getMonth());
		sb.append("		and a.ISHITCHNAME = '安装单'");
		sb.append("		union all");

		sb.append(
				"		select '安装工单处理时长' flagName,to_char(a.INSTALL_AVG_TIME_DAY) flagValue,a.ORG gridCode,a.GRIDNAME gridName");
		sb.append(
				"		,a.STAMONTH, 'INSTALL_AVG_TIME_DAY' flagCode,'DM.TM_REPFAULT_MONTH_CMMS_V' tabName,'安装单' flagType");
		sb.append("		from DM.TM_REPFAULT_MONTH_CMMS_V a");
		sb.append("		where a.ORG = ?");
		params.add(req.getGridCode());
		sb.append("		and a.STAMONTH = ?");
		params.add(req.getMonth());
		sb.append("		and a.ISHITCHNAME = '安装单'");
		sb.append("		union all");

		sb.append("		select '故障单及时率' flagName,to_char(a.ONTIMERATE) flagValue,a.ORG gridCode,a.GRIDNAME gridName");
		sb.append("		,a.STAMONTH, 'ONTIMERATE' flagCode,'DM.TM_REPFAULT_MONTH_CMMS_V' tabName,'故障单' flagType");
		sb.append("		from DM.TM_REPFAULT_MONTH_CMMS_V a");
		sb.append("		where a.ORG = ?");
		params.add(req.getGridCode());
		sb.append("		and a.STAMONTH = ?");
		params.add(req.getMonth());
		sb.append("		and a.ISHITCHNAME = '故障单'");
		sb.append("		union all");

		sb.append("		select '重复故障数' flagName,to_char(a.REPEATNUMS) flagValue,a.ORG gridCode,a.GRIDNAME gridName");
		sb.append("		,a.STAMONTH, 'REPEATNUMS' flagCode,'DM.TM_REPFAULT_MONTH_CMMS_V' tabName,'故障单' flagType");
		sb.append("		from DM.TM_REPFAULT_MONTH_CMMS_V a");
		sb.append("		where a.ORG = ?");
		params.add(req.getGridCode());
		sb.append("		and a.STAMONTH = ?");
		params.add(req.getMonth());
		sb.append("		and a.ISHITCHNAME = '故障单'");
		sb.append("		union all");

		sb.append(
				"		select '重复故障率' flagName,to_char(a.REPEATNUM_RATE) flagValue,a.ORG gridCode,a.GRIDNAME gridName");
		sb.append("		,a.STAMONTH, 'REPEATNUM_RATE' flagCode,'DM.TM_REPFAULT_MONTH_CMMS_V' tabName,'故障单' flagType");
		sb.append("		from DM.TM_REPFAULT_MONTH_CMMS_V a");
		sb.append("		where a.ORG = ?");
		params.add(req.getGridCode());
		sb.append("		and a.STAMONTH = ?");
		params.add(req.getMonth());
		sb.append("		and a.ISHITCHNAME = '故障单'");
		sb.append("		union all");

		sb.append(
				"		select '户均故障工单处理时长' flagName,to_char(a.AVGFOPTTIME) flagValue,a.GRIDCODE gridCode,a.gridname gridName");
		sb.append("		,a.STAMTH staMonth, 'AVGFOPTTIME' flagCode,'DM.TM_GRID_SERV_KPI_CMMS_V' tabName,'1' flagType");
		sb.append("		from DM.TM_GRID_SERV_KPI_CMMS_V a");
		sb.append("		where a.GRIDCODE = ?");
		params.add(req.getGridCode());
		sb.append("		and a.STAMTH = ?");
		params.add(req.getMonth());
		sb.append("		union all");

		sb.append(
				"		select '网格故障率' flagName,to_char(a.FAULTRATE) flagValue,a.GRIDCODE gridCode,a.GRIDNAME gridName");
		sb.append("		,a.STAMTH staMonth, 'FAULTRATE' flagCode,'DM.TM_GRID_SERV_KPI_CMMS_V' tabName,'1' flagType");
		sb.append("		from DM.TM_GRID_SERV_KPI_CMMS_V a");
		sb.append("		where a.GRIDCODE = ?");
		params.add(req.getGridCode());
		sb.append("		and a.STAMTH = ?");
		params.add(req.getMonth());
		sb.append("		union all");

		sb.append(
				"		select '装维工单满意度' flagName,to_char(a.SATISFYRATE ) flagValue,a.GRIDCODE gridCode,a.GRIDNAME gridName");
		sb.append("		,a.STAMTH staMonth, 'SATISFYRATE ' flagCode,'DM.TM_GRID_SERV_KPI_CMMS_V' tabName,'1' flagType");
		sb.append("		from DM.TM_GRID_SERV_KPI_CMMS_V a");
		sb.append("		where a.GRIDCODE = ?");
		params.add(req.getGridCode());
		sb.append("		and a.STAMTH = ?");
		params.add(req.getMonth());
		sb.append("		union all");

		sb.append(
				"		select '新装用户网络达标率' flagName,to_char(b.NEW_STAND_RATE) flagValue,b.GRIDCODE gridCode,b.GRIDNAME gridName,b.STAMONTH ");
		sb.append("		,'NEW_STAND_RATE' flagCode,'DM.TM_GRID_NET_TRANSFER_CMMS_V' tabName,'' flagType");
		sb.append("		from DM.TM_GRID_NET_TRANSFER_CMMS_V b");
		sb.append("		where b.gridCode = ?");
		params.add(req.getGridCode());
		sb.append("		and b.STAMONTH = ?");
		params.add(req.getMonth());

		List<CityServiceTag> datas = SpringBeanUtil.getPersistentService().find(sb.toString(), CityServiceTag.class,
				params.toArray());
		if (datas == null || datas.isEmpty()) {
			CheckUtils.checkNull(null, "查询不到当前网格或月份数据");
		}
		resp.setDatas(datas);
		return returnInfo;
	}

	/**
	 * 查询中山特色服务指标周期内指标列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queCityServiceTagMonthList(QueCityTimeListReq req, QueCityTimeListResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getTabName(), "需要查询的指标不能为空");
		CheckUtils.checkEmpty(req.getFlagCode(), "需要查询的指标不能为空");

		List params = new ArrayList();
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isNotBlank(req.getFlagType())) {
			try {
				Integer.parseInt(req.getFlagType());
				sb.append("		select a.STAMTH staMonth,a.GRIDNAME gridName,a." + req.getFlagCode() + " flagValue");
				sb.append(" 	from " + req.getTabName() + " a");
				sb.append("		where a.STAMTH >= ?");
				params.add(req.getStartTime());
				sb.append("		and a.STAMTH <= ?");
				params.add(req.getEndTime());
				sb.append("		and a.GRIDCODE = ?");
				params.add(req.getGridCode());
			} catch (Exception e) {
				sb.append("		select a.STAMONTH staMonth,a.GRIDNAME gridName,a." + req.getFlagCode() + " flagValue");
				sb.append(" 	from " + req.getTabName() + " a");
				sb.append("		where a.STAMONTH >= ?");
				params.add(req.getStartTime());
				sb.append("		and a.STAMONTH <= ?");
				params.add(req.getEndTime());
				sb.append("		and a.ORG = ?");
				params.add(req.getGridCode());
				sb.append("		and a.ISHITCHNAME = ?");
				params.add(req.getFlagType());
			}
		} else {
			sb.append("		select a.STAMONTH staMonth,a.GRIDNAME gridName,a." + req.getFlagCode() + " flagValue");
			sb.append(" 	from " + req.getTabName() + " a");
			sb.append("		where a.STAMONTH >= ?");
			params.add(req.getStartTime());
			sb.append("		and a.STAMONTH <= ?");
			params.add(req.getEndTime());
			sb.append("		and a.GRIDCODE = ?");
			params.add(req.getGridCode());
		}

		List<CityServiceTimeTag> datas = SpringBeanUtil.getPersistentService().find(sb.toString(),
				CityServiceTimeTag.class, params.toArray());
		if (datas == null || datas.isEmpty()) {
			CheckUtils.checkNull(null, "查询不到当前网格或月份数据");
		}
		resp.setDatas(datas);

		return returnInfo;
	}

	/**
	 * 流失预警数据详情
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queCustExpiriList(QueCustExpiriListReq req, QueCustExpiriListResp resp) throws Exception {
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkEmpty(req.getGridcodes(), "网格编码不能为空");
		CheckUtils.checkEmpty(req.getTagid(), "属性标签不能为空");
		fillData(req,resp,loginInfo.getCity());
		return returnInfo;
	}
	
	
	private void fillData(QueCustExpiriListReq req,QueCustExpiriListResp resp,String city) throws Exception{
		
		List params = new ArrayList();
		params.add(req.getTagid());
		params.add(city);
		String[] gridStrs = req.getGridcodes().split(",");
		StringBuffer sb = new StringBuffer();
		sb.append("	SELECT DISTINCT(A.CUSTID) CUSTID,A.NAME CUSTNAME,A.WHLADDR LINKADDR,A.MOBILE MOBILE,");
		sb.append("	A.PID PID,A.SERVID SERVID,A.HOUSEID HOUSEID,A.PERMARK PERMARK,A.SALESID SALESID,");
		sb.append("	A.SALESNAME SALESNAME,A.ARREAR_FEES ARREARFEES,A.WHGRIDNAME GRIDNAME");
		sb.append("	FROM CMMS_CRE_EARLY_WARN A");
		sb.append("	where a.type =?");
		sb.append("	and city = ?");
		sb.append("	and a.whgridcode in(");
		for(int i = 0;i <gridStrs.length;i++){
			sb.append("?");
			params.add(gridStrs[i]);
			if(i != gridStrs.length -1){
				sb.append(",");
			}
		}
		sb.append("	)");
		sb.append("	group by a.custid,a.name,a.whladdr,a.mobile,a.pid,a.servid,a.houseid,a.permark,a.salesid,a.salesname,a.arrear_fees,a.whgridname");
		sb.append("	order by a.custid,a.name,a.whladdr,a.mobile,a.pid,a.servid,a.houseid,a.permark,a.salesid,a.salesname,a.arrear_fees,a.whgridname");
		
		Page page = new Page();
		page.setPageNo(req.getCurrentpager());
		page.setPageSize(req.getPagercount());
		page = SpringBeanUtil.getDBPersistentService().find(page, sb.toString(), CustExpiriBean.class, params.toArray());
		resp.setDatas(page.getResult());
		resp.setPageSize(page.getPageSize());
		resp.setCunpager(page.getCntPageNo());
		resp.setTotalcount(page.getTotalCount());
	}

	/**
	 * 查询流失预警标签页面各种指数
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queCustExpiringList(QueCustExpiringListReq req, QueCustExpiringListResp resp) throws Exception {
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();

		CheckUtils.checkEmpty(req.getGridcodes(), "网格编码不能为空");
		String[] gridStrs = req.getGridcodes().split(",");
		
		List params = new ArrayList();
		params.add(loginInfo.getCity());
		StringBuffer sb = new StringBuffer();
		sb.append("	select a.type tagid,count(distinct(a.custid)) custcount");
		sb.append("	from cmms_cre_early_warn a");
		sb.append("	where a.city = ?");
		sb.append("	and a.whgridcode in (");
		for (int i = 0; i < gridStrs.length; i++) {
			sb.append("?");
			params.add(gridStrs[i]);
			if(i != gridStrs.length -1){
				sb.append(",");
			}
		}
		sb.append("	)");
		sb.append("	group by a.type");
		sb.append("	order by a.type");

		List<CustExpiriTag> datas = SpringBeanUtil.getDBPersistentService().find(sb.toString(), CustExpiriTag.class,
				params.toArray());
		List<PrvSysparam> sysparams = paramService.getData("CUST_EXPIRI_PRESS");
		if(sysparams != null && !sysparams.isEmpty()){
			for(CustExpiriTag tag:datas){
				for(PrvSysparam sysparam:sysparams){
					if(sysparam.getMcode().equals(tag.getTagId())){
						tag.setTagName(sysparam.getMname());
						break;
					}
				}
			}
		}
		resp.setDatas(datas);
		addLetterInfo(loginInfo.getCity(),req,resp);
		return returnInfo;
	}
	
	private void addLetterInfo(String city,QueCustExpiringListReq req,QueCustExpiringListResp resp) throws Exception{
		Rule rule = ruleService.getRule(city, "LETTER_OPERN_RULE");
		if(rule != null){
			QueControlNumReq req2 = new QueControlNumReq(req.getGridcodes());
			QueControlResp resp2 = new QueControlResp();
			queLetterControlNum(req2, resp2);
			resp.setQueDatas(resp2.getDatas());
		}
	}
	
	
	/**
	 * 查询绩效指标月份
	 * @param resps
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queSystemMonth(ArrayList<QueSystemMonthResp> resps) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT DATE_FORMAT(CYCLENUM,'%Y%m') value,");
		sb.append("		DATE_FORMAT(CYCLENUM,'%Y年%m月') currentName,");
		sb.append("		DATE_FORMAT(CYCLENUM,'%Y年%m月') name");
		sb.append("		FROM ass_target_togrid ");
		sb.append("		WHERE CITY = ?");
		sb.append("		GROUP BY CYCLENUM");
		sb.append("		ORDER BY CYCLENUM DESC");
		List<QueSystemMonthResp> datas = getDAO().find(sb.toString(),QueSystemMonthResp.class, loginInfo.getCity());
		resps.addAll(datas);
		return returnInfo;
	}
	
	
	
	/**
	 * 省网绩效指标列表查询
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queAchievementData(QueAchievementDataReq req,QueAchievementDataResp resp) throws Exception{
		
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		CheckUtils.checkEmpty(req.getGridCode(), "网格编号不能为空");
		CheckUtils.checkEmpty(req.getDate(), "日期信息不能为空");
		
		Page page = new Page();
		page.setPageNo(req.getCurrentPage());
		page.setPageSize(req.getPageSize());
		
		List params = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append("		select a.ASSID id, a.ASSNUM,a.CURRENT_VALUE,");
		sb.append("		 CASE WHEN a.ASSNUM_UNIT IS NULL THEN a.ASSNUM ELSE CONCAT(a.ASSNUM, a.ASSNUM_UNIT) END AS targetValue,");
		sb.append("		CASE WHEN a.ASSNUM_UNIT IS NULL THEN a.CURRENT_VALUE ELSE CONCAT(a.CURRENT_VALUE, a.ASSNUM_UNIT) END AS currentValue,");
		sb.append("		 ROUND((a.CURRENT_VALUE - a.ASSNUM),2) dropValue,");
		sb.append("		a.final_grade grade,b.NAME NAME,a.KPI_DATE kpidate");
		sb.append("		FROM");
		sb.append("		ass_target_togrid a");
		sb.append("		INNER JOIN ass_target_store b");
		sb.append("		ON a.ASSID = b.ASSID");
		sb.append("		AND (a.CITY = ? or b.city = '*')");
		params.add(loginInfo.getCity());
		sb.append("		AND a.gridid = ");
		sb.append("		(SELECT t.gridid FROM biz_grid_info t WHERE t.gridcode = ?)");
		params.add(req.getGridCode());
		sb.append("		AND DATE_FORMAT(a.CYCLENUM,'%Y%m') = ?");
		params.add(req.getDate());
		sb.append("		AND a.STATUS = 1");
		page = getDAO().find(page, sb.toString(), AchievementData.class, params.toArray());
		resp.setCurrentPage(page.getPageNo());
		resp.setPageSize(page.getPageSize());
		handlerDateFormart(page.getResult());
		resp.setDatas(page.getResult());
		return returnInfo;
		
	}

	
	private void handlerDateFormart(List<AchievementData> datas){
		if(datas == null || datas.isEmpty()) return;
		for(AchievementData data:datas){
			if(StringUtils.isNotBlank(data.getKpidate())){
				Date date = DateUtils.parseDate(data.getKpidate(), DateUtils.FORMAT_YYYYMMDD_FORMATER);
				String formartDate = DateUtils.getFormatDateString(date, DateUtils.DEFAULT_DATE_FORMAT);
				data.setKpidate(formartDate);
			}
		}
	}
}
