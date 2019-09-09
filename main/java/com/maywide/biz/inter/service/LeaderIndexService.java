package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.core.servlet.SpringBeanUtil;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.dataReport.DataTarget;
import com.maywide.biz.inter.pojo.dataReport.TargetListResp;
import com.maywide.biz.inter.pojo.queAchievementData.AchievementData;
import com.maywide.biz.inter.pojo.queAchievementData.QueAchievementDataReq;
import com.maywide.biz.inter.pojo.queIncomeTagList.IncomeTag;
import com.maywide.biz.inter.pojo.queLeaderIncomeList.QueLeaderIncomeListReq;
import com.maywide.biz.inter.pojo.queLeaderIncomeList.QueLeaderIncomeListResp;
import com.maywide.biz.inter.pojo.queLeaderSatisList.QueLeaderSatisListReq;
import com.maywide.biz.inter.pojo.queLeaderStatisIndexList.LeaderStatistIndex;
import com.maywide.biz.inter.pojo.queLeaderTargetDataTypeList.QueLeaderTargetDataTypeListReq;
import com.maywide.biz.inter.pojo.queLeaderUserStatistKpiIndexList.QueLeaderUserStatistKpiIndexListReq;
import com.maywide.biz.inter.pojo.queSatis.QueSatisResp;
import com.maywide.biz.inter.pojo.queSatis.SatisInfo;
import com.maywide.biz.manage.targer.entity.SysKpiConfig;
import com.maywide.core.util.CheckUtils;
import com.maywide.core.util.DateUtils;

@Service
public class LeaderIndexService extends CommonService {

	public ReturnInfo queIncomeIndexList(ArrayList<SysKpiConfig> datas) throws Exception {
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		SysKpiConfig config = new SysKpiConfig();
		config.setCity(loginInfo.getCity());
		config.setIsShow("Y");
		config.setKpiType("3");

		List<SysKpiConfig> configs = getDAO().find(config);
		if(null == configs || configs.isEmpty()) {
		CheckUtils.checkNull(null, "当前地市未配置收入类指标项列表!");
		}
		datas.addAll(configs);
		return returnInfo;
	}
	
	public ReturnInfo queLeaderIncomeList(QueLeaderIncomeListReq req, QueLeaderIncomeListResp resp) throws Exception {

		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = getLoginInfo();
		CheckUtils.checkEmpty(req.getGridCodes(),"网格编码不能为空!");
		SysKpiConfig config = new SysKpiConfig();
		config.setCity(loginInfo.getCity());
		config.setIsShow("Y");
		config.setKpiType("3");
		String[] gridcodeList = req.getGridCodes().split(",");
		List<SysKpiConfig> configs = getDAO().find(config);
		if (configs == null || configs.isEmpty()) {
			CheckUtils.checkNull(null, "查询不到该地市的特色指标");
		}
		config = configs.get(0);

		List params = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append("		select distinct(a.STAMONTH) tagMonth,a.AREAID areaid,a.GRID gridCode,a.PERCENTS percents");
		sb.append("		from EDS.TW_BANKFEE_MONTH_GRID_ZS_V a");
		sb.append("		where 1 = 1");
		sb.append("		and a.GRID in (");
		for(int i = 0; i < gridcodeList.length;i++) {
			sb.append("?");
			params.add(gridcodeList[i]);
			if(i != gridcodeList.length-1) {
				sb.append(",");
			}
		}
		sb.append(")");
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
		if (datas == null ||datas.isEmpty()) {
			CheckUtils.checkNull(null, "查询不到当前网格或月份数据");
		}
		for (IncomeTag tag : datas) {
			tag.setGridName(getGridName(tag.getGridCode(), loginInfo.getCity()));
			tag.setTagCode(config.getKpiCode());
			tag.setTagName(config.getKpiName());
		}
		resp.setDatas(datas);
		return returnInfo;
	}

	
	private String getGridName(String gridcode,String city) throws Exception {
		BizGridInfo gridInfo = new BizGridInfo();
		gridInfo.setGridcode(gridcode);
		gridInfo.setCity(city);
		List<BizGridInfo> gridInfoList = getDAO().find(gridInfo);
		if(null == gridInfoList || gridInfoList.isEmpty()) {
			return gridcode;
		}
		gridInfo = gridInfoList.get(0);
		return StringUtils.isNotBlank(gridInfo.getGridname())?gridInfo.getGridname():gridcode;
	}
	
	
	public ReturnInfo queLeaderStatisIndexList(ArrayList<LeaderStatistIndex> datas) throws Exception {
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		
		StringBuffer sql = new StringBuffer();
		sql.append("		SELECT distinct(A.Flag) kpiCode,etl.code2name(A.FLAG,'REPORT_GRIDRATE') kpiName");
		sql.append("		FROM NEDS.TW2_GRID_RATE A");
		sql.append("		WHERE A.CITY = ?");
		
		List<LeaderStatistIndex> indexList = SpringBeanUtil.getDBPersistentService()
										.find(sql.toString(), LeaderStatistIndex.class, loginInfo.getCity());
		if(null == indexList || indexList.isEmpty()) {
			CheckUtils.checkNull(null, "当前地市没有配置用户类指标");
		}
		datas.addAll(indexList);
		return returnInfo;
	}
	
	public ReturnInfo queLeaderSatisList(QueLeaderSatisListReq req,QueSatisResp resp) throws Exception {
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getGridCode(), "网格编码不能为空");
		CheckUtils.checkEmpty(req.getDate(), "日期纬度不能为空");
		CheckUtils.checkEmpty(req.getFlag(),"指标类型不能为空");
		String[] gridcodes = req.getGridCode().split(",");
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
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT etl.code2name(A.CITY,'PRV_CITY')CITY, A.WHGRIDCODE gridCode,");
		sb.append("		A.WHGRIDNAME gridName,A.Flag flag,etl.code2name(A.FLAG,'REPORT_GRIDRATE') flagName,");
		sb.append("		DECODE(SUM(A.ANUMS),0,0,");
		sb.append("		ROUND(100*SUM(A.NUMS)/SUM(A.ANUMS),2)) scale");
		sb.append("		FROM NEDS.TW2_GRID_RATE A");
		sb.append("		WHERE  A.TMONTH = ? ");
		params.add(time);
		sb.append("		AND A.Flag = ?");
		params.add(req.getFlag());
		sb.append("		AND A.WHGRIDCODE in ( ");
		for(int i = 0;i < gridcodes.length; i++) {
			sb.append("?");
			params.add(gridcodes[i]);
			if(i != gridcodes.length-1) {
				sb.append(",");
			}
		}
		sb.append(")");
		sb.append("		AND A.CITY =  ? ");
		params.add(loginInfo.getCity());
		sb.append("		AND A.STATIDIMENSION = ?");
		params.add(dimension);
		sb.append("		GROUP BY A.CITY,A.FLAG, A.WHGRIDCODE,A.WHGRIDNAME");
		sb.append("		ORDER BY A.CITY, A.WHGRIDCODE,A.WHGRIDNAME,A.Flag");
		List<SatisInfo> datas = SpringBeanUtil.getDBPersistentService().find(sb.toString(), SatisInfo.class,
				params.toArray());
		if (datas == null || datas.isEmpty())
			CheckUtils.checkNull(null, "暂无当前网格的最新数据");
		resp.setTime(time);
		params.remove(0);
//		params.clear();

		if (req.getDate().equalsIgnoreCase("M")) {
			date = getPreMonth(getPreMonth(nowdate));
		} else {
			date = getPreDay(getPreDay(nowdate));
		}
		time = DateUtils.formatDate(date, format);
		params.add(0, time);
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
	
	public ReturnInfo queLeaderUserStatistKpiIndexList(QueLeaderUserStatistKpiIndexListReq req,ArrayList<SysKpiConfig> datas) throws Exception {
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		String sql = "FROM SysKpiConfig where kpiType = ? and isShow = ? and (city = ? or city = ?)";
		List params = new ArrayList();
		params.add(req.getKpiType());
		params.add("Y");
		params.add("*");
		params.add(loginInfo.getCity());
		List<SysKpiConfig> configs = getDAO().find(sql,params.toArray());
		if(null == configs || configs.isEmpty()) {
			CheckUtils.checkNull(null, "当前地市未配置收入类指标项列表!");
		}
		datas.addAll(configs);
		return returnInfo;
	}
	
	public ReturnInfo queLeaderTargetDataTypeList(QueLeaderTargetDataTypeListReq req,
			TargetListResp resp) throws Exception {
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		CheckUtils.checkEmpty(req.getDate(), "日期纬度不能为空");
		CheckUtils.checkEmpty(req.getGridCode(), "网格编码不能为空!");
		CheckUtils.checkEmpty(req.getKpiCode(), "指标编码不能为空!");
		CheckUtils.checkNull(req.getKpiId(), "指标编码不能为空!");
		SysKpiConfig config = (SysKpiConfig) getDAO().find(SysKpiConfig.class, req.getKpiId());
		CheckUtils.checkNull(config, "根据指标编码查找不到对应的指标!");
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
		
		String[] gridcodes = req.getGridCode().split(",");
		String formatStr = "NNN";
		List params = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append("		select etl.code2name(a.city,'PRV_CITY') city,a.whgridcode gridCode,a.whgridname gridName");
		sb.append("		,a.kpiid flag,b.kpiname flagName,a.kpivalue flagSum,a.kpiadd addNums,a.kpiloss lossNums,a.kpinetinc etincNums");
		sb.append("		from dm.dm_kpi_whgrid a");
		sb.append("		inner join frntpc.TR_KPICODE_CFG b");
		sb.append("		on a.kpiid = b.kpiid");
		sb.append(" 	and a.kpiid = ?");
		params.add(req.getKpiCode());
		sb.append("		and a.city = ?");
		params.add(loginInfo.getCity());
		sb.append("		and a.areaid = ?");
		params.add("NNN");
		if(req.getDate().equalsIgnoreCase("m")){
			sb.append("		and a.monthtype != ?");
			sb.append("		and a.monthtype = ?");
		}else{
			sb.append("		and a.stadate != ?");
			sb.append("		and a.stadate = ?");
		}
		params.add(formatStr);
		params.add(time);
		sb.append("		and a.whgridcode in (");
		for(int i = 0;i < gridcodes.length;i++) {
			sb.append("?");
			params.add(gridcodes[i]);
			if(i != gridcodes.length-1) {
				sb.append(",");	
			}
		}
		sb.append(")");
		List<DataTarget> targets = SpringBeanUtil.getPersistentService().find(sb.toString(), 
				DataTarget.class, params.toArray());
		if(targets == null || targets.isEmpty())
			CheckUtils.checkNull(null, "根据当前网格编码查询不到对应的信息");
		for(DataTarget target:targets) {
			target.setIsDetail(config.getIsDetail());
			target.setSort(config.getSort());
			break;
		}
		Collections.sort(targets, dataTarParator);
		resp.setDatas(targets);
		return returnInfo;
	}

	private Comparator<DataTarget> dataTarParator = new Comparator<DataTarget>() {

		@Override
		public int compare(DataTarget o1, DataTarget o2) {
			if(o1.getSort() > o2.getSort()){
				return 1;
			}
			return -1;
		}
	};
	
	public ReturnInfo queLeaderAchievementDataList(QueAchievementDataReq req) throws Exception {
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		CheckUtils.checkEmpty(req.getDate(), "绩效日期不能为空");
		CheckUtils.checkEmpty(req.getGridCode(),"网格编码不能为空!");
		String[] gridcodes = req.getGridCode().split(",");
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
		sb.append("		AND DATE_FORMAT(a.CYCLENUM,'%Y%m') = ?");
		params.add(req.getDate());
		sb.append("		AND a.STATUS = 1");
		sb.append("		AND a.gridid in ");
		sb.append("		(SELECT t.gridid FROM biz_grid_info t WHERE t.gridcode in (");
		for(int i = 0;i<gridcodes.length;i++) {
			sb.append("?");
			params.add(gridcodes[i]);
			if(i!= gridcodes.length-1) {
				sb.append(",");
			}
		}
		sb.append("))");
		List<AchievementData> datas = getDAO().find(sb.toString(), AchievementData.class, params.toArray());
		if(null == datas || datas.isEmpty()) {
			CheckUtils.checkNull(null, "查询不到相关数据!");
		}
		handlerDateFormart(datas);
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
