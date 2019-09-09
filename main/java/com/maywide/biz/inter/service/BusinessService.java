package com.maywide.biz.inter.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.cons.BizConstant.BizCustorderOrderstatus;
import com.maywide.biz.cons.BizConstant.BizCustorderSyncmode;
import com.maywide.biz.cons.BizConstant.BossInterfaceService;
import com.maywide.biz.core.entity.Rule;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.bizBusinessOrder.BizBusinessOrderBossReq;
import com.maywide.biz.inter.pojo.bizBusinessOrder.BizBusinessOrderReq;
import com.maywide.biz.inter.pojo.bizBusinessOrder.BizBusinessOrderResp;
import com.maywide.biz.inter.pojo.bizResDev.BizChlRestart2BossReq;
import com.maywide.biz.inter.pojo.bizResDev.BizChlRestartBossResp;
import com.maywide.biz.inter.pojo.bizResDev.BizChlRestartDevReq;
import com.maywide.biz.inter.pojo.bizResDev.BizChlRestartDevResp;
import com.maywide.biz.inter.pojo.bizUserUEChange.BizUserUEChangeBossReq;
import com.maywide.biz.inter.pojo.bizUserUEChange.BizUserUEChangeReq;
import com.maywide.biz.inter.pojo.bizUserUEChange.BizUserUEChangeResp;
import com.maywide.biz.inter.pojo.bizvlannum.BizVlanNumBossReq;
import com.maywide.biz.inter.pojo.bizvlannum.BizVlanNumReq;
import com.maywide.biz.inter.pojo.bizvlannum.BizVlanNumResp;
import com.maywide.biz.inter.pojo.checkAcpt.CheckAcceptBossReq;
import com.maywide.biz.inter.pojo.checkAcpt.CheckAcceptReq;
import com.maywide.biz.inter.pojo.checkAcpt.CheckAcceptResp;
import com.maywide.biz.inter.pojo.queBizBusiness.BusinessOrderBean;
import com.maywide.biz.inter.pojo.queBizBusiness.QueBizBusinessBossReq;
import com.maywide.biz.inter.pojo.queBizBusiness.QueBizBusinessReq;
import com.maywide.biz.inter.pojo.queBizBusiness.QueBizBusinessResp;
import com.maywide.biz.inter.pojo.queBusOrderInfo.GridSysPrams;
import com.maywide.biz.inter.pojo.queBusOrderInfo.QueBusOrderInfoResp;
import com.maywide.biz.inter.pojo.queCmPwdShow.QueCmPwdShowResp;
import com.maywide.biz.inter.pojo.queProductDetail.QueProductDetailReq;
import com.maywide.biz.inter.pojo.queProductDetail.QueProductDetailResp;
import com.maywide.biz.inter.pojo.queStopUser.QueStop2BossReq;
import com.maywide.biz.inter.pojo.queStopUser.QueStopUserBossResp;
import com.maywide.biz.inter.pojo.queStopUser.QueStopUserReq;
import com.maywide.biz.inter.pojo.queStopUser.QueStopUserResp;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.biz.pay.unify.manager.ParamsManager;
import com.maywide.biz.prd.entity.SalespkgKnow;
import com.maywide.biz.prd.service.SalespkgKnowService;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;
import com.maywide.core.util.DateTimeUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class BusinessService extends CommonService {

	@Autowired
	private PubService pubService;
	
	@Autowired
	private ParamService paramService;
	
	@Autowired
	private PrintService printService;
	
	@Autowired
	private SalespkgKnowService knowService;
	
	private final String BUSINESS_ORDER_STATUS = "BUSINESS_ORDER_STATUS";
	
	
	/**
	 *下销售单产品详情界面
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queProductDetail(QueProductDetailReq req,QueProductDetailResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		CheckUtils.checkNull(req.getKnowid(), "套餐知识库编号不能为空");
		
		SalespkgKnow know = knowService.findOne(req.getKnowid());
		CheckUtils.checkNull(know, "当前产品在产品库中不存在!");
		resp.setData(know);
		return returnInfo;
	}


	/**
	 * 查询停机客户信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queStopUserList(QueStopUserReq req, QueStopUserResp resp) throws Exception {

		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		if (StringUtils.isBlank(req.getCustid()) && StringUtils.isBlank(req.getLogicdevno())) {
			CheckUtils.checkNull(null, "客户id跟设备id不能同时为空");
		}

		String bossOutput = getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.QUE_STOPUSER,
				getQueStop2BossReq(req), loginInfo);

		JSONArray array = new JSONArray(bossOutput);
		List<QueStopUserBossResp> datas = BeanUtil.jsonToObject(array, QueStopUserBossResp.class);
		resp.setDatas(datas);

		return returnInfo;
	}

	private QueStop2BossReq getQueStop2BossReq(QueStopUserReq req) {
		QueStop2BossReq req2Boss = new QueStop2BossReq();
		req2Boss.setChouseid(req.getChouseId());
		req2Boss.setCustid(req.getCustid());
		req2Boss.setLogicdevno(req.getLogicdevno());
		return req2Boss;
	}

	/**
	 * 办理开机业务功能
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */

	public ReturnInfo bizChlRestartDev(BizChlRestartDevReq req, BizChlRestartDevResp resp) throws Exception {

		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkEmpty(req.getCustid(), "开机客户信息不能为空");

		CustOrder custOrder = insertBizCustor(req, loginInfo);
		/*if(null!=req){
			ParamsManager.isCorrectData(req.get,custOrder.getPortalOrder().getPaycode());
		}*/
		String bossOutPut = getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.BIZ_CHL_RESTART,
				getBizChlRestart2BossReq(req), loginInfo);
		BizChlRestartBossResp bossResp = (BizChlRestartBossResp) BeanUtil.jsonToObject(bossOutPut, BizChlRestartBossResp.class);
		custOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.SYNC);
		custOrder.setBossserialno(bossResp.getSerialno());
		getDAO().update(custOrder);
		resp.setOrderId(custOrder.getId().toString());
		printService.judgePrintCondition(custOrder.getOpcode(),
				custOrder.getCity(), custOrder.getBossserialno(), resp);
		return returnInfo;
	}
	

	private BizChlRestart2BossReq getBizChlRestart2BossReq(BizChlRestartDevReq req) {
		BizChlRestart2BossReq req2Boss = new BizChlRestart2BossReq();
		req2Boss.setCustid(req.getCustid());
		req2Boss.setHouseid(req.getHouseid());
		req2Boss.setInstalltype("0");
		req2Boss.setRestartTime(req.getRestartTime());
		req2Boss.setServstList(req.getParams());
		return req2Boss;
	}

	private CustOrder insertBizCustor(BizChlRestartDevReq req, LoginInfo loginInfo) throws Exception {
		CustOrder custOrder = new CustOrder();
		custOrder.setId(Long.parseLong(req.getBizorderid()));
		custOrder.setAreaid(loginInfo.getAreaid());
		custOrder.setCity(loginInfo.getCity());
		custOrder.setOprdep(loginInfo.getDeptid());
		custOrder.setName(req.getCustName());
		custOrder.setCustid(Long.parseLong(req.getCustid()));
		custOrder.setOpcode(BizConstant.BizOpcode.BIZ_CHL_RESTART);
		custOrder.setAddr(req.getAddress());
		custOrder.setSyncmode(BizConstant.BizCustorderSyncmode.SYNC);
		custOrder.setOperator(loginInfo.getOperid());
		custOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.INIT);
		custOrder.setOptime(new Date());
		custOrder.setOrdercode(pubService.getOrderCode());
		custOrder.setPortalOrder(null);
		getDAO().save(custOrder);
		getDAO().flushSession();
		return custOrder;
	}

	/**
	 * 查询报盘接口
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo checkBizAccept(CheckAcceptReq req, CheckAcceptResp resp) throws Exception {

		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkEmpty(req.getCustid(), "客户编号不能为空");

		CheckAcceptBossReq req2Boss = getCheckAcceptBossReq(req);

		String bossOutputStr = getBossHttpInfo(req.getBizorderid(), BossInterfaceService.CHECK_BIZACCEPT, req2Boss,
				loginInfo);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.setDateFormat(dateFormat);

		JsonNode nodeTree = objectMapper.readTree(bossOutputStr);
		parseRespone(nodeTree);
		handlerBossResp(nodeTree.get("output").toString(), resp);
		return returnInfo;
	}
	
	private void parseRespone(JsonNode nodeTree) throws Exception{
		 if (IErrorDefConstant.ERROR_SUCCESS_CODE != nodeTree.get("status")
	                .asInt()) {
	            String msg = nodeTree.get("message").toString();
				if(StringUtils.isNotBlank(msg) ){
				if(msg.equalsIgnoreCase("null")) return;
				CheckUtils.checkNull(null, msg);
	        }
			
		}
	}

	private void handlerBossResp(String jsonStr, CheckAcceptResp resp) throws Exception {
		CheckAcceptResp bossResp = (CheckAcceptResp) BeanUtil.jsonToObject(jsonStr, CheckAcceptResp.class);
		BeanUtils.copyProperties(resp, bossResp);
	}

	private CheckAcceptBossReq getCheckAcceptBossReq(CheckAcceptReq req) {
		CheckAcceptBossReq req2Boss = new CheckAcceptBossReq();
		req2Boss.setCustid(req.getCustid());
		req2Boss.setDevno(req.getDevno());
		return req2Boss;
	}

	/**
	 * 查询录入销售单数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queBizBusinessOrderInfo(QueBusOrderInfoResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT * FROM GRID_SYS_PARAM a");
		sb.append("		WHERE a.GCODE = ?");
		sb.append("		AND a.CITY = ?");

		List<GridSysPrams> bizscenes = getDAO().find(sb.toString(), GridSysPrams.class, "biz_scene",
				loginInfo.getCity());
		resp.setBizscenes(bizscenes);
		sb = new StringBuffer();
		sb.append("		SELECT * FROM GRID_SYS_PARAM a");
		sb.append("		WHERE a.GCODE = ?");
		sb.append("		AND a.CITY = ?");

		List<GridSysPrams> productinfos = getDAO().find(sb.toString(), GridSysPrams.class, "product_info",
				loginInfo.getCity());
		resp.setProductinfos(productinfos);
		List<PrvSysparam> statusParams = paramService.getDataNotCompare(BUSINESS_ORDER_STATUS);
		resp.setStatusParams(statusParams);
//		resp.setOrderid(getDAO().getSequence("SEQ_BIZ_CUSTORDER_ID").toString());
		resp.setOrderid(getBizorderid());
		return returnInfo;
	}

	/**
	 * 
	 * 销售单录入接口
	 * 
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo bizBusinessOrder(BizBusinessOrderReq req, BizBusinessOrderResp resp) throws Exception {

		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CustOrder custOrder = hanlderCustOrder(req, loginInfo);

		BizBusinessOrderBossReq req2Boss = getBizBusinessOrderBossReq(req, loginInfo);
		String bossJsonStr = getBossHttpInfOutput(req.getOrderid(), BossInterfaceService.BIZ_BUSSINESS_ORDER, req2Boss,
				loginInfo);
		custOrder.setOrderstatus(BizCustorderOrderstatus.SYNC);
		//ywp 如果是销售单录入就用orderid调用boss打印无纸化
		custOrder.setBossserialno(req.getBizorderid());
		getDAO().update(custOrder);
		resp.setCustOrderid(custOrder.getId());
		return returnInfo;
	}

	private BizBusinessOrderBossReq getBizBusinessOrderBossReq(BizBusinessOrderReq req, LoginInfo loginInfo) {
		BizBusinessOrderBossReq req2Boss = new BizBusinessOrderBossReq();
		req2Boss.setAreaid(loginInfo.getAreaid());
		req2Boss.setBusinesstype(req.getBusinesstype());
		req2Boss.setCity(loginInfo.getCity());
		req2Boss.setCustid(req.getCustid());
		req2Boss.setEquiplists(req.getEquiplists());
		req2Boss.setErector(loginInfo.getOperid());
		req2Boss.setErectordept(loginInfo.getDeptid());
		req2Boss.setErectorname(loginInfo.getName());
		req2Boss.setLinkaddr(req.getLinkaddr());
		req2Boss.setLinkname(req.getLinkname());
		req2Boss.setLinkphone(req.getLinkphone());
		req2Boss.setMemo(req.getMemo());
		req2Boss.setOrderid(req.getBizorderid());
		req2Boss.setProds(req.getProds());
		req2Boss.setAuthtype(req.getAuthtype());
		return req2Boss;
	}

	private CustOrder hanlderCustOrder(BizBusinessOrderReq req, LoginInfo loginInfo) throws Exception {
		CustOrder custOrder = new CustOrder();
		custOrder.setCity(loginInfo.getCity());
		custOrder.setId(Long.parseLong(req.getOrderid()));
		if (StringUtils.isNotBlank(req.getCustid())) {
			custOrder.setCustid(Long.parseLong(req.getCustid()));
			//ywp 把客户姓名和地址保存到数据库中
			custOrder.setName(req.getLinkname());
			custOrder.setAddr(req.getLinkaddr());
		}
		custOrder.setOperator(loginInfo.getOperid());
		custOrder.setOprdep(loginInfo.getDeptid());
		custOrder.setPortalOrder(null);
		custOrder.setOptime(new Date());
		custOrder.setOrderstatus(BizCustorderOrderstatus.INIT);
		custOrder.setOpcode(BizConstant.BizOpcode.BIZ_BUSSINESS_ORDER);
		getDAO().save(custOrder);
		return custOrder;
	}

	/**
	 * 销售单查询
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queBizBusiness(QueBizBusinessReq req, QueBizBusinessResp resp) throws Exception {

		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		QueBizBusinessBossReq req2Boss = getQueBizBusinessBossReq(loginInfo, req);

		String jsonStr = getBossHttpInfOutput(req.getBizorderid(), BossInterfaceService.QUE_BIZ_BUSSINESS, req2Boss,
				loginInfo);
		handlerBusinessOrderStr(jsonStr, resp);
		return returnInfo;
	}

	private void handlerBusinessOrderStr(String jsonStr, QueBizBusinessResp resp) {
		List<BusinessOrderBean> datas = new Gson().fromJson(jsonStr, new TypeToken<List<BusinessOrderBean>>() {
		}.getType());
		resp.setDatas(datas);
	}

	private QueBizBusinessBossReq getQueBizBusinessBossReq(LoginInfo loginInfo, QueBizBusinessReq req) {
		QueBizBusinessBossReq req2Boss = new QueBizBusinessBossReq();
		req2Boss.setCustid(req.getCustid());
		req2Boss.setErector(loginInfo.getOperid());
		req2Boss.setErectordept(loginInfo.getDeptid());
		if(StringUtils.isNotBlank(req.getStatus())){
			req2Boss.setStatus(req.getStatus());
		}
		if(StringUtils.isNotBlank(req.getStime())){
			req2Boss.setStime(constructDate(req.getStime(), false));
		}
		if(StringUtils.isNotBlank(req.getEtime())){
			req2Boss.setEtime(constructDate(req.getEtime(), true));
		}
		return req2Boss;
	}
	
	private String constructDate(String time,boolean end){
		Date date = DateTimeUtil.parseDate(time, "yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if(end){
			calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
			calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		}else{
			calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
			calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		}
		return DateTimeUtil.formatDate(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 更改UE标识
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo bizUserUEChange(BizUserUEChangeReq req,BizUserUEChangeResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CustOrder custOrder = getCustOrder(req, loginInfo);
		BizUserUEChangeBossReq req2Boss = new BizUserUEChangeBossReq(req);
		String outputJsonStr = getBossHttpInfOutput(req.getBizorderid(),BossInterfaceService.BIZ_USERUE_CHANGE, req2Boss, loginInfo);
		custOrder.setOrderstatus(BizCustorderOrderstatus.SYNC);
		getDAO().update(custOrder);
		resp.setOrderid(custOrder.getId().toString());
		return returnInfo;
	}
	
	private CustOrder getCustOrder(BizUserUEChangeReq req,LoginInfo loginInfo) throws Exception{
		CustOrder custOrder = new CustOrder();
		custOrder.setCity(loginInfo.getCity());
		custOrder.setId(Long.parseLong(req.getBizorderid()));
		if (StringUtils.isNotBlank(req.getCustid())) {
			custOrder.setCustid(Long.parseLong(req.getCustid()));
		}
		custOrder.setName(req.getCustname());
		custOrder.setAreaid(loginInfo.getAreaid());
		custOrder.setOperator(loginInfo.getOperid());
		custOrder.setOprdep(loginInfo.getDeptid());
		custOrder.setPortalOrder(null);
		custOrder.setOptime(new Date());
		custOrder.setOrderstatus(BizCustorderOrderstatus.INIT);
		custOrder.setOpcode(BizConstant.BizOpcode.BIZ_USERUE_CHANGE);
		custOrder.setSyncmode(BizCustorderSyncmode.SYNC);
		custOrder.setOrdercode(pubService.getOrderCode());
		if(StringUtils.isNotBlank(req.getAddress())){
			custOrder.setAddr(req.getAddress());
		}
		getDAO().save(custOrder);
		return custOrder;
	}
	
	
	/**
	 * 查询是否显示宽带帐号密码
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queCmPwdShow(QueCmPwdShowResp resp) throws Exception{
		
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		
		Rule rule = new Rule();
		rule.setRule("CITY_SHOW_CMPWD");
		
		List<Rule> rules = getDAO().find(rule);
		if(rules == null || rules.isEmpty() || rules.get(0) == null){return returnInfo;}
		
		rule = rules.get(0);
		if(StringUtils.isBlank(rule.getValue())){
			return returnInfo;
		}
		if(rule.getValue().contains(loginInfo.getCity())){
			resp.setShow(true);
		}
		return returnInfo;
	}

	/**
	 * VLAN号录入接口
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo bizVlanNum(BizVlanNumReq req, BizVlanNumResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		BizVlanNumBossReq req2Boss = new BizVlanNumBossReq(req);
		String result = getBossHttpInfOutput(req.getBizorderid(), BossInterfaceService.BIZ_VLAN_NUM, req2Boss, loginInfo);

		BizVlanNumResp bossResp = (BizVlanNumResp) BeanUtil.jsonToObject(result, BizVlanNumResp.class);
		BeanUtils.copyProperties(resp, bossResp);

		if (StringUtils.isNotBlank(req.getVlanNum())) {
			saveCustOrder4VlanNum(req, loginInfo, BizConstant.BizOpcode.BIZ_VLAN_NUM);
		}
		return returnInfo;
	}

	public CustOrder saveCustOrder4VlanNum(BizVlanNumReq req, LoginInfo loginInfo, String opcode) throws Exception {
		CustOrder bizCustOrder = new CustOrder();
		bizCustOrder.setAddr(req.getAddr());
		bizCustOrder.setAreaid(loginInfo.getAreaid());
		bizCustOrder.setCity(loginInfo.getCity());
		bizCustOrder.setBossserialno(null);
		bizCustOrder.setCanceltime(null);
		bizCustOrder.setCustid(Long.valueOf(req.getCustid()));
		bizCustOrder.setDescrip(null);
		bizCustOrder.setLockoper(null);
		bizCustOrder.setName(req.getName());
		bizCustOrder.setOpcode(opcode);
		bizCustOrder.setOperator(loginInfo.getOperid());
		bizCustOrder.setOprdep(loginInfo.getDeptid());
		bizCustOrder.setOptime(new Date());
		bizCustOrder.setOrdercode(pubService.getOrderCode().toString());
		bizCustOrder.setId(Long.valueOf(req.getBizorderid()));
		bizCustOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.SYNC);
		bizCustOrder.setSyncmode(BizConstant.BizCustorderSyncmode.SYNC);
		bizCustOrder.setPortalOrder(null);
		bizCustOrder.setVerifyphone(null);
		getDAO().save(bizCustOrder);
		return bizCustOrder;
	}
	
}
