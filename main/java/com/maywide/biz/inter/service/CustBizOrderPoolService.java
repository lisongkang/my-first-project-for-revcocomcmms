package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.cons.BizConstant.BizCustorderOrderstatus;
import com.maywide.biz.cons.BizConstant.BossInterfaceService;
import com.maywide.biz.cons.BizConstant.CUSTBIZ_ORDER_STATUS;
import com.maywide.biz.cons.BizConstant.PORTAL_ORDER_STATUS;
import com.maywide.biz.core.entity.Rule;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.pojo.api.BaseApiRequest;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.inter.entity.CustBizOrderPool;
import com.maywide.biz.inter.pojo.KindNameEnum;
import com.maywide.biz.inter.pojo.addCustBizOrderToPool.AddCustBizOrderToPoolReq;
import com.maywide.biz.inter.pojo.addCustBizOrderToPool.AddCustBizOrderToPoolResp;
import com.maywide.biz.inter.pojo.dataReport.GridInfoResp;
import com.maywide.biz.inter.pojo.deletecustbizorderpool.DeleteCustbizorderpoolInterReq;
import com.maywide.biz.inter.pojo.deletecustbizorderpool.DeleteCustbizorderpoolInterResp;
import com.maywide.biz.inter.pojo.equipInfoSubmit.EquiInfoSubmitDevInfo;
import com.maywide.biz.inter.pojo.equipInfoSubmit.EquipInfoSubmitBossReq;
import com.maywide.biz.inter.pojo.equipInfoSubmit.EquipInfoSubmitReq;
import com.maywide.biz.inter.pojo.equipInfoSubmit.EquipInfoSubmitResp;
import com.maywide.biz.inter.pojo.queCustBizOrderDetail.QueCustBizOrderDetailReq;
import com.maywide.biz.inter.pojo.queCustBizOrderDetail.QueCustBizOrderDetailResp;
import com.maywide.biz.inter.pojo.quecustorder.SortConditionsBO;
import com.maywide.biz.inter.pojo.querycustbizorderpool.QueryCustbizorderpoolInterReq;
import com.maywide.biz.inter.pojo.querycustbizorderpool.QueryCustbizorderpoolInterResp;
import com.maywide.biz.inter.pojo.savecustbizorderpool.SaveCustbizorderpoolInterReq;
import com.maywide.biz.inter.pojo.savecustbizorderpool.SaveCustbizorderpoolInterResp;
import com.maywide.biz.inter.pojo.wflqueequipdetinfo.QueEquipDetBoBean;
import com.maywide.biz.inter.pojo.wflqueequipdetinfo.WflQueEquipdetinfoBossReq;
import com.maywide.biz.inter.pojo.wflqueequipdetinfo.WflQueEquipdetinfoBossResp;
import com.maywide.biz.inter.pojo.wflqueequipdetinfo.WflQueEquipdetinfoInterReq;
import com.maywide.biz.inter.pojo.wflqueequipdetinfo.WflQueEquipdetinfoInterResp;
import com.maywide.biz.inter.pojo.wflqueequipinfo.WflQueEquipinfoInterReq;
import com.maywide.biz.inter.pojo.wflqueequipinfo.WflQueEquipinfoInterResp;
import com.maywide.biz.market.entity.BizPortalOrder;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.biz.prv.entity.PrvArea;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.dao.support.Page;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;

@Service
public class CustBizOrderPoolService extends CommonService {

	private static final Logger logger = LoggerFactory.getLogger(CustBizOrderPoolService.class);
	@Autowired
	private RuleService ruleService;

	private static Object object = new Object();

	@Autowired
	private ParamService paramService;

	@Autowired
	private PubService pubService;

	private Map<String, String> codeNameMap = new ConcurrentHashMap<String, String>();

	private Map<String,String> paywayMap = new ConcurrentHashMap<String, String>();

	@Autowired
	private DeviceAllocationService deviceAllocationService;
	
	@Autowired
	private InterPrdDataService dataService;

	private void initHashMap() {
		try {
			if (codeNameMap.size() == 0) {
				List<PrvSysparam> prvSysparams = paramService.getData("BIZ_OPCODE");
				if (null != prvSysparams && !prvSysparams.isEmpty()) {
					for (PrvSysparam sysparam : prvSysparams) {
						if (StringUtils.isNotBlank(sysparam.getMcode())
								&& StringUtils.isNotBlank(sysparam.getMname())) {
							codeNameMap.put(sysparam.getMcode(), sysparam.getMname());
						}
					}
				}
			}
			if(paywayMap.size() == 0) {
				List<PrvSysparam> prvSysparams = paramService.getData("SYS_PAYWAY");
				if (null != prvSysparams && !prvSysparams.isEmpty()) {
					for (PrvSysparam sysparam : prvSysparams) {
						if (StringUtils.isNotBlank(sysparam.getMcode())
								&& StringUtils.isNotBlank(sysparam.getMname())) {
							paywayMap.put(sysparam.getMcode(), sysparam.getMname());
						}
					}
				}
			}
		} catch (Exception ex) {

		}
	}
	

	@Transactional(rollbackFor = Exception.class)
	public ReturnInfo addCustBizOrderToPool(AddCustBizOrderToPoolReq req, AddCustBizOrderToPoolResp resp)
			throws Exception {
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		CheckUtils.checkEmpty(req.getBizorderid(), "订单流水号不能为空!");
		WflQueEquipdetinfoBossReq bossReq = new WflQueEquipdetinfoBossReq();
		bossReq.setSerialno(req.getBossserialno());
		String serverCode = BizConstant.BossInterfaceService.WFL_QUE_EQUIPDETINFO;
		String bossRespOutput = this.getBossHttpInfOutput(req.getBizorderid(), serverCode, bossReq, loginInfo);
		WflQueEquipdetinfoBossResp bossResp = (WflQueEquipdetinfoBossResp) BeanUtil.jsonToObject(bossRespOutput,
				WflQueEquipdetinfoBossResp.class);
		CheckUtils.checkNull(bossResp, "根据订单号【" + req.getBossserialno() + "】无法查询到相关订单!");
		if (null == bossResp.getQueEquipDetBo() || bossResp.getQueEquipDetBo().isEmpty()) {
			CheckUtils.checkNull(null, "根据订单号【" + req.getBossserialno() + "】无法查询到相关订单!");
		}
		CustBizOrderPool orderPool = new CustBizOrderPool();
		orderPool.setBossserialno(req.getBossserialno());
		List<CustBizOrderPool> lists = DAO.find(orderPool);
		if (null != lists && !lists.isEmpty()) {
			CheckUtils.checkNull(null, "该业务订单已被其他业务员加入订单池!");
		}
		synchronized (object) {
			Date optime = new Date();
			List<QueEquipDetBoBean> datas = bossResp.getQueEquipDetBo();
			List<String> ids = new ArrayList<String>();
			for (QueEquipDetBoBean bean : datas) {
				CustBizOrderPool custBizOrderPool = new CustBizOrderPool();
				custBizOrderPool.setAddr(bean.getAddr());
				custBizOrderPool.setAreaid(bean.getAreaid());
				custBizOrderPool.setBizcode(bean.getBizcode());
				custBizOrderPool.setBiztime(bean.getBiztime());
				custBizOrderPool.setBossserialno(bean.getSerialno());
				custBizOrderPool.setCustid(bean.getCustid());
				custBizOrderPool.setCustName(bean.getCustname());
				custBizOrderPool.setServorderid(bean.getServorderid());
				custBizOrderPool.setFees(bean.getFees());
				custBizOrderPool.setMarkno(bean.getMarkno());
				custBizOrderPool.setMobile(bean.getMobile());
				custBizOrderPool.setOperid(loginInfo.getOperid());
				custBizOrderPool.setOptime(optime);
				custBizOrderPool.setPayways(bean.getPayway());
				custBizOrderPool.setPhone(bean.getPhone());
				custBizOrderPool.setFeeStatus(bean.getFeeStatus());
				DAO.save(custBizOrderPool);
				ids.add(bean.getServorderid().toString());
			}
			resp.setDatas(ids);
		}
		return returnInfo;
	}

	/**
	 * 1 新建或者修改订单池
	 * 
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public ReturnInfo saveCustbizorderpool(SaveCustbizorderpoolInterReq req, SaveCustbizorderpoolInterResp resp)
			throws Exception {
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();

		// LoginInfo loginInfo = InterTest.getGridTestLoginInfo2();
		Rule rule = ruleService.getRule(loginInfo.getCity(), "CUSTBIZORDERPOOL_MAX_VALUE");
		int maxValue = 99;
		if (null != rule && null != rule.getValue() && Integer.parseInt(rule.getValue()) > 0) {
			maxValue = Integer.parseInt(rule.getValue());
		}
		String areaids = queAreas(loginInfo);
		synchronized (object) {
			// 当前地市已经抢到的单
			Long count = countPoolsByCity(areaids);
			// 如果当前地市已经抢到的单大于该地市的最大限制值，抛异常，不让加
			if (count >= maxValue) {
				throw new BusinessException("该地市抢到的订单量已经达到最大值");
			}
			CustBizOrderPool item = req.getCustBizOrderPool();
			if (null == item.getId()) {
				initObject(item, loginInfo);
			}
			getDAO().saveOrUpdate(item);
			resp.setCustBizOrderPool(item);
		}
		return returnInfo;
	}

	private void initObject(CustBizOrderPool item, LoginInfo loginInfo) {
		item.setOptime(new Date());
		item.setOperid(loginInfo.getOperid());
	}

	/**
	 * 2删除订单池订单
	 */
	@Transactional(rollbackFor=Exception.class)
	public ReturnInfo deleteCustbizorderpool(DeleteCustbizorderpoolInterReq req, DeleteCustbizorderpoolInterResp resp)
			throws Exception {
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		CheckUtils.checkNull(req.getId(), "操作异常,订单id不能为空");
		CustBizOrderPool bizOrderPool = (CustBizOrderPool) DAO.find(CustBizOrderPool.class, req.getId());
		CheckUtils.checkNull(req.getId(), "操作异常,无法关联到当前id订单");
		CustBizOrderPool orderPool = new CustBizOrderPool();
		orderPool.setBossserialno(bizOrderPool.getBossserialno());
		orderPool.setOperid(loginInfo.getOperid());
		List<CustBizOrderPool> lists = DAO.find(orderPool);
		if(null == lists || lists.isEmpty()) {
			CheckUtils.checkNull(null, "抱歉,您的订单池中不包含该订单!");
		}
		List<String> datas = new ArrayList<String>();
		for (CustBizOrderPool pool : lists) {
			try {
				DAO.delete(pool);
				datas.add(pool.getId().toString());
			} catch (Exception e) {
				continue;
			}
		}
		resp.setDatas(datas);
		// getDAO().executeSql("DELETE FROM biz_custorder_pool WHERE biz_orderid = ?",
		// req.getId());
		return returnInfo;
	}

	/**
	 * 3订单池查询接口
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queryCustbizorderpool(QueryCustbizorderpoolInterReq req, QueryCustbizorderpoolInterResp resp)
			throws Exception {

		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
//		LoginInfo loginInfo = InterTest.getGridTestLoginInfo();
		List params = new ArrayList();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("			select * from (");
		sqlBuffer.append("			select a.biz_orderid as id");
		sqlBuffer.append("			,a.bossserialno");
		sqlBuffer.append("			,a.cust_name");
		sqlBuffer.append("			,a.custid");
		sqlBuffer.append("			,a.servorderid");
		sqlBuffer.append("			,code2name(a.bizcode,'BIZ_OPCODE') bizcode");
		sqlBuffer.append("			,a.addr");
		sqlBuffer.append("			,a.markno");
		sqlBuffer.append("			,a.mobile");
		sqlBuffer.append("			,a.phone");
		sqlBuffer.append("			,a.fees");
		sqlBuffer.append("			,a.payways");
		sqlBuffer.append("			,a.operid");
		sqlBuffer.append("			,a.optime ");
		sqlBuffer.append("			,a.biztime");
		sqlBuffer.append(" FROM biz_custorder_pool a WHERE 1=1");
		if (null != req && null != req.getPermark() && !"".equals(req.getPermark())) {
			sqlBuffer.append(" AND a.permark LIKE ? ");
			String permark = "%" + req.getPermark() + "%";
			params.add(permark);
		}
		if (null != req && null != req.getStarttime() && !"".equals(req.getStarttime())) {
			sqlBuffer.append(" AND a.optime > ? ");
			String starttime = req.getStarttime();
			params.add(starttime);
		}
		if (null != req && null != req.getEndtime() && !"".equals(req.getEndtime())) {
			sqlBuffer.append(" AND a.optime < ? ");
			String endtime = req.getEndtime();
			params.add(endtime);
		}
		sqlBuffer.append("		AND a.operid = ?");
		params.add(loginInfo.getOperid());
		sqlBuffer.append("		AND a.order_status is null");
		sqlBuffer.append(" )v");

		if (BeanUtil.isListNotNull(req.getSortConditions())) {
			// 进行可排序字段合法性检查
			checkSortConditins(req, loginInfo);

			StringBuffer sortConditions = new StringBuffer();
			sortConditions.append(" order by ");
			for (SortConditionsBO obj : req.getSortConditions()) {
				sortConditions.append(obj.getSortname() + " ");
				if (BizConstant.SortType.ASC.endsWith(obj.getSorttype())) {
					sortConditions.append(" ASC, ");
				} else {
					sortConditions.append(" DESC, ");
				}
			}

			sortConditions.setLength(sortConditions.lastIndexOf(","));
			sqlBuffer.append(sortConditions);
		}

		Page page = new Page();
		page.setPageSize(10);
		if (StringUtils.isNotBlank(req.getPagesize())) {
			page.setPageSize(Integer.valueOf(req.getPagesize()));
		}
		if (StringUtils.isNotBlank(req.getCurrentPage())) {
			page.setPageNo(Integer.valueOf(req.getCurrentPage()));
		}
		getDAO().clear();
		page = getDAO().find(page, sqlBuffer.toString(), CustBizOrderPool.class, params.toArray());
		List<CustBizOrderPool> custBizOrderPoolList = page.getResult();
		resp.setPagesize(BeanUtil.object2String(page.getPageSize()));
		resp.setCurrentPage(BeanUtil.object2String(page.getCntPageNo()));
		resp.setTotalRecords(BeanUtil.object2String(page.getTotalCount()));
		resp.setCustBizOrderPoolList(custBizOrderPoolList);
		return returnInfo;
	}

	/**
	 * 通过查询boss接口跟本地的数据库进行比对返回给app(工单列表界面接口)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo wflQueEquipdetinfo(WflQueEquipdetinfoInterReq req, WflQueEquipdetinfoInterResp resp)
			throws Exception {
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		// LoginInfo loginInfo = InterTest.getGridTestLoginInfo();
		WflQueEquipdetinfoBossReq bossReq = getWflQueEquipdetinfoBossReq4Req(req, loginInfo);
		String serverCode = BizConstant.BossInterfaceService.WFL_QUE_EQUIPDETINFO;
		// 调用BOSS接口
		String bossRespOutput = this.getBossHttpInfOutput(req.getBizorderid(), serverCode, bossReq, loginInfo);
		// 把boss返回的出参转成集客的出参
		/*
		 * JSONArray array = new JSONArray(bossRespOutput);
		 * List<WflQueEquipdetinfoInterChileResp> list = BeanUtil.jsonToObject(array,
		 * WflQueEquipdetinfoInterChileResp.class); resp.setList(list);
		 */
		WflQueEquipdetinfoBossResp bossResp = (WflQueEquipdetinfoBossResp) this.copyBossResp2InterResp(resp,
				WflQueEquipdetinfoBossResp.class, bossRespOutput);
		WflQueEquipdetinfoInterResp resp2 = new WflQueEquipdetinfoInterResp(bossResp);
		fillBizName(resp2);
		resp2.setOrderNum(countOperOrderNum(loginInfo.getOperid()));
		BeanUtils.copyProperties(resp, resp2);
		// List<QueEquipDetBoBean> queEquipDetBo = bossResp.getQueEquipDetBo();
		return returnInfo;
	}

	/**
	 * 添加过滤的工单
	 * 
	 * @param req
	 * @param loginInfo
	 * @return
	 * @throws Exception
	 */
	private WflQueEquipdetinfoBossReq getWflQueEquipdetinfoBossReq4Req(WflQueEquipdetinfoInterReq req,
			LoginInfo loginInfo) throws Exception {
		WflQueEquipdetinfoBossReq bossReq = new WflQueEquipdetinfoBossReq(req);
		if(StringUtils.isBlank(bossReq.getAreaid())) {
			bossReq.setAreaid(loginInfo.getAreaid().toString());	
		}
		if(StringUtils.isBlank(bossReq.getGridcodes())) {
			ArrayList<GridInfoResp> gridResp = new ArrayList<GridInfoResp>();
			try {
				dataService.queGridDataInfo(gridResp);	
			}catch(Exception ex) {
			}
			if(!gridResp.isEmpty()) {
				StringBuffer sb = new StringBuffer();
				for(int i = 0; i < gridResp.size(); i++) {
					sb.append(gridResp.get(i).getGridCode());
					if(i != gridResp.size() - 1) {
						sb.append(",");
					}
				}
				bossReq.setGridcodes(sb.toString());
			}
			
		}
		// 把请求转成boss入参
		// BeanUtils.copyPropertiesNotSuperClass(bossReq, req);
		if (req.isFilter()) {
			String sql = "select * from biz_custorder_pool where order_status != ? or order_status is null";
			List<CustBizOrderPool> lists = DAO.find(sql, CustBizOrderPool.class,"2");
			if (null != lists && !lists.isEmpty()) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < lists.size(); i++) {
					sb.append(lists.get(i).getServorderid());
					if (i != lists.size() - 1) {
						sb.append(",");
					}
				}
				bossReq.setFilters(sb.toString());
			}
		}
		return bossReq;
	}

	private void fillBizName(WflQueEquipdetinfoInterResp resp) {
		initHashMap();
		if (resp.getQueEquipDetBo() != null && !resp.getQueEquipDetBo().isEmpty()) {
			if(codeNameMap.size() != 0 ) {
				for (QueEquipDetBoBean bean : resp.getQueEquipDetBo()) {
					bean.setBizName(codeNameMap.get(bean.getBizcode()));
				}
			}
			if(paywayMap.size() != 0) {
				for (QueEquipDetBoBean bean : resp.getQueEquipDetBo()) {
					if(StringUtils.isNotBlank(bean.getPayway())) {
						bean.setPaywayName(paywayMap.get(bean.getPayway()));
					}
				}
			}
			
		}
		
	}

	private int countOperOrderNum(Long operid) throws Exception {
		CustBizOrderPool entity = new CustBizOrderPool();
		//entity.setOperid(operid);
		//entity.setOrderStatus(null);
		List<Object> params = new ArrayList();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT biz_orderid from biz_custorder_pool where operid = ? and order_status is null ");
		params.add(operid);
		List<CustBizOrderPool> lists = DAO.find(sqlBuffer.toString(),
				CustBizOrderPool.class, params.toArray());
		return lists == null ? 0 : lists.size();
	}

	private WflQueEquipdetinfoBossReq getWflQueEquipdetinfoBossReq(WflQueEquipdetinfoInterReq req) throws Exception {
		WflQueEquipdetinfoBossReq bossReq = new WflQueEquipdetinfoBossReq();
		BeanUtils.copyPropertiesNotSuperClass(bossReq, req);

		return bossReq;
	}

	/**
	 * 定义可以当做排序的条件
	 */
	private void checkSortConditins(QueryCustbizorderpoolInterReq req, LoginInfo loginInfo) throws Exception {
		if (null == req || BeanUtil.isListNull(req.getSortConditions())) {
			return;
		}

		Set sortnameSet = new HashSet();
		sortnameSet.add("optime");
		sortnameSet.add("biztime");
		sortnameSet.add("bizcode");

		for (SortConditionsBO obj : req.getSortConditions()) {
			if (!sortnameSet.contains(obj.getSortname())) {
				throw new BusinessException("排序条件[" + obj.getSortname() + "]未定义");
			}
		}
	}

	/**
	 * 查询所在地市的业务区
	 * 
	 * @return
	 * @throws Exception
	 */
	private String queAreas(LoginInfo loginInfo) throws Exception {
		List<Object> params = new ArrayList();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT GROUP_CONCAT(a.areaid) AS NAME FROM prv_area a WHERE a.city=?");
		params.add(loginInfo.getCity());
		List<PrvArea> prvAreaList = getDAO().find(sqlBuffer.toString(), PrvArea.class, params.toArray());
		String areas = null;
		if (null != prvAreaList) {
			areas = prvAreaList.get(0).getName();
		}
		return areas;
	}

	private Long countPoolsByCity(String areaids) throws Exception {
		List<Object> sqlparam = new ArrayList<Object>();
		StringBuffer selectSQL = new StringBuffer("SELECT COUNT(*) FROM biz_custorder_pool a ");
		selectSQL.append(" WHERE FIND_IN_SET(a.areaid, ?) ");
		sqlparam.add(areaids);
		long counter = getDAO().count(selectSQL.toString(), sqlparam.toArray());
		return counter;
	}

	/**
	 * 进入业务订单操作详情界面查询接口
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queCustBizOrderDetail(QueCustBizOrderDetailReq req, QueCustBizOrderDetailResp resp)
			throws Exception {
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		if (null == req.getCustorderid() && null == req.getServorderid()) {
			CheckUtils.checkNull(null, "订单编号信息不能为空");
		}
		CustBizOrderPool custBizOrderPool = getCustBizOrderPool4Id(req.getCustorderid(), req.getServorderid());
		WflQueEquipdetinfoInterReq wfReq = new WflQueEquipdetinfoInterReq();
		wfReq.setSerialno(custBizOrderPool.getBossserialno());
		wfReq.setServorderid(custBizOrderPool.getServorderid().toString());
		wfReq.setCustid(custBizOrderPool.getCustid().toString());
		wfReq.setFilter(false);
//		wfReq.setBizorderid(DAO.getSequence("SEQ_BIZ_CUSTORDER_ID").toString());
        wfReq.setBizorderid(getBizorderid());
		WflQueEquipdetinfoInterResp wfResp = new WflQueEquipdetinfoInterResp();
		wflQueEquipdetinfo(wfReq, wfResp);
		if (null == wfResp.getQueEquipDetBo() || wfResp.getQueEquipDetBo().isEmpty()) {
			CheckUtils.checkNull(null, "查询工单接口出错,没有该订单下的设备信息!");
		}

		WflQueEquipinfoInterReq devReq = new WflQueEquipinfoInterReq();
		devReq.setCustid(custBizOrderPool.getCustid().toString());
		devReq.setSerialno(custBizOrderPool.getBossserialno());
		devReq.setServorderid(custBizOrderPool.getServorderid().toString());
		devReq.setStepcode(req.getStepcode());
//		devReq.setBizorderid(DAO.getSequence("SEQ_BIZ_CUSTORDER_ID").toString());
		devReq.setBizorderid(getBizorderid());
		WflQueEquipinfoInterResp devResp = new WflQueEquipinfoInterResp();
		deviceAllocationService.wflQueEquipinfo(devReq, devResp);

		if (null == devResp.getWflQueEquipinfoList() || devResp.getWflQueEquipinfoList().isEmpty()) {
			CheckUtils.checkNull(null, "查询设备接口出错,没有该订单下的设备信息!");
		}
		resp.setOrderInfo(wfResp.getQueEquipDetBo().get(0));
		resp.setDevInfo(devResp.getWflQueEquipinfoList().get(0));

		return returnInfo;
	}

	/**
	 * 工单设备分配提交接口
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo equipInfoSubmit(EquipInfoSubmitReq req, EquipInfoSubmitResp resp) throws Exception {
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		if (null == req.getServorderid() && null == req.getRecid()) {
			CheckUtils.checkNull(null, "订单编号信息不能为空");
		}
		CustBizOrderPool custBizOrderPool = getCustBizOrderPool4Id(req.getRecid(), req.getServorderid());
		WflQueEquipdetinfoInterReq wfReq = new WflQueEquipdetinfoInterReq();
		wfReq.setSerialno(custBizOrderPool.getBossserialno());
		wfReq.setServorderid(custBizOrderPool.getServorderid().toString());
		wfReq.setCustid(custBizOrderPool.getCustid().toString());
		wfReq.setFilter(false);
//		wfReq.setBizorderid(DAO.getSequence("SEQ_BIZ_CUSTORDER_ID").toString());
		wfReq.setBizorderid(getBizorderid());
		WflQueEquipdetinfoInterResp wfResp = new WflQueEquipdetinfoInterResp();
		wflQueEquipdetinfo(wfReq, wfResp);
		if (null == wfResp.getQueEquipDetBo() || wfResp.getQueEquipDetBo().isEmpty()) {
			CheckUtils.checkNull(null, "该工单已完成设备分配!");
		}
		final QueEquipDetBoBean boBean = wfResp.getQueEquipDetBo().get(0);
		final EquipInfoSubmitBossReq bossReq = getEquipInfoSubmitBossReq(req, boBean, custBizOrderPool);
		if (boBean.getFeeStatus().equalsIgnoreCase("Y")||boBean.getFeeStatus().equalsIgnoreCase("G")) {/** 已支付状态 添加订单记录到网格数据库并调用BOSS接口 **/
			getBossHttpInfOutput(req.getBizorderid(),
			 BossInterfaceService.WFL_EQUIPINFO_SUBMIT, bossReq, loginInfo);
			try {
				handlerDB(req, custBizOrderPool, boBean, loginInfo);
				resp.setIsPayed("Y");
				resp.setCustorderid(req.getBizorderid());
			} catch (Exception ex) {
				logger.error("====handle DB fail====");
			}
		} else {/** 为未支付状态 **/
			if (StringUtils.isNotBlank(req.getPayway()) && custBizOrderPool.getFeeStatus().equalsIgnoreCase("N")) {// 判断支付方式是否为空
																													// 为空则是要发起支付请求，不为空则表示为已支付发起提交请求
				 getBossHttpInfOutput(req.getBizorderid(),
				 BossInterfaceService.WFL_EQUIPINFO_SUBMIT, bossReq, loginInfo);
				try {
					handlerDB(req, custBizOrderPool, boBean, loginInfo);
					resp.setIsPayed("Y");
					resp.setCustorderid(req.getBizorderid());
				} catch (Exception ex) {
					logger.error("====handle DB fail====");
				}
			} else {
				custBizOrderPool.setDevStr(new Gson().toJson(req.getDevicelist()));
				custBizOrderPool.setSalePkgName(req.getSalespkgname());
				DAO.saveOrUpdate(custBizOrderPool);
				resp.setIsPayed("N");
			}
		}
		return returnInfo;
	}

	@Transactional(rollbackFor = Exception.class)
	private void handlerDB(EquipInfoSubmitReq req, CustBizOrderPool custBizOrderPool, QueEquipDetBoBean boBean,
			LoginInfo loginInfo) throws Exception {
		Map<String, Object> infoMap = new HashMap<String, Object>();
		if(null != req.getDevicelist() && !req.getDevicelist().isEmpty()) {
			fillCustDevinfo(req.getDevicelist());
			infoMap.put("devInfo", req.getDevicelist());
		}
		if(StringUtils.isNotBlank(custBizOrderPool.getDevStr())) {
			List<EquiInfoSubmitDevInfo> devList = new Gson().fromJson(custBizOrderPool.getDevStr(),
					new TypeToken<List<EquiInfoSubmitDevInfo>>() {
					}.getType());
			fillCustDevinfo(devList);
			infoMap.put("devInfo", devList);
		}
		infoMap.put("salepkgInfo", custBizOrderPool.getSalePkgName());
		addCustOrder(req, loginInfo, boBean, new Gson().toJson(infoMap));
		addPortalOrder(loginInfo, boBean, req);
		removeCustBizOrder(custBizOrderPool);
	}
	
	private void fillCustDevinfo(List<EquiInfoSubmitDevInfo> devInfos) {
		if(devInfos == null || devInfos.isEmpty()) return;
		for(EquiInfoSubmitDevInfo devInfo:devInfos) {
			if(StringUtils.isNotBlank(devInfo.getKind())) {
				devInfo.setKindname(KindNameEnum.getKindName(devInfo.getKind()));
			}
		}
	}

	private void addCustOrder(BaseApiRequest req, LoginInfo loginInfo, QueEquipDetBoBean boBean, String extrasInfo)
			throws Exception {
		CustOrder custOrder = new CustOrder();
		custOrder.setId(Long.parseLong(req.getBizorderid()));
		custOrder.setAreaid(loginInfo.getAreaid());
		custOrder.setAddr(boBean.getAddr());
		custOrder.setBossserialno(boBean.getSerialno());
		custOrder.setCity(loginInfo.getCity());
		custOrder.setCustid(boBean.getCustid());
		custOrder.setName(boBean.getCustname());
		custOrder.setOpcode(BossInterfaceService.WFL_EQUIPINFO_SUBMIT);
		custOrder.setOperator(loginInfo.getOperid());
		custOrder.setOprdep(loginInfo.getDeptid());
		custOrder.setOptime(new Date());
		custOrder.setOrdercode(pubService.getOrderCode().toString());
		custOrder.setOrderstatus(BizCustorderOrderstatus.SYNC);
		custOrder.setBizmemo(extrasInfo);
		custOrder.setPortalOrder(null);
		DAO.save(custOrder);
	}

	private void addPortalOrder(LoginInfo loginInfo, QueEquipDetBoBean boBean, EquipInfoSubmitReq req)
			throws Exception {
		BizPortalOrder portalOrder = new BizPortalOrder();
		portalOrder.setCreatetime(new Date());
		portalOrder.setFees(boBean.getFees());
		portalOrder.setId(Long.parseLong(req.getBizorderid()));
		portalOrder.setPaycode(req.getSubpay());
		portalOrder.setPayreqid(req.getPayreqid());
		portalOrder.setPaytime(new Date());
		portalOrder.setPayway(req.getPayway());
		portalOrder.setResporderid(boBean.getServorderid());
		if (boBean.getFeeStatus().equalsIgnoreCase("Y")) {
			portalOrder.setStatus(PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_BOSSPAD);
		} else {
			portalOrder.setStatus(PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_PAYED);
		}
		DAO.save(portalOrder);
	}

	private void removeCustBizOrder(CustBizOrderPool custBizOrderPool) throws Exception {
		synchronized (object) {
			custBizOrderPool.setOrderStatus(CUSTBIZ_ORDER_STATUS.ORDER_FINISH);
			DAO.save(custBizOrderPool);
		}
	}

	private EquipInfoSubmitBossReq getEquipInfoSubmitBossReq(EquipInfoSubmitReq req, QueEquipDetBoBean boBean,
			CustBizOrderPool custBizOrderPool) throws Exception {
		EquipInfoSubmitBossReq bossReq = new EquipInfoSubmitBossReq();
		bossReq.setAcctno(req.getAcctno());
		bossReq.setCustid(boBean.getCustid());
		bossReq.setCustorderid(boBean.getCustorderid());
		if (boBean.getFeeStatus().equalsIgnoreCase("Y")||boBean.getFeeStatus().equalsIgnoreCase("G")) {
			CheckUtils.checkNull(req.getDevicelist(), "设备列表信息不能为空!");
			bossReq.setDevicelist(req.getDevicelist());
			bossReq.setPayway(boBean.getPayway());
			bossReq.setRpay(boBean.getFees());
		} else {
			if (StringUtils.isNotBlank(req.getPayway()) && custBizOrderPool.getFeeStatus().equalsIgnoreCase("N")) {
				CheckUtils.checkEmpty(custBizOrderPool.getDevStr(), "办理失败,订单池内该设备信息为空");
				CheckUtils.checkEmpty(req.getPayway(), "参数错误:支付方式不能为空!");
				// CheckUtils.checkEmpty(req.getRpay(), "参数错误:支付金额不能为空!");
				bossReq.setPayway(req.getPayway());
				if(StringUtils.isNotBlank(req.getRpay())) {
					bossReq.setRpay(req.getRpay());
				}else {
					bossReq.setRpay(custBizOrderPool.getFees());
				}
				
				List<EquiInfoSubmitDevInfo> devList = new Gson().fromJson(custBizOrderPool.getDevStr(),
						new TypeToken<List<EquiInfoSubmitDevInfo>>() {
						}.getType());
				bossReq.setDevicelist(devList);
				bossReq.setSerialno(boBean.getSerialno());
				bossReq.setSubpay(req.getSubpay());
			}
		}
		bossReq.setSerialno(boBean.getSerialno());
		bossReq.setServorderid(boBean.getServorderid());

		return bossReq;
	}

	private CustBizOrderPool getCustBizOrderPool4Id(Long recid, Long servorderid) throws Exception {
		CustBizOrderPool custBizOrderPool = null;
		if (recid != null) {
			custBizOrderPool = (CustBizOrderPool) DAO.find(CustBizOrderPool.class, recid);
		} else if (servorderid != null) {
			CustBizOrderPool tmPool = new CustBizOrderPool();
			tmPool.setServorderid(servorderid);
			List<CustBizOrderPool> list = DAO.find(tmPool);
			if (list != null && !list.isEmpty()) {
				custBizOrderPool = list.get(0);
			}
		}
		CheckUtils.checkNull(custBizOrderPool, "根据订单池编号查找不到对应的订单信息!");
		return custBizOrderPool;
	}

	private EquipInfoSubmitBossReq getEquipInfoSubmitBossReq4Req(QueEquipDetBoBean orderInfo, EquipInfoSubmitReq req) {
		EquipInfoSubmitBossReq bossReq = new EquipInfoSubmitBossReq();

		bossReq.setCustid(orderInfo.getCustid());
		bossReq.setCustorderid(orderInfo.getCustorderid());
		bossReq.setSerialno(orderInfo.getSerialno());
		bossReq.setServorderid(bossReq.getServorderid());

		bossReq.setAcctno(req.getAcctno());
		bossReq.setDevicelist(req.getDevicelist());
		bossReq.setPayway(req.getPayway());
		bossReq.setRpay(req.getRpay());
		bossReq.setSubpay(req.getSubpay());
		return bossReq;
	}
}
