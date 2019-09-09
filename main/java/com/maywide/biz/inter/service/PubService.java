package com.maywide.biz.inter.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.googlecode.jsonplugin.JSONUtil;
import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.cons.BizConstant.BizGridObjObjtype;
import com.maywide.biz.cons.BizConstant.SMSTemplateCode;
import com.maywide.biz.core.entity.DevuseRule;
import com.maywide.biz.core.entity.RemotecallLog;
import com.maywide.biz.core.entity.Rule;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.core.servlet.SpringBeanUtil;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.biz.inter.constant.QueConstant;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.entity.BizManagerSign;
import com.maywide.biz.inter.manager.ExpriringManager;
import com.maywide.biz.inter.pojo.QueryCustInfoReq;
import com.maywide.biz.inter.pojo.QueryUserInfoReq;
import com.maywide.biz.inter.pojo.authrandomcode.AuthRandomCodeInterReq;
import com.maywide.biz.inter.pojo.authrandomcode.AuthRandomCodeInterResp;
import com.maywide.biz.inter.pojo.authrandomcode.AuthRandomCodeUapReq;
import com.maywide.biz.inter.pojo.authrandomcode.AuthRandomCodeUapResp;
import com.maywide.biz.inter.pojo.bizchgoperatorpwd.BizChgOperatorPwdBossReq;
import com.maywide.biz.inter.pojo.bizchgoperatorpwd.BizChgOperatorPwdInterReq;
import com.maywide.biz.inter.pojo.bizchgoperatorpwd.BizChgOperatorPwdInterResp;
import com.maywide.biz.inter.pojo.bizfeedback.BizFeedbackInterReq;
import com.maywide.biz.inter.pojo.bizfeedback.BizFeedbackInterResp;
import com.maywide.biz.inter.pojo.bizupdatecmpwd.BizUpdateCmPwdBossReq;
import com.maywide.biz.inter.pojo.bizupdatecmpwd.BizUpdateCmPwdReq;
import com.maywide.biz.inter.pojo.callcenter.orderdetail.*;
import com.maywide.biz.inter.pojo.chgportalpwd.ChgPortalPwdReq;
import com.maywide.biz.inter.pojo.common.GridBean;
import com.maywide.biz.inter.pojo.common.SqlStrBean;
import com.maywide.biz.inter.pojo.createcust.*;
import com.maywide.biz.inter.pojo.deleteorder.DeleteOrderBossReq;
import com.maywide.biz.inter.pojo.editcust.*;
import com.maywide.biz.inter.pojo.grCode.QueGrCodeReq;
import com.maywide.biz.inter.pojo.grCode.QueGrcodeResp;
import com.maywide.biz.inter.pojo.invalidrandomcode.InvalidRandomCodeInterReq;
import com.maywide.biz.inter.pojo.invalidrandomcode.InvalidRandomCodeInterResp;
import com.maywide.biz.inter.pojo.invalidrandomcode.InvalidRandomCodeUapReq;
import com.maywide.biz.inter.pojo.invalidrandomcode.InvalidRandomCodeUapResp;
import com.maywide.biz.inter.pojo.oss.order.OssOrderDetailResponse;
import com.maywide.biz.inter.pojo.oss.order.OssOrderRet;
import com.maywide.biz.inter.pojo.oss.order.OssOrderTasks;
import com.maywide.biz.inter.pojo.oss.order.WorkAssignServiceOrderListRequest;
import com.maywide.biz.inter.pojo.queOrderPkgInfo.QueOrderPkgInfoReq;
import com.maywide.biz.inter.pojo.queOrderPkgInfo.QueOrderPkgInfoResp;
import com.maywide.biz.inter.pojo.queapplyknowdet.ApplyKnowDetBO;
import com.maywide.biz.inter.pojo.queapplyknowdet.QueApplyKnowDetInterReq;
import com.maywide.biz.inter.pojo.queapplyknowdet.QueApplyKnowDetInterResp;
import com.maywide.biz.inter.pojo.quearreardet.QueArreardetBossReq;
import com.maywide.biz.inter.pojo.quearreardet.QueArreardetBossResp;
import com.maywide.biz.inter.pojo.quearreardet.QueArreardetInterReq;
import com.maywide.biz.inter.pojo.quearreardet.QueArreardetInterResp;
import com.maywide.biz.inter.pojo.quecustbank.*;
import com.maywide.biz.inter.pojo.quecustorder.*;
import com.maywide.biz.inter.pojo.quedata.QueDataInterReq;
import com.maywide.biz.inter.pojo.quedata.QueDataInterResp;
import com.maywide.biz.inter.pojo.quegridmanager.GridmanagersBO;
import com.maywide.biz.inter.pojo.quegridmanager.QueGridmanagerInterReq;
import com.maywide.biz.inter.pojo.quegridmanager.QueGridmanagerInterResp;
import com.maywide.biz.inter.pojo.queproduct.QueProductResp;
import com.maywide.biz.inter.pojo.quereshouse.QueResHouseBossReq;
import com.maywide.biz.inter.pojo.quereshouse.QueResHouseBossResp;
import com.maywide.biz.inter.pojo.quereshouse.QueResHouseInterReq;
import com.maywide.biz.inter.pojo.quereshouse.QueResHouseInterResp;
import com.maywide.biz.inter.pojo.querycatalog.PersonCustPtReq;
import com.maywide.biz.inter.pojo.querycatalog.PersonCustPtResp;
import com.maywide.biz.inter.pojo.querycatalog.QueCustMarktInfoReq;
import com.maywide.biz.inter.pojo.querycatalog.QueCustMarktInfoResp;
import com.maywide.biz.inter.pojo.querysalespkgknow.*;
import com.maywide.biz.inter.pojo.queservstinfo.*;
import com.maywide.biz.inter.pojo.quetempopenplan.*;
import com.maywide.biz.inter.pojo.queuserpkg.*;
import com.maywide.biz.inter.pojo.queuserprd.QueUserPrdBossReq;
import com.maywide.biz.inter.pojo.queuserprd.QueUserPrdBossResp;
import com.maywide.biz.inter.pojo.queuserprd.QueUserPrdInterReq;
import com.maywide.biz.inter.pojo.queuserprd.QueUserPrdInterResp;
import com.maywide.biz.inter.pojo.record.RecordOpertReq;
import com.maywide.biz.inter.pojo.scancodeandsign.ScanCodeAndSignReq;
import com.maywide.biz.inter.pojo.scancodeandsign.ScanCodeAndSignResp;
import com.maywide.biz.inter.pojo.sendrandomcode.*;
import com.maywide.biz.inter.pojo.tempAddress.TempAdrReq;
import com.maywide.biz.inter.pojo.wechatBinding.WechatBindingReq;
import com.maywide.biz.inter.pojo.wechatBinding.WechatBindingResp;
import com.maywide.biz.market.entity.*;
import com.maywide.biz.market.pojo.BizGridHouse;
import com.maywide.biz.portal.service.Am5Service;
import com.maywide.biz.prd.entity.SalespkgKnow;
import com.maywide.biz.prd.entity.Tmpbuildaddr;
import com.maywide.biz.prv.entity.OperAction;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.biz.ws.callcenter.WorkAssignService;
import com.maywide.biz.ws.callcenter.WorkAssignServiceServiceLocator;
import com.maywide.biz.ws.oss.Channel2IomCallService;
import com.maywide.biz.ws.oss.Channel2IomCallServiceServiceLocator;
import com.maywide.core.cons.SysConstant;
import com.maywide.core.dao.support.Page;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.security.remote.BossHttpClientImpl;
import com.maywide.core.security.remote.socket.Environment;
import com.maywide.core.security.remote.socket.XmlConverter;
import com.maywide.core.util.*;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional(rollbackFor = Exception.class)
public class PubService extends CommonService {
	@Autowired
	private ParamService paramService;
	@Autowired
	private InterPrdService interPrdService;
	@Autowired
	private BizParamCfgService bizParamCfgService;
	@Autowired
	private RuleService ruleService;
	@Autowired
	private Am5Service am5Service;
	@Autowired
	private PrintService printService;

	/**
	 * 新建客户接口
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo createCust(CreateCustInterReq req, CreateCustInterResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getAreaid(), "匹配条件[areaid]不能为空");
		CheckUtils.checkEmpty(req.getCardno(), "匹配条件[cardno]不能为空");
		CheckUtils.checkEmpty(req.getCardtype(), "匹配条件[cardtype]不能为空");
		CheckUtils.checkEmpty(req.getName(), "匹配条件[name]不能为空");
		if (StringUtils.isBlank(req.getMobile()) && StringUtils.isBlank(req.getPhone())) {
			throw new BusinessException("匹配条件[mobile]和[phone]不能同时为空");
		}
		checkConditions(req.getCardtype(), req.getCardno());

		// 将请求做一下转换，并赋默认值
		CreateCustBossReq req2Boss = getCreateCustReq2Boss(req);

		String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
				BizConstant.BossInterfaceService.BIZ_CREATECUSTINF, req2Boss, loginInfo);

		copyBossResp2InterResp4createCust(resp, bossRespOutput);

		return returnInfo;
	}

	private void checkConditions(String cartType, String cardno) throws Exception {
		if (cartType.equals("1")) {
			if (!isCardId(cardno)) {
				CheckUtils.checkNull(null, "身份证编号不正确");
			}
		}
	}

	private void copyBossResp2InterResp4createCust(CreateCustInterResp resp, String jsonStr) throws Exception {

		CreateCustBossResp bossResp = (CreateCustBossResp) BeanUtil.jsonToObject(jsonStr, CreateCustBossResp.class);

		resp.setAreaid(bossResp.getAreaid());
		resp.setCardno(bossResp.getCardno());
		resp.setCardtype(bossResp.getCardtype());
		resp.setCity(bossResp.getCity());
		resp.setCustid(bossResp.getId());
		resp.setCustname(bossResp.getName());
		resp.setMobile(bossResp.getMobile());
		resp.setPhone(bossResp.getPhone());
		resp.setCredit(bossResp.getCredit());
		resp.setCusttype(bossResp.getCusttype());
		resp.setMagchl(bossResp.getMagchl());
		resp.setMarkno(bossResp.getMarkno());
		resp.setSecsubtype(bossResp.getSecsubtype());
		resp.setService(bossResp.getService());
		resp.setServkind(bossResp.getServkind());
		resp.setSubtype(bossResp.getSubtype());

		setPwdSensitiveInfo4CreatCust(resp);

	}

	private CreateCustBossReq getCreateCustReq2Boss(CreateCustInterReq req) {
		CustInfoBO custInfo = genCustInfo4createCust(req);
		CreateCustBossReq createCustBossReq = new CreateCustBossReq();
		createCustBossReq.setSysCust(custInfo);

		return createCustBossReq;
	}

	private EditCustBossReq getEditCustReq2Boss(EditCustInterReq req, String serialno) throws Exception {
		CustInfoBO custInfo = genCustInfo4EditCust(req);// genCustInfo4createCust(req);
		EditCustBossReq EditCustBossReq = new EditCustBossReq();
		EditCustBossReq.setSysCust(custInfo);
		EditCustBossReq.setSerialno(serialno);

		return EditCustBossReq;
	}

	private CustInfoBO genCustInfo4EditCust(EditCustInterReq req) throws Exception {
		// 生成客户信息，以便适配boss接口
		CustInfoBO custInfo = new CustInfoBO();
		// org.apache.commons.beanutils.PropertyUtils po = new
		// org.apache.commons.beanutils.PropertyUtils();
		// po.copyProperties(req, custInfo);

		custInfo.setId(Long.valueOf(req.getId()));
		custInfo.setCusttype(req.getCusttype());
		custInfo.setSubtype(req.getSubtype());
		custInfo.setSecsubtype(req.getSecsubtype());
		custInfo.setCardno(req.getCardno());
		custInfo.setCardtype(req.getCardtype());
		custInfo.setName(req.getName());

		custInfo.setPhone(req.getPhone());
		custInfo.setMobile(req.getMobile());
		custInfo.setLinkman(req.getLinkman());
		custInfo.setEmail(req.getEmail());
		custInfo.setLinkaddr(req.getLinkaddr());
		custInfo.setMemo(req.getMemo());

		CustPubInfoInterInfo pubInfo = new CustPubInfoInterInfo();
		pubInfo.setId(Long.valueOf(req.getId()));
		pubInfo.setInterest(req.getInterest());
		pubInfo.setBirth(req.getBirth());
		pubInfo.setGrade(req.getGrade());
		pubInfo.setTrade(req.getTrade());
		pubInfo.setOccupation(req.getOccupation());
		pubInfo.setCompany(req.getCompany());

		custInfo.setPubinfo(pubInfo);

		// CustAttrInterInfo attr = new CustAttrInterInfo();
		// attr.setAttrcode("CUSTDEGREE");// 客户身份 CUST_DEGREE
		// custInfo.getAttrs().add(attr);

		return custInfo;
	}

	private CustInfoBO genCustInfo4createCust(CreateCustInterReq req) {
		// 生成客户信息，以便适配boss接口
		CustInfoBO custInfo = new CustInfoBO();
		custInfo.setAreaid(Long.valueOf(req.getAreaid()));
		custInfo.setCardno(req.getCardno());
		custInfo.setCardtype(req.getCardtype());
		custInfo.setName(req.getName());
		custInfo.setLinkman(req.getName());
		custInfo.setPhone(req.getPhone());
		custInfo.setMobile(req.getMobile());
		custInfo.setLinkaddr(req.getLinkaddr());
		//ywp 增加一个custstatus
		custInfo.setCuststatus(req.getCuststatus());
		// custInfo.setCity(city);

		custInfo.setCusttype(BizConstant.CustType.CUST_TYPE_PERSON);
		custInfo.setSubtype(BizConstant.CustSubType.CUST_SUB_TYPE_NORMAL);
		custInfo.setSecsubtype(BizConstant.CustSecType.SYS_CUST_SEC_TYPE_PUB);

		custInfo.setMarkno("0");

		CustPubInfoInterInfo pubInfo = new CustPubInfoInterInfo();
		custInfo.setPubinfo(pubInfo);

		CustAttrInterInfo attr = new CustAttrInterInfo();
		attr.setAttrcode("CUSTDEGREE");// 客户身份 CUST_DEGREE
		attr.setAttrruleid(130l);// 普通用户
		custInfo.getAttrs().add(attr);

		return custInfo;
	}

	/**
	 * 查询客户信息接口
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo queryCustInfo(QueryCustInfoReq req) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getIdentifierType(), "匹配条件[identifierType]不能为空");
		CheckUtils.checkEmpty(req.getIdentifier(), "匹配值[identifier]不能为空");
		//		CheckUtils.checkEmpty(req.getCity(), "操作员所在城市编码[city]不能为空");

		if(StringUtils.isNotBlank(req.getIsQueGrp())){
			CheckUtils.checkEmpty(req.getAreaids(), "查询集客信息,片区不能为空!");
		}

		JSONObject requestContent = new JSONObject();
		requestContent.put("pagesize", "10");
		if (StringUtils.isNotBlank(req.getPagesize())) {
			requestContent.put("pagesize", req.getPagesize());
		}
		if (StringUtils.isNotBlank(req.getCurrentPage())) {
			requestContent.put("currentPage", req.getCurrentPage());
		}

		requestContent.put("identifierType", req.getIdentifierType());
		requestContent.put("identifier", req.getIdentifier());
		requestContent.put("city", loginInfo.getCity());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.setDateFormat(dateFormat);

		// System.out.println("=========>bossReq===>" +
		// requestContent.toString());

		String serverCode = "";
		if (StringUtils.isNotBlank(SysConfig.getServiceCity())) {
			serverCode = BizConstant.ServerCityBossInterface.QUE_CUSTINFO;
		} else {
			serverCode = BizConstant.BossInterfaceService.QUE_CUSTINFO;
		}

		String response = bossHttpClientService.requestPost(req.getBizorderid(), serverCode, requestContent.toString(),
				loginInfo);
		if (StringUtils.isEmpty(response)) {
			throw new BusinessException("BOSS接口调用出错，没有返回数据");
		}

		JsonNode nodeTree = objectMapper.readTree(response);
		parseBossResponse(nodeTree, serverCode);

		returnInfo.setMessage(nodeTree.get("output").toString());

		return returnInfo;
	}

	/**
	 * 查询用户信息接口
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo queryUserInfo(QueryUserInfoReq req) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getIcno(), "智能卡号[icno]不能为空");

		JSONObject requestContent = new JSONObject();
		requestContent.put("pagesize", "10");

		requestContent.put("icno", req.getIcno());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.setDateFormat(dateFormat);

		String response = bossHttpClientService.requestPost(req.getBizorderid(),
				BizConstant.BossInterfaceService.QUE_USERINFO, requestContent.toString(), loginInfo);
		if (StringUtils.isEmpty(response)) {
			throw new BusinessException("BOSS接口调用出错，没有返回数据");
		}

		JsonNode nodeTree = objectMapper.readTree(response);
		parseBossResponse(nodeTree, BizConstant.BossInterfaceService.QUE_USERINFO);

		returnInfo.setMessage(nodeTree.get("output").toString());

		return returnInfo;
	}

	/**
	 * 查询点通方案接口
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo queTempopenPlan(QueTempopenPlanInterReq req, QueTempopenPlanInterResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");

		// 将请求做一下转换，并赋默认值
		QueTempopenPlanBossReq req2Boss = getQueTempopenPlanReq2Boss(req);

		String serverCode = "";
		if (StringUtils.isNotBlank(SysConfig.getServiceCity())) {
			serverCode = BizConstant.ServerCityBossInterface.QUE_TEMPOPENPLAN;
		} else {
			serverCode = BizConstant.BossInterfaceService.QUE_TEMPOPENPLAN;
		}

		String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(), serverCode, req2Boss, loginInfo);

		copyBossResp2InterResp4queTempopenPlan(resp, bossRespOutput, req);

		return returnInfo;
	}

	private void copyBossResp2InterResp4queTempopenPlan(QueTempopenPlanInterResp resp, String jsonStr,
			QueTempopenPlanInterReq req) throws Exception {

		QueTempopenPlanBossResp bossResp = (QueTempopenPlanBossResp) BeanUtil.jsonToObject(jsonStr,
				QueTempopenPlanBossResp.class);

		resp.setCurrentPage(bossResp.getCurrentPage());
		resp.setPagesize(bossResp.getPagesize());
		resp.setTotalRecords(bossResp.getTotalRecords());
		if (StringUtils.isNotBlank(req.getPermark())) {
			List<TempopenPlanInfoBO> tmpDatas = new ArrayList<TempopenPlanInfoBO>();
			List<TempopenPlanInfoBO> datas = bossResp.getPlans();
			for (TempopenPlanInfoBO data : datas) {
				if (data.getPermark().equals(req.getPermark())) {
					tmpDatas.add(data);
				}
			}
			resp.setPlans(tmpDatas);
		} else {
			resp.setPlans(bossResp.getPlans());
		}

	}

	private QueTempopenPlanBossReq getQueTempopenPlanReq2Boss(QueTempopenPlanInterReq req) {
		QueTempopenPlanBossReq queTempopenPlanBossReq = new QueTempopenPlanBossReq();
		queTempopenPlanBossReq.setAreaid(req.getAreaid());
		queTempopenPlanBossReq.setPagesize(req.getPagesize());
		queTempopenPlanBossReq.setCurrentPage(req.getCurrentPage());

		return queTempopenPlanBossReq;
	}

	/**
	 * 查询客户银行接口
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo queCustbank(QueCustbankInterReq req, QueCustbankInterResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getCustid(), "匹配条件[custid]不能为空");

		// 将请求做一下转换，并赋默认值
		QueCustbankBossReq req2Boss = getQueCustbankReq2Boss(req);

		String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.QUE_CUSTBANK,
				req2Boss, loginInfo);

		copyBossResp2InterResp4queCustbank(resp, bossRespOutput);

		return returnInfo;
	}

	private void copyBossResp2InterResp4queCustbank(QueCustbankInterResp resp, String jsonStr) throws Exception {

		QueCustbankBossResp bossResp = (QueCustbankBossResp) BeanUtil.jsonToObject(jsonStr, QueCustbankBossResp.class);

		// 因为字段是一样的，这里先偷懒一下，
		// 后面如果接口对外字段和boss返回字段不一致，则自行写使用set方法设置值
		BeanUtils.copyProperties(resp, bossResp);
		// resp.setAcctkind(bossResp.getAcctkind());

		// 再这里统一对敏感信息进行加密
		setPwdSensitiveInfo4queCustbankresp(resp);
	}

	private void setPwdSensitiveInfo4queCustbankresp(QueCustbankInterResp resp) throws Exception {
		if (null == resp) {
			return;
		}
		List<CustBankInfoBO> custBanks = resp.getCustBanks();

		if (BeanUtil.isListNull(custBanks)) {
			return;
		}

		for (CustBankInfoBO bank : custBanks) {
			//			bank.setPwdacctno(replaceSensitiveInfo(bank.getAcctno(), "acctno"));
			bank.setPwdacctno(hideCardNo(bank.getAcctno()));
			bank.setPwdacctname(replaceSensitiveInfo(bank.getAcctname(), "custname"));

		}

	}

	private QueCustbankBossReq getQueCustbankReq2Boss(QueCustbankInterReq req) {
		QueCustbankBossReq queCustbankBossReq = new QueCustbankBossReq();
		queCustbankBossReq.setCustid(req.getCustid());

		return queCustbankBossReq;
	}

	/**
	 * 查询参数接口
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo getData(QueDataInterReq req, QueDataInterResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		CheckUtils.checkNull(req, "请求信息不能为空");

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		if (req.getComposes() != null && req.getComposes().size() == 2) {// 用于临时加载组合城市支付方式
			List<PrvSysparam> paramdataList1 = paramService.getData(req.getComposes().get(0).getGcode(),
					req.getComposes().get(0).getMcode(), req.getComposes().get(0).getScope(), null);
			List<PrvSysparam> paramdataList2 = paramService.getData(req.getComposes().get(1).getGcode(),
					req.getComposes().get(1).getMcode(), req.getComposes().get(1).getScope(), null);
			ArrayList<PrvSysparam> payList = new ArrayList<PrvSysparam>();
			queOperPayWay(payList);
			if (payList != null && payList.size() > 0) {
				resp.setParamdata(payList);
			} else {
				if (paramdataList1.size() > 0 && !paramdataList2.isEmpty()) {
					List<PrvSysparam> resultData = new ArrayList<PrvSysparam>();
					String datas = paramdataList2.get(0).getData() + "~";
					for (PrvSysparam parameter : paramdataList1) {
						if (datas.indexOf(parameter.getMcode() + "~") >= 0) {
							resultData.add(parameter);
						}
					}
					resp.setParamdata(resultData);
				} else {
					resp.setParamdata(paramdataList1);
				}
			}
		} else {
			List paramdataList = paramService.getData(req.getGcode(), req.getMcode(), req.getScope(), req.getMcodeSeparator());
			if (req.isDef() && StringUtils.isNotBlank(req.getMcode())
					&& (paramdataList == null || paramdataList.size() == 0)) {
				paramdataList = paramService.getData(req.getGcode(), "*", req.getScope(), null);
			}
			resp.setParamdata(paramdataList);
		}
		handlerDataByRule(resp, loginInfo, req.getGcode());
		return returnInfo;
	}

	private void handlerDataByRule(QueDataInterResp resp, LoginInfo loginInfo, String gcode) throws Exception {
		if (StringUtils.isBlank(gcode))
			return;
		if (StringUtils.isBlank(SysConfig.getServiceCity())) {
			if (resp.getParamdata() != null && !resp.getParamdata().isEmpty()) {
				if (gcode.equals("BIZ_DEV_RESON")) {
					handlerReasonData(resp.getParamdata(), loginInfo);
				}
			}
		}
	}

	private void handlerReasonData(List<PrvSysparam> paramList, LoginInfo loginInfo) throws Exception {
		List params = new ArrayList();
		params.add("7");
		params.add(loginInfo.getCity());
		String sql = "SELECT distinct city FROM biz_devuse_rule where reason = ? and city = ?";
		List<DevuseRule> datas = getDAO().find(sql, DevuseRule.class, params.toArray());
		if (datas == null || datas.isEmpty()) {
			int position = -1;
			for (PrvSysparam param : paramList) {
				if (param.getMcode().equals("7")) {
					position = paramList.indexOf(param);
					break;
				}
			}
			if (position != -1) {
				paramList.remove(position);
			}
		}
	}

	/**
	 * 查询用户信息(所有用户)接口
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo queServstInfo(QueServstInfoInterReq req, QueServstInfoInterResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkNull(req.getQueConditions(), "匹配条件[queConditions]不能为空");

		// 将请求做一下转换，并赋默认值
		QueServstInfoBossReq req2Boss = getQueServstInfoReq2Boss(req, loginInfo);

		String serverCode = "";
		if (StringUtils.isNotBlank(SysConfig.getServiceCity())) {
			serverCode = BizConstant.ServerCityBossInterface.QUE_SERVSTINFO;
		} else {
			serverCode = BizConstant.BossInterfaceService.QUE_SERVSTINFO;
		}

		String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(), serverCode, req2Boss, loginInfo);

		copyBossResp2InterResp4queServstInfo(resp, bossRespOutput);

		return returnInfo;
	}

	private QueServstInfoBossReq getQueServstInfoReq2Boss(QueServstInfoInterReq req, LoginInfo loginInfo)
			throws Exception {
		QueServstInfoBossReq queServstInfoBossReq = new QueServstInfoBossReq();
		queServstInfoBossReq.setCity(loginInfo.getCity());
		queServstInfoBossReq.setCurrentPage(req.getCurrentPage());
		queServstInfoBossReq.setPagesize(req.getPagesize());
		queServstInfoBossReq.setIsNotQueServInfo(req.getIsNotQueServInfo());

		List<QueConditionsBO> queConditionList = req.getQueConditions();
		if (null == queConditionList) {
			queConditionList = new ArrayList();
		}

		Map<String, String> conditionMap = procQueConditionList2Map(queConditionList, loginInfo);

		if (StringUtils.isBlank(conditionMap.get(QueConstant.QueKeywordtype.CUST_TYPE))) {
			// 如果没传客户类型条件，则默认为查个人客户

			conditionMap.put(QueConstant.QueKeywordtype.CUST_TYPE, BizConstant.CustType.CUST_TYPE_PERSON);

		} else {
			// 如果客户类型条件为值为"*",则表示查全部客户，在这里去掉该条件，以实现在boss查所有类型客户
			if ("*".endsWith(conditionMap.get(QueConstant.QueKeywordtype.CUST_TYPE))) {
				conditionMap.remove(QueConstant.QueKeywordtype.CUST_TYPE);
			}
		}
		if (StringUtils.isBlank(SysConfig.getServiceCity())) {
			SqlStrBean bean = getSqlStrBean(loginInfo);
			if (StringUtils.isNotBlank(bean.getFild()) && bean.getFild().equalsIgnoreCase("y")) {
				if (bean.getPrevid() != -1) {
					queServstInfoBossReq.setAreaid(loginInfo.getAreaid());
				}

			} else {
				// 如果是片区型的网格，可在boss通过片区查询条件进行权限控制
				addGrid(loginInfo, conditionMap, req.getNotGrid());
			}
		} else {
			addGrid(loginInfo, conditionMap, req.getNotGrid());
		}

		queConditionList = procQueConditionMap2List(conditionMap, loginInfo);

		queServstInfoBossReq.setQueConditions(queConditionList);

		return queServstInfoBossReq;
	}

	private void addGrid(LoginInfo loginInfo, Map<String, String> conditionMap, String gridNotice) throws Exception {
		String gridobjType = bizParamCfgService.getGridobjTypeByCfg(loginInfo);
		if (BizConstant.BizGridObjObjtype.PATCH.equals(gridobjType)) {

			String patchidstr = getPatchids(loginInfo);
			conditionMap.put(QueConstant.QueKeywordtype.PATCHIDS, patchidstr);

		}
		if (BizConstant.BizGridObjObjtype.ADDR.equals(gridobjType)) {

			if (StringUtils.isBlank(gridNotice)) {
				String patchidstr = getGridcodes(loginInfo);
				conditionMap.put(QueConstant.QueKeywordtype.GRIDCODES, patchidstr);
			}
		}
	}

	private SqlStrBean getSqlStrBean(LoginInfo loginInfo) throws Exception {
		String sql = "SELECT DISTINCT(B.previd) previd,A.ISCGRID fild FROM BIZ_GRID_MANAGER A,BIZ_GRID_INFO B WHERE OPERID = ? AND A.GRIDID = B.GRIDID AND B.CITY = ?";
		List<SqlStrBean> datas = getDAO().find(sql, SqlStrBean.class, loginInfo.getOperid(), loginInfo.getCity());
		CheckUtils.checkNull(datas, "该操作员未进行网格权限配置,请联系管理员进行配置");
		CheckUtils.checkNull(datas.get(0), "该操作员未进行网格权限配置,请联系管理员进行配置");
		return datas.get(0);
	}

	private String getGridCodes(GridBean bean) throws Exception {

		StringBuffer gridcodes = new StringBuffer();

		List params = new ArrayList();
		params.add(bean.getOperid());
		params.add(bean.getAreaid());

		StringBuffer sb = new StringBuffer();
		sb.append(
				"		SELECT DISTINCT(b.gridid) gridid, b.gridcode gridcode,b.gtype gtype,b.city,a.operid operid,b.areaid areaid");
		sb.append("		FROM biz_grid_manager a,biz_grid_info b");
		sb.append("		WHERE a.gridid = b.gridid");
		sb.append("		AND a.operid = ?");
		sb.append("		AND b.areaid = ?");
		if (StringUtils.isNotBlank(bean.getCity())) {
			params.add(bean.getCity());
			sb.append("		AND b.city = ?");
		}
		/*
		 * if(StringUtils.isNotBlank(bean.getGtype())){
		 * params.add(bean.getGtype()); sb.append("		AND b.gtype = ?"); }
		 */
		if (bean.getGridid() != null) {
			params.add(bean.getGridid());
			sb.append("		AND b.previd = ?");
		}
		List<GridBean> datas = getDAO().find(sb.toString(), GridBean.class, params.toArray());
		if (datas != null) {
			for (GridBean data : datas) {
				if (data.getGtype().equals(BizGridObjObjtype.PATCH)) {
					gridcodes.append(getChildGridCode(data.getAreaid(), data.getCity(), data.getGridid()));
				} else {
					gridcodes.append(data.getGcode() + ",");
				}
			}
		}
		String codes = gridcodes.toString();
		if (codes.endsWith(",")) {
			codes = codes.substring(0, codes.length() - 2);
		}
		return codes;
	}

	private String getChildGridCode(Long areaid, String city, Long gridid) throws Exception {

		StringBuffer gridcodes = new StringBuffer();

		List params = new ArrayList();
		params.add(areaid);
		params.add(city);
		params.add(gridid);

		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT DISTINCT (b.gridid) gridid, b.gridcode gcode, b.gtype gtype,b.city,b.areaid areaid ");
		sb.append("		FROM   biz_grid_info b");
		sb.append("		WHERE b.areaid = ?");
		sb.append("		AND b.city = ?");
		sb.append("		AND b.previd = ?");

		List<GridBean> datas = getDAO().find(sb.toString(), GridBean.class, params.toArray());
		if (datas != null && !datas.isEmpty()) {
			for (GridBean bean : datas) {
				if (bean.getGtype().equals(BizGridObjObjtype.ADDR)) {
					gridcodes.append(bean.getGcode() + ",");
				} else {
					gridcodes.append(getChildGridCode(bean.getAreaid(), bean.getCity(), bean.getGridid()));
				}
			}
		}

		return gridcodes.toString();
	}

	private List<QueConditionsBO> procQueConditionMap2List(Map<String, String> conditionMap, LoginInfo loginInfo) {
		List<QueConditionsBO> retList = new ArrayList();
		if (null == conditionMap || conditionMap.size() <= 0 || conditionMap.isEmpty()) {
			return retList;
		}

		Set<String> keySet = conditionMap.keySet();
		if (null == keySet || keySet.size() <= 0 || keySet.isEmpty()) {
			return retList;
		}

		for (String key : keySet) {
			QueConditionsBO queCondition = new QueConditionsBO();
			queCondition.setKeywordtype(key);
			queCondition.setQuekeyword(conditionMap.get(key));

			retList.add(queCondition);
		}

		return retList;
	}

	private Map<String, String> procQueConditionList2Map(List<QueConditionsBO> paramList, LoginInfo loginInfo)
			throws Exception {

		Map<String, String> retMap = new HashMap<String, String>();
		if (BeanUtil.isListNull(paramList)) {
			return retMap;
		}

		for (QueConditionsBO queCondition : paramList) {
			if (StringUtils.isNotBlank(queCondition.getKeywordtype())
					|| StringUtils.isNotBlank(queCondition.getQuekeyword())) {
				if (queCondition.getKeywordtype().equals("auto")) {
					String type = autoGetKeywordType(queCondition.getQuekeyword());
					retMap.put(type, queCondition.getQuekeyword());
					continue;
				}
				retMap.put(queCondition.getKeywordtype(), queCondition.getQuekeyword());
			}
		}
		return retMap;
	}

	// custname,custmarkno,cardno,smno,stbno,cmmacno,ucmccno,address,phone
	private String autoGetKeywordType(String str) {
		String type = "";
		if (hasChinese(str)) {
			if (str.indexOf(" ") != -1 || str.length() > 4) {
				type = "address";
			} else {
				type = "custname";
			}
		} else if (str.length() < 5) {
			type = "custname";
		} else if (NumberUtils.isNumber(str)) {
			if (isMobileNumber(str)) {
				type = "linkphone";
			} else if (isPhoneNumber(str)) {
				type = "linkphone";
			} else if (isCardId(str)) {
				type = "cardno";
			} else {
				type = "smno";
			}
		} else if (isCardId(str)) {
			type = "cardno";
		} else if (isPhoneNumber(str)) {
			type = "linkphone";
		} else {
			type = "smno";
		}
		return type;
	}

	private boolean isCardId(String str) {
		String regEx = "^([1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3})|([1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx]))$";
		Pattern pat = Pattern.compile(regEx);
		Matcher matcher = pat.matcher(str);
		return matcher.matches();
	}

	private boolean hasChinese(String str) {
		String regEx = "[\u4e00-\u9fa5]";
		Pattern pat = Pattern.compile(regEx);
		Matcher matcher = pat.matcher(str);
		return matcher.matches();
	}

	private boolean isMobileNumber(String number) {
		String matcherStr = "^1[3|4|5|7|8][0-9]\\d{8}$";// ^1[3|4|5|8][0-9]\d{8}$
		return isTrue(matcherStr, number);
	}

	private boolean isPhoneNumber(String number) {
		String matchrStr = "^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?$";
		return isTrue(matchrStr, number);
	}

	private boolean isTrue(String matcher, String content) {
		Pattern pattern = Pattern.compile(matcher);
		Matcher m = pattern.matcher(content);
		return m.matches();
	}

	private void copyBossResp2InterResp4queServstInfo(QueServstInfoInterResp resp, String jsonStr) throws Exception {

		QueServstInfoBossResp bossResp = (QueServstInfoBossResp) BeanUtil.jsonToObject(jsonStr,
				QueServstInfoBossResp.class);

		// 如果字段是一样的，这里可以偷懒一下，
		// 后面如果接口对外字段和boss返回字段不一致，则自行写使用set方法设置值
		BeanUtils.copyProperties(resp, bossResp);
		// resp.setAcctkind(bossResp.getAcctkind());

		// 再这里统一对客户敏感信息进行加密
		setPwdSensitiveInfo4queServstInfo(resp);

	}

	private void setPwdSensitiveInfo4CreatCust(CreateCustInterResp resp) {
		if (SysConstant.SysCardType.IDCARD.equals(resp.getCardtype())) {
			resp.setPwdcardno(replaceSensitiveInfo(resp.getCardno(), "cardno"));
		}
		resp.setPwdcustname(replaceSensitiveInfo(resp.getCustname(), "custname"));
		resp.setPwdmobile(replaceSensitiveInfo(resp.getMobile(), "phone"));
		resp.setPwdphone(replaceSensitiveInfo(resp.getPhone(), "phone"));
	}

	private void setPwdSensitiveInfo4queServstInfo(QueServstInfoInterResp resp) throws Exception {
		if (null == resp) {
			return;
		}
		List<CustInfosBO> custs = resp.getCusts();
		if (BeanUtil.isListNull(custs)) {
			return;
		}

		for (CustInfosBO cust : custs) {

			// /*1.隐藏头部，2.隐藏尾部，3.隐藏中间,4.隐藏两头，5.保留头部，6.保留尾部，7.保留中间,8.保留两头*/
			// String proctype = "3";

			// String tmpstr = replaceSensitiveInfo(cust.getCardno(), "cardno");
			if (SysConstant.SysCardType.IDCARD.equals(cust.getCardtype())) {
				cust.setPwdcardno(replaceSensitiveInfo(cust.getCardno(), "cardno"));
			}
			cust.setPwdcustname(replaceSensitiveInfo(cust.getCustname(), "custname"));
			cust.setPwdlinkaddr(replaceSensitiveInfo(cust.getLinkaddr(), "addr"));
			cust.setPwdmobile(replaceSensitiveInfo(cust.getMobile(), "phone"));
			cust.setPwdphone(replaceSensitiveInfo(cust.getPhone(), "phone"));

			if (BeanUtil.isListNotNull(cust.getServs())) {
				for (ServstInfosBO serv : cust.getServs()) {
					serv.setPwdaddr(replaceSensitiveInfo(serv.getAddr(), "addr"));
				}
			}

		}
	}

	/**
	 * 查询网格经理接口
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo queGridmanager(QueGridmanagerInterReq req, QueGridmanagerInterResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求对象不能为空");
		CheckUtils.checkEmpty(req.getCity(), "匹配条件[city]不能为空");

		StringBuffer sql = new StringBuffer();
		List paramList = new ArrayList();

		// 拼sql
		// sql.append(" select areamger,areamgername,mobile,status,statusname
		// from ( ");
		sql.append(" SELECT * from( ");
		sql.append(" SELECT o.operid areamger, ");
		sql.append("        o.name areamgername, ");
		sql.append("        (SELECT i.callno FROM prv_operinfo i WHERE i.operid = o.operid) mobile, ");
		sql.append("        o.status, ");
		sql.append("        code2name(o.status,'PRV_USE_STATUS') statusname ");
		sql.append("   FROM prv_operator o ");
		sql.append("  WHERE 1 = 1 ");

		if (StringUtils.isNotBlank(req.getAreamgername())) {
			sql.append("    AND o.name LIKE ? ");
			paramList.add("%" + req.getAreamgername() + "%");
		}

		sql.append("    AND EXISTS (SELECT 1 ");
		sql.append("           FROM biz_grid_manager g, biz_grid_info gi ");
		sql.append("          WHERE g.GRIDID = gi.GRIDID ");
		sql.append("          AND g.operid = o.operid ");

		if (StringUtils.isNotBlank(req.getIsmain())) {
			sql.append("            AND g.ISMAIN = ? ");
			paramList.add(req.getIsmain());
		}
		if (StringUtils.isNotBlank(req.getGridid())) {
			sql.append("            AND g.GRIDID = ? ");
			paramList.add(req.getGridid());
		}
		if (StringUtils.isNotBlank(req.getGridname())) {
			sql.append("            AND g.gridname = ? ");
			paramList.add(req.getGridname());
		}
		sql.append("             ) ");

		sql.append(" ) v ");

		getDAO().clear();
		List<GridmanagersBO> gridmanagersList = getDAO().find(sql.toString(), GridmanagersBO.class,
				paramList.toArray());

		resp.setGridmanagers(gridmanagersList);

		return returnInfo;

	}

	/**
	 * 查询用户产品接口
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo queUserPrd(QueUserPrdInterReq req, QueUserPrdInterResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");
		if (StringUtils.isBlank(req.getCustid()) && StringUtils.isBlank(req.getServid())) {
			throw new BusinessException("查询用户产品：客户id和用户id不能同时为空");
		}

		// 将请求做一下转换，并赋默认值
		QueUserPrdBossReq req2Boss = getQueUserPrdReq2Boss(req);

		String serverCode = "";
		if (StringUtils.isNotBlank(SysConfig.getServiceCity())) {
			serverCode = BizConstant.ServerCityBossInterface.QUE_USERPRDINFO;
		} else {
			serverCode = BizConstant.BossInterfaceService.QUE_USERPRDINFO;
		}

		String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(), serverCode, req2Boss, loginInfo);

		copyBossResp2InterResp4queUserPrd(resp, bossRespOutput);

		return returnInfo;
	}

	private QueUserPrdBossReq getQueUserPrdReq2Boss(QueUserPrdInterReq req) {
		QueUserPrdBossReq queUserPrdBossReq = new QueUserPrdBossReq();
		queUserPrdBossReq.setCustid(req.getCustid());
		queUserPrdBossReq.setPermark(req.getPermark());
		queUserPrdBossReq.setPstatus(req.getPstatus());
		queUserPrdBossReq.setServid(req.getServid());

		return queUserPrdBossReq;
	}

	private void copyBossResp2InterResp4queUserPrd(QueUserPrdInterResp resp, String jsonStr) throws Exception {

		QueUserPrdBossResp bossResp = (QueUserPrdBossResp) BeanUtil.jsonToObject(jsonStr, QueUserPrdBossResp.class);

		// 如果字段是一样的，这里可以偷懒一下，
		// 后面如果接口对外字段和boss返回字段不一致，则自行写使用set方法设置值
		BeanUtils.copyProperties(resp, bossResp);
		// resp.setAcctkind(bossResp.getAcctkind());

	}

	/**
	 * 查询用户有销营销方案接口
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo queUserPkg(QueUserPkgInterReq req, QueUserPkgInterResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getServid(), "匹配条件[servid]不能为空");

		// 将请求做一下转换，并赋默认值
		QueUserPkgBossReq req2Boss = getQueUserPkgReq2Boss(req);
		String serverCode = "";
		if (StringUtils.isNotBlank(SysConfig.getServiceCity())) {
			serverCode = BizConstant.ServerCityBossInterface.QUE_USERPKGINFO;
		} else {
			serverCode = BizConstant.BossInterfaceService.QUE_USERPKGINFO;
		}
		String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(), serverCode, req2Boss, loginInfo);

		copyBossResp2InterResp4queUserPkg(resp, bossRespOutput, loginInfo);

		return returnInfo;
	}

	private QueUserPkgBossReq getQueUserPkgReq2Boss(QueUserPkgInterReq req) {
		QueUserPkgBossReq queUserPkgBossReq = new QueUserPkgBossReq();
		queUserPkgBossReq.setCustid(req.getCustid());
		queUserPkgBossReq.setServid(req.getServid());

		return queUserPkgBossReq;
	}

	private void copyBossResp2InterResp4queUserPkg(QueUserPkgInterResp resp, String jsonStr, LoginInfo loginInfo)
			throws Exception {

		QueUserPkgBossResp bossResp = (QueUserPkgBossResp) BeanUtil.jsonToObject(jsonStr, QueUserPkgBossResp.class);

		if (bossResp.getPkgs() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<UserPkgsBO> datas = new ArrayList<UserPkgsBO>();
			for (int i = 0; i < bossResp.getPkgs().size(); i++) {
				UserPkgsBO bo1 = new UserPkgsBO();
				UserPkgsBO bo = bossResp.getPkgs().get(i);
				bo1.setEtime(bo.getEtime());
				bo1.setIspostpone(bo.getIspostpone());
				bo1.setKeyno(bo.getKeyno());
				bo1.setOptime(bo.getOptime());
				bo1.setPayway(bo.getPayway());
				bo1.setPcode(bo.getPcode());
				bo1.setPkginsid(bo.getPkginsid());
				bo1.setPname(bo.getPname());
				bo1.setSalespkgid(bo.getSalespkgid());
				bo1.setServid(bo.getServid());
				bo1.setStdfee(bo.getStdfee());
				
				//不判断是否顺延和时间比对，有数据直接返回
				bo1.setPstatus("00");
				datas.add(bo1);
//				if (bo1.getIspostpone().equalsIgnoreCase("y")) {
//					bo1.setPstatus("00");
//					datas.add(bo1);
//				} else {
//					long endtime = sdf.parse(bo.getEtime()).getTime();
//					if (endtime > System.currentTimeMillis()) {
//						bo1.setPstatus("00");
//						datas.add(bo1);
//					}
//				}
			}
			bossResp.setPkgs(datas);
		}

		// 如果字段是一样的，这里可以偷懒一下，
		// 后面如果接口对外字段和boss返回字段不一致，则自行写使用set方法设置值
		BeanUtils.copyProperties(resp, bossResp);

	}

	/**
	 * 查询客户订单接口
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo queCustorder(QueCustorderInterReq req, QueCustorderInterResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求对象不能为空");
		if (StringUtils.isBlank(req.getCustorderid())) {
			CheckUtils.checkEmpty(req.getCity(), "匹配条件[city]不能为空");
		}

		StringBuffer sql = new StringBuffer();
		List paramList = new ArrayList();

		// 查询之前更新一下订单状态，将已过期的订单状态置为过期--TODO
		sql.setLength(0);
		paramList.clear();
		sql.append(" update biz_portal_order bpo ");
		sql.append("    set bpo.status = ? ");
		paramList.add(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_OVERDUE);
		sql.append("  where bpo.status = ? ");
		paramList.add(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_NOPAY);
		sql.append("    and bpo.createtime < curdate() ");

		getDAO().executeSql(sql.toString(), paramList.toArray());

		// 拼查询sql
		sql.setLength(0);
		paramList.clear();
		// sql.append(" select areamger,areamgername,mobile,status,statusname
		// from ( ");
		sql.append(" SELECT * from( ");
		sql.append(" SELECT t.orderid custorderid, ");
		sql.append("        t.ordercode, ");
		sql.append("        t.syncmode, ");
		sql.append("        code2name(t.syncmode, 'BIZ_CUSTORDER_SYNCMODE') syncmodename, ");
		sql.append("        t.bossserialno, ");
		sql.append("        t.orderstatus, ");
		sql.append("        t.verifyphone, ");
		sql.append("		t.bizmemo extras,");
		sql.append("        t.screenshots is not null and length(trim( t.screenshots))>0 hasscreenshot, ");
		sql.append("        code2name(t.orderstatus, 'BIZ_CUSTORDER_ORDERSTATUS') orderstatusname, ");
		sql.append("		(select t1.multipay  from biz_portal_order t1 WHERE t1.orderid = t.orderid) muiltpay,");
		sql.append("		(select t1.pay_fees  from biz_portal_order t1 WHERE t1.orderid = t.orderid) payFess,");
		sql.append("        (SELECT t1.status FROM biz_portal_order t1 WHERE t1.orderid = t.orderid) paystatus, ");
		sql.append("        (SELECT code2name(t1.status, 'BIZ_PORTAL_ORDER_STATUS') ");
		sql.append("           FROM biz_portal_order t1 ");
		sql.append("          WHERE t1.orderid = t.orderid and t1.status <> 3) paystatusname, ");
		sql.append("        (SELECT SUM(t1.fees) FROM biz_portal_order t1 WHERE t1.orderid = t.orderid) fees, ");
		sql.append("        (SELECT t1.payway FROM biz_portal_order t1 WHERE t1.orderid = t.orderid) payway, ");
		sql.append(
				"        code2name((SELECT t1.payway FROM biz_portal_order t1 WHERE t1.orderid = t.orderid), 'PORTAL_ORDER_PAYWAY') paywayname, ");
		// sql.append(" t.canceltime, ");
		sql.append("        DATE_FORMAT(t.canceltime, '%Y-%m-%d %T') canceltime, ");
		sql.append("        t.lockoper, ");
		sql.append("		(SELECT t1.resporderid FROM biz_portal_order t1 WHERE t1.orderid = t.orderid) resporderid,");
		sql.append("        (SELECT t1.name FROM prv_operator t1 WHERE t1.operid = t.LOCKOPER) lockopername, ");
		sql.append("        t.custid, ");
		sql.append("        t.NAME custname, ");
		sql.append("        t.opcode, ");
		sql.append("        code2name(t.OPCODE, 'BIZ_OPCODE') opcodename, ");
		// sql.append(" t.optime, ");
		sql.append("        DATE_FORMAT(t.optime, '%Y-%m-%d %T') optime, ");
		sql.append("        t.operator, ");
		sql.append("        (SELECT t1.name FROM prv_operator t1 WHERE t1.operid = t.OPERATOR) operatorname, ");
		sql.append("        t.oprdep, ");
		sql.append("        (SELECT t1.name FROM prv_department t1 WHERE t1.deptid = t.OPRDEP) oprdepname, ");
		sql.append("        t.areaid, ");
		sql.append("        (SELECT t1.name FROM PRV_AREA t1 WHERE t1.areaid = t.AREAID) areaname, ");
		sql.append("        t.gridid, ");
		sql.append("        (SELECT t1.gridname FROM biz_grid_info t1 WHERE t1.gridid = t.GRIDID) gridname, ");
		sql.append("        t.descrip, ");
		sql.append("        t.addr, ");
		sql.append("        t.city, ");
		sql.append("        code2name(t.CITY, 'PRV_CITY') cityname ");
		sql.append("		,t.printed");
		sql.append("		,t.printedinv ");
//		sql.append("		,t2.dev_str devStr,t2.sale_pkg_name pkgName");
		sql.append("   FROM biz_custorder t ");
		sql.append("  WHERE 1 = 1 ");
		sql.append(
				"	AND NOT EXISTS (SELECT 1 FROM biz_portal_order g WHERE t.ORDERID = g.orderid AND g.status = 3)");

		if (StringUtils.isNotBlank(req.getCity())) {
			sql.append("    AND t.city = ? ");
			paramList.add(req.getCity());
		}
		if (StringUtils.isNotBlank(req.getCustid())) {
			sql.append("    AND t.custid = ? ");
			paramList.add(req.getCustid());
		}
		if (StringUtils.isNotBlank(req.getCustorderid())) {
			sql.append("    AND t.orderid = ? ");
			paramList.add(req.getCustorderid());
		}
		if (StringUtils.isNotBlank(req.getGridid())) {
			sql.append("    AND t.gridid = ? ");
			paramList.add(req.getGridid());
		}
		if (StringUtils.isNotBlank(req.getGt_optime())) {
			sql.append("    AND t.optime > str_to_date(?,'%Y-%m-%d %T') ");
			paramList.add(req.getGt_optime());
		}
		if (StringUtils.isNotBlank(req.getLt_optime())) {
			sql.append("    AND t.optime <= str_to_date(?,'%Y-%m-%d %T') ");
			paramList.add(req.getLt_optime());
		}
		if (StringUtils.isNotBlank(req.getOpcode())) {
			sql.append("    AND t.opcode = ? ");
			paramList.add(req.getOpcode());
		}
		if (StringUtils.isNotBlank(req.getOperator())) {
			sql.append("    AND t.operator = ? ");
			paramList.add(req.getOperator());
		}
		if (StringUtils.isNotBlank(req.getOprdep())) {
			sql.append("    AND t.oprdep = ? ");
			paramList.add(req.getOprdep());
		}
		if (StringUtils.isNotBlank(req.getPaystatus())) {
			// //如果订单状态是不需支付，不用管boss订单状态
			// if("00".equals(req.getPaystatus())){
			// sql.append(" AND NOT EXISTS (SELECT 1 FROM biz_portal_order t1
			// WHERE t1.orderid=t.orderid ) ");
			//
			// } else {
			// sql.append(" AND t.oprdep = ? ");
			// paramList.add(req.getOprdep());
			// }
			// 如果订单状态是不需支付，不用管boss订单状态
			sql.append(
					"    AND EXISTS (SELECT 1 FROM biz_portal_order t1 WHERE t1.orderid=t.orderid and t1.status=?) ");
			paramList.add(req.getPaystatus());
		}

		if (StringUtils.isNotBlank(req.getOrderstatus())) {
			sql.append("    AND t.orderstatus = ? ");
			paramList.add(req.getOrderstatus());
		}

		if (StringUtils.isNotBlank(req.getCustname())) {
			sql.append("    AND t.name like ? ");
			paramList.add(req.getCustname() + "%");
		}
		// sql.append(" order by t.orderid desc ");

		sql.append(" ) v ");

		if (BeanUtil.isListNotNull(req.getSortConditions())) {
			// 进行可排序字段合法性检查
			checkSortConditins4queCustorder(req, loginInfo);

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
			sql.append(sortConditions);
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
		page = getDAO().find(page, sql.toString(), CustordersBO.class, paramList.toArray());

		Rule showPrdINfoRule = ruleService.getRule("CITY_SHOW_PRDINFO");
		Rule rollbackRule = ruleService.getRule("CITY_ROLLBACK_RULE");
		List<CustordersBO> custordersList = page.getResult();
		if (custordersList != null && !custordersList.isEmpty() && custordersList.size() > 0) {
			for (CustordersBO custorder : custordersList) {
				CustorderDetBO custorderDet = getCustorderDet(custorder.getCustorderid(), loginInfo);
				custorder.setCustorderDet(custorderDet);
				checkPrintInfoRule(custorder);

				//查询订单对应的返还计划
				if (req.getDetail() && (("*".equals(showPrdINfoRule.getCity())||showPrdINfoRule.getCity().contains(custorder.getCity()))//判断地市满足 
						&&showPrdINfoRule.getValue().contains(custorder.getOpcode()))) {//判断订单类型满足
					QueOrderPkgInfoReq req2Boss = new QueOrderPkgInfoReq();
					req2Boss.setOrderid(custorder.getResporderid()+"");
					try {
						String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),BizConstant.BossInterfaceService.QUE_ORDER_PKGINFO,req2Boss, loginInfo);
						if(null!=bossRespOutput&&!"null".equals(bossRespOutput)){
							JSONArray array = new JSONArray(bossRespOutput);
							List<QueOrderPkgInfoResp> bossResp = BeanUtil.jsonToObject(array, QueOrderPkgInfoResp.class);
							custorder.setPkgInfos(bossResp);
						}
					} catch (BusinessException e) {
						log.error(e.getMessage());
					}
				}
				if (req.getDetail() && (null != rollbackRule) && (("*".equals(rollbackRule.getCity())||rollbackRule.getCity().contains(custorder.getCity()))//判断地市满足 
						&&rollbackRule.getValue().contains(custorder.getOpcode()))){
					custorder.setRollbackEnable("Y");
					
					
					
				}
			}
		}


		resp.setPagesize(BeanUtil.object2String(page.getPageSize()));
		resp.setCurrentPage(BeanUtil.object2String(page.getCntPageNo()));
		resp.setTotalRecords(BeanUtil.object2String(page.getTotalCount()));
		resp.setCustorders(custordersList);
		if (req.getDetail() && null!=custordersList && !custordersList.isEmpty() && custordersList.size() > 0) {
			CustordersBO bo = custordersList.get(0);
			if (BizConstant.BizCustorderSyncmode.ASYNC.equals(bo.getSyncmode())) {
				DeleteOrderBossReq queReq = new DeleteOrderBossReq();
				if (StringUtils.isNotBlank(req.getSystem())) {
					queReq.setQueflag(req.getSystem());
				}
				if (StringUtils.isBlank(SysConfig.getServiceCity())) {
					queReq.setOrderid(bo.getCustorderid());
				} else {
					queReq.setOrderid(String.valueOf(bo.getResporderid()));
				}
				String bossRespOutput = this.getBossHttpInfOutput(req.getBizorderid(),
						BizConstant.BossInterfaceService.QUE_ASYNC_ORDERRESULT, queReq, loginInfo);
				String bossResult = "处理成功";
				JSONObject object = new JSONObject(bossRespOutput);
				String result = object.getString("orderstatus");
				String bossSerialno = object.getString("bossserialno");
				String describe = "";
				try {
					describe = object.getString("describe");
				} catch (Exception e) {
				}
				if (result.equalsIgnoreCase("FAIL")) {
					String reason = object.getString("failmemo");
					bossResult = "处理失败:" + reason;
				} else if (result.equalsIgnoreCase("SYNC")) {
					bossResult = "当前订单正在处理中，请稍后再进行查询";
				}
				bo.setBossResult(bossResult);
				bo.setSerialno(bossSerialno);
				bo.setDescribe(describe);
			}
		}
		return returnInfo;

	}

	private void checkPrintInfoRule(CustordersBO bo) throws Exception {
		printService.judgePrintCondition(bo.getOpcode(), bo.getCity(), bo.getBossserialno(), bo);
		/*
		 * if(bo == null ||StringUtils.isBlank(bo.getOpcode()) ||
		 * StringUtils.isBlank(bo.getCity()) ||
		 * StringUtils.isBlank(bo.getBossserialno())){ return; } Rule rule =
		 * ruleService.getRule(bo.getCity(), CITY_PRINT_RULE_NAME); if(rule !=
		 * null && StringUtils.isNotBlank(rule.getValue())){
		 * if(rule.getValue().contains(bo.getOpcode())){ bo.setPrintShow("Y"); }
		 * }
		 */
	}

	private void checkSortConditins4queCustorder(QueCustorderInterReq req, LoginInfo loginInfo) throws Exception {
		if (null == req || BeanUtil.isListNull(req.getSortConditions())) {
			return;
		}

		Set sortnameSet = new HashSet();
		sortnameSet.add("optime");
		sortnameSet.add("orderstatus");
		sortnameSet.add("opcode");
		sortnameSet.add("paystatus");

		for (SortConditionsBO obj : req.getSortConditions()) {
			if (!sortnameSet.contains(obj.getSortname())) {
				throw new BusinessException("排序条件[" + obj.getSortname() + "]未定义");
			}
		}
	}

	private CustorderDetBO getCustorderDet(String custorderid, LoginInfo loginInfo) throws Exception {
		CheckUtils.checkEmpty(custorderid, "查询客户订单明细信息:客户订单编号不能为空");
		// 申请报装信息
		List<ApplyInstallBO> applyInstallList = getApplyInstall4getCustorderDet(Long.valueOf(custorderid));

		// 申请产品信息
		List<ApplyProductBO> applyProductList = getApplyProduct4getCustorderDet(Long.valueOf(custorderid));

		// 申请银行信息
		List<ApplyBankBO> applyBankList = getApplyBank4getCustorderDet(Long.valueOf(custorderid));
		// 体验授权信息
		List<ApplyTmpopenBO> applyTmpopenList = getApplyTmpopen4getCustorderDet(Long.valueOf(custorderid));
		// 刷新授权信息
		List<ApplyRefreshBO> applyRefreshList = getApplyRefresh4getCustorderDet(Long.valueOf(custorderid));
		// 缴纳欠费信息
		List<ApplyArrearBO> applyArrearList = getApplyArrear4getCustorderDet(Long.valueOf(custorderid));

		CustorderDetBO retCustorderDetBO = new CustorderDetBO();
		retCustorderDetBO.setApplyInstalls(applyInstallList);
		//ywp 对银行卡密码进行加密，只显示前后四位，其他用*号代替
		if(null!=applyBankList){
			for(ApplyBankBO item:applyBankList){
				item.setPwdacctno(hideCardNo(item.getAcctno()));
			}
		}
		retCustorderDetBO.setApplyProducts(applyProductList);
		retCustorderDetBO.setApplyBanks(applyBankList);
		retCustorderDetBO.setApplyTmpopens(applyTmpopenList);
		retCustorderDetBO.setApplyRefreshs(applyRefreshList);
		retCustorderDetBO.setApplyArrears(applyArrearList);

		return retCustorderDetBO;
	}

	private List<ApplyArrearBO> getApplyArrear4getCustorderDet(Long custorderid) throws Exception {
		CheckUtils.checkNull(custorderid, "查询刷新授权信息:客户订单编号不能为空");

		StringBuffer sql = new StringBuffer();
		List paramList = new ArrayList();

		sql.append(" SELECT * FROM ( ");
		sql.append(" SELECT t.custid, ");
		sql.append("        t.keyno, ");
		sql.append("        t.permark, ");
		sql.append("        t.pid, ");
		sql.append("        (SELECT s.pname FROM prd_pcode s WHERE s.pid = t.pid) pname, ");
		sql.append("        DATE_FORMAT(t.stime, '%Y-%m-%d %T') stime, ");
		sql.append("        DATE_FORMAT(t.etime, '%Y-%m-%d %T') etime, ");
		sql.append("        t.fees ");
		sql.append("   FROM biz_apply_arrear t ");
		sql.append("  WHERE 1 = 1 ");
		sql.append("    AND t.orderid = ? ");
		paramList.add(custorderid);
		sql.append(" ) v ");

		List<ApplyArrearBO> applyArrearList = getDAO().find(sql.toString(), ApplyArrearBO.class, paramList.toArray());

		return applyArrearList;
	}

	private List<ApplyRefreshBO> getApplyRefresh4getCustorderDet(Long custorderid) throws Exception {
		CheckUtils.checkNull(custorderid, "查询刷新授权信息:客户订单编号不能为空");

		StringBuffer sql = new StringBuffer();
		List paramList = new ArrayList();

		sql.append(" SELECT * FROM ( ");
		sql.append(" SELECT t.SERVID, ");
		sql.append("        t.LOGICDEVNO, ");
		sql.append("        t.LOGICSTBNO, ");
		sql.append("        t.OPKIND, ");
		sql.append("        code2name(t.OPKIND, 'SYS_FRESH_KIND') opkindname,  ");
		sql.append("        t.PID, ");
		sql.append("        (SELECT s.pname FROM prd_pcode s WHERE s.pid = t.pid) pname ");
		sql.append("   FROM biz_apply_reflesh t ");
		sql.append("  WHERE 1 = 1 ");
		sql.append("    AND t.orderid = ? ");
		paramList.add(custorderid);
		sql.append(" ) v ");

		List<ApplyRefreshBO> applyRefreshList = getDAO().find(sql.toString(), ApplyRefreshBO.class,
				paramList.toArray());

		return applyRefreshList;
	}

	private List<ApplyTmpopenBO> getApplyTmpopen4getCustorderDet(Long custorderid) throws Exception {
		CheckUtils.checkNull(custorderid, "查询体验授权信息:客户订单编号不能为空");

		StringBuffer sql = new StringBuffer();
		List paramList = new ArrayList();

		sql.append(" SELECT * FROM ( ");
		sql.append(" SELECT t.SERVID, ");
		sql.append("        t.LOGICDEVNO, ");
		sql.append("        t.LOGICSTBNO, ");
		sql.append("        t.PLANID, ");
		sql.append("        t.PLANNAME, ");
		sql.append("        t.PID, ");
		sql.append("        (SELECT s.pname FROM prd_pcode s WHERE s.pid = t.pid) pname, ");
		sql.append("        t.DAYS ");
		sql.append("   FROM biz_apply_tmpopen t ");
		sql.append("  WHERE 1 = 1 ");
		sql.append("    AND t.orderid = ? ");
		paramList.add(custorderid);
		sql.append(" ) v ");

		List<ApplyTmpopenBO> applyTmpopenList = getDAO().find(sql.toString(), ApplyTmpopenBO.class,
				paramList.toArray());

		return applyTmpopenList;
	}

	private List<ApplyBankBO> getApplyBank4getCustorderDet(Long custorderid) throws Exception {
		CheckUtils.checkNull(custorderid, "查询申请银行授权信息:客户订单编号不能为空");

		StringBuffer sql = new StringBuffer();
		List paramList = new ArrayList();

		sql.append(" SELECT * FROM ( ");
		sql.append(" SELECT t.servid, ");
		sql.append("        t.payway, ");
		sql.append("        code2name(t.payway, 'SYS_FEEWAY') paywayname, ");
		sql.append("        t.acctname, ");
		sql.append("        t.bankcode, ");
		sql.append("        code2name(t.bankcode, 'SYS_BANK') bankcodename, ");
		sql.append("        t.acctno, ");
		sql.append("        t.acctkind, ");
		sql.append("        code2name(t.acctkind, 'SYS_ACCT_KIND') acctkindname, ");
		sql.append("        t.accttype, ");
		sql.append("        code2name(t.accttype, 'SYS_ACCT_TYPE') accttypename ");
		sql.append("   FROM biz_apply_bank t ");
		sql.append("  WHERE 1 = 1 ");
		sql.append("    AND t.orderid = ? ");
		paramList.add(custorderid);
		sql.append(" ) v ");

		List<ApplyBankBO> applyBankList = getDAO().find(sql.toString(), ApplyBankBO.class, paramList.toArray());

		return applyBankList;
	}

	private List<ApplyProductBO> getApplyProduct4getCustorderDet(Long custorderid) throws Exception {
		CheckUtils.checkNull(custorderid, "查询申请产品信息:客户订单编号不能为空");

		StringBuffer sql = new StringBuffer();
		List paramList = new ArrayList();

		sql.append(" SELECT * FROM ( ");
		sql.append(" SELECT t.recid, ");
		sql.append("        t.ostatus, ");
		sql.append("        code2name(t.ostatus, 'BIZ_APPLY_PRODUCT_OSTATUS') ostatusname, ");
		sql.append("        t.servid, ");
		sql.append("        t.logicdevno, ");
		sql.append("        t.knowid, ");
		sql.append("        (SELECT k.knowname FROM prd_salespkg_know k WHERE k.knowid = t.KNOWID) knowname, ");
		// sql.append(" (SELECT k.objtype FROM prd_salespkg_know k WHERE
		// k.knowid = t.KNOWID) knowobjtype, ");
		sql.append("        t.salespkgid, ");
		sql.append("        (SELECT s.salespkgname ");
		sql.append("           FROM prd_salespkg s ");
		sql.append("          WHERE s.salespkgid = t.salespkgid) salespkgname, ");
		sql.append("        t.pid, ");
		sql.append("        (SELECT s.pname FROM prd_pcode s WHERE s.pid = t.pid) pname, ");
		sql.append("        t.count, ");
		sql.append("        t.unit, ");
		sql.append("        code2name(t.unit, 'PRD_ORDERUNIT') unitname ");
		sql.append("   FROM biz_apply_product t ");
		sql.append("  WHERE 1 = 1 ");
		sql.append("    AND t.orderid = ? ");
		paramList.add(custorderid);
		sql.append(" ) v ");

		List<ApplyProductBO> applyProductList = getDAO().find(sql.toString(), ApplyProductBO.class,
				paramList.toArray());

		return applyProductList;
	}

	private List<ApplyInstallBO> getApplyInstall4getCustorderDet(Long custorderid) throws Exception {
		CheckUtils.checkNull(custorderid, "查询申请报装信息:客户订单编号不能为空");

		StringBuffer sql = new StringBuffer();
		List paramList = new ArrayList();

		sql.append(" SELECT * FROM ( ");
		sql.append(" SELECT t.cardtype, ");
		sql.append("        code2name(t.cardtype, 'SYS_CARD_TYPE') cardtypename, ");
		sql.append("        t.cardno, ");
		sql.append("        t.cardaddr, ");
		sql.append("        t.linkphone, ");
		sql.append("        t.whladdr, ");
		sql.append("        t.oservid, ");
		sql.append("        t.ologicdevno, ");
		sql.append("        t.devback, ");
		sql.append("        t.detaddr, ");
		sql.append("        t.predate, ");
		sql.append("        t.logicdevno, ");
		sql.append("        t.stbno ");
		sql.append("   FROM biz_apply_install t ");
		sql.append("  WHERE 1 = 1 ");
		sql.append("    AND t.orderid = ? ");
		paramList.add(custorderid);
		sql.append(" ) v ");

		List<ApplyInstallBO> applyInstallList = getDAO().find(sql.toString(), ApplyInstallBO.class,
				paramList.toArray());

		return applyInstallList;
	}

	/**
	 * 查询欠费记录接口
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo queArreardet(QueArreardetInterReq req, QueArreardetInterResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		//		LoginInfo loginInfo = InterTest.getGridTestLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");
		if (StringUtils.isBlank(req.getCustid()) && StringUtils.isBlank(req.getKeyno())) {
			throw new BusinessException("请求条件[custid]和[keyno]不能同时为空");
		}

		QueArreardetBossReq bossReq = new QueArreardetBossReq();
		BeanUtils.copyPropertiesNotSuperClass(bossReq, req);// 不复制父类的字段，不然报错
		// 如与boss有不同的字段，在这里另外做特殊处理
		// bossReq.setXxx(req.getXxx());

		String serverCode = "";
		if (StringUtils.isNotBlank(SysConfig.getServiceCity())) {
			serverCode = BizConstant.ServerCityBossInterface.QUE_ARREARDET;
		} else {
			serverCode = BizConstant.BossInterfaceService.QUE_ARREARDET;
		}

		// 调用BOSS接口
		String bossRespOutput = this.getBossHttpInfOutput(req.getBizorderid(), serverCode, bossReq, loginInfo);

		QueArreardetBossResp bossResp = (QueArreardetBossResp) this.copyBossResp2InterResp(resp,
				QueArreardetBossResp.class, bossRespOutput);
		// 如与boss有不同的字段，在这里另外做特殊处理
		// resp.setXxx(bossResp.getXxx());

		return returnInfo;
	}

	/**
	 * 查询申请产品详细接口
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo queApplyKnowDet(QueApplyKnowDetInterReq req, QueApplyKnowDetInterResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getCustorderid(), "匹配条件[custorderid]不能为空");

		ApplyProduct applyProductVO = new ApplyProduct();
		if (StringUtils.isNotBlank(req.getRecid())) {
			applyProductVO.setRecid(Long.valueOf(req.getRecid()));
		}
		if (StringUtils.isNotBlank(req.getCustorderid())) {
			applyProductVO.setOrderid(Long.valueOf(req.getCustorderid()));
		}
		if (StringUtils.isNotBlank(req.getKnowid())) {
			applyProductVO.setKnowid(Long.valueOf(req.getKnowid()));
		}
		// 只查订购
		applyProductVO.setOstatus(BizConstant.BizApplyProductOstatus.ORDER);

		List<ApplyProduct> applyProductList = getDAO().find(applyProductVO);
		if (BeanUtil.isListNull(applyProductList)) {
			throw new BusinessException("查询申请产品详细:根据请求参数查询不到订购的产品信息");
		}

		List<ApplyKnowDetBO> retApplyKnowDetList = new ArrayList();
		for (ApplyProduct applyProduct : applyProductList) {
			if (null == applyProduct.getKnowid()) {
				continue;
			}

			SalespkgKnow salesKnowVO = new SalespkgKnow();
			salesKnowVO.setKnowid(Long.valueOf(applyProduct.getKnowid()));
			List<SalespkgKnow> salesKnowList = getDAO().find(salesKnowVO);
			if (BeanUtil.isListNull(salesKnowList)) {
				continue;
			}
			SalespkgKnow salesKnow = salesKnowList.get(0);

			KnowObjDet knowObjDet = interPrdService.getKnowObjDetInfo(salesKnow.getObjtype(), salesKnow.getObjid());
			if (null == knowObjDet) {
				throw new BusinessException("查询申请产品详细:根据知识库存对象id[" + salesKnow.getObjid() + "]和知识库对象类型["
						+ salesKnow.getObjtype() + "]查询不到知识库对象明细信息");
			}

			if (BizConstant.PrdSalespkgKnowObjtype.SALESPKG.equals(salesKnow.getObjtype())) {
				if (null == knowObjDet.getPkg()) {
					throw new BusinessException("查询申请产品详细:根据知识库存对象id[" + salesKnow.getObjid() + "]查询不到营销方案明细信息");
				}

				// 标记已选择的优惠
				setPkgSelflag4queApplyKnowDet(applyProduct, knowObjDet);

			}

			// 拼装返回信息
			ApplyKnowDetBO applyKnowDet = new ApplyKnowDetBO();
			applyKnowDet.setKnowid(BeanUtil.object2String(salesKnow.getKnowid()));
			applyKnowDet.setKnowname(salesKnow.getKnowname());
			applyKnowDet.setType(salesKnow.getObjtype());
			applyKnowDet.setPkg(knowObjDet.getPkg());
			applyKnowDet.setPrd(knowObjDet.getPrd());

			retApplyKnowDetList.add(applyKnowDet);

		}

		resp.setApplyKnowDet(retApplyKnowDetList);
		return returnInfo;
	}

	private void setPkgSelflag4queApplyKnowDet(ApplyProduct applyProduct, KnowObjDet knowObjDet) throws Exception {

		if (null == applyProduct || null == knowObjDet || null == knowObjDet.getPkg()) {
			return;
		}

		SalespkgDetailBO requeired = knowObjDet.getPkg().getRequired();
		SalespkgDetailBO optional = knowObjDet.getPkg().getOptional();

		if (null != requeired) {
			List<SalespkgSoftsBO> softPrdList = requeired.getSoftPrds();
			List<SalespkgSelectsBO> selectPrdList = requeired.getSelectPrds();

			setPkgSoftPrdSelflag4queApplyKnowDet(applyProduct, softPrdList);
			setPkgSelectPrdSelflag4queApplyKnowDet(applyProduct, selectPrdList);

		}

		if (null != optional) {
			List<SalespkgSoftsBO> softPrdList = optional.getSoftPrds();
			List<SalespkgSelectsBO> selectPrdList = optional.getSelectPrds();

			setPkgSoftPrdSelflag4queApplyKnowDet(applyProduct, softPrdList);
			setPkgSelectPrdSelflag4queApplyKnowDet(applyProduct, selectPrdList);
		}

	}

	private void setPkgSoftPrdSelflag4queApplyKnowDet(ApplyProduct applyProduct, List<SalespkgSoftsBO> softPrdList)
			throws Exception {
		CheckUtils.checkNull(applyProduct, "申请产品信息不能为空");
		CheckUtils.checkNull(applyProduct.getRecid(), "申请产品记录id不能为空");
		CheckUtils.checkNull(applyProduct.getOrderid(), "客户订单id不能为空");
		CheckUtils.checkNull(applyProduct.getKnowid(), "知识库营销id不能为空");

		if (BeanUtil.isListNull(softPrdList)) {
			return;
		}

		for (SalespkgSoftsBO soft : softPrdList) {
			if (StringUtils.isBlank(soft.getPid())) {
				continue;
			}

			ApplyProductSoft applyProductSoftVO = new ApplyProductSoft();
			applyProductSoftVO.setPrecid(applyProduct.getRecid());
			applyProductSoftVO.setOrderid(applyProduct.getOrderid());
			applyProductSoftVO.setKnowid(Long.valueOf(applyProduct.getKnowid()));
			applyProductSoftVO.setPid(Long.valueOf(soft.getPid()));

			List<ApplyProductSoft> applyProductSoftList = getDAO().find(applyProductSoftVO);

			if (BeanUtil.isListNotNull(applyProductSoftList)) {
				soft.setIssel(SysConstant.SysYesNo.YES);
			} else {
				soft.setIssel(SysConstant.SysYesNo.NO);
			}

		}

	}

	private void setPkgSelectPrdSelflag4queApplyKnowDet(ApplyProduct applyProduct,
			List<SalespkgSelectsBO> selectPrdList) throws Exception {
		CheckUtils.checkNull(applyProduct, "申请产品信息不能为空");
		CheckUtils.checkNull(applyProduct.getRecid(), "申请产品记录id不能为空");
		CheckUtils.checkNull(applyProduct.getOrderid(), "客户订单id不能为空");
		CheckUtils.checkNull(applyProduct.getKnowid(), "知识库营销id不能为空");

		if (BeanUtil.isListNull(selectPrdList)) {
			return;
		}

		for (SalespkgSelectsBO select : selectPrdList) {
			if (StringUtils.isBlank(select.getSelectid())) {
				continue;
			}
			if (BeanUtil.isListNull(select.getPrds())) {
				continue;
			}
			for (SalespkgSelectDetsBO selectDet : select.getPrds()) {

				ApplyProductSelect applyProductSeletVO = new ApplyProductSelect();
				applyProductSeletVO.setPrecid(applyProduct.getRecid());
				applyProductSeletVO.setOrderid(applyProduct.getOrderid());
				applyProductSeletVO.setKnowid(Long.valueOf(applyProduct.getKnowid()));
				applyProductSeletVO.setPid(Long.valueOf(selectDet.getPid()));

				List<ApplyProductSelect> applyProductSoftList = getDAO().find(applyProductSeletVO);

				if (BeanUtil.isListNotNull(applyProductSoftList)) {
					selectDet.setIssel(SysConstant.SysYesNo.YES);
				} else {
					selectDet.setIssel(SysConstant.SysYesNo.NO);
				}
			}

		}
	}

	public ReturnInfo bizChgOperatorPwd(BizChgOperatorPwdInterReq req, BizChgOperatorPwdInterResp resp)
			throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();

		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getBizorderid(), "业务订单号不能为空");
		CheckUtils.checkEmpty(req.getOperid(), "操作员id不能为空");
		CheckUtils.checkEmpty(req.getOldpwd(), "旧密码不能为空");
		CheckUtils.checkEmpty(req.getNewpwd(), "新密码不能为空");

		// 调boss接口,并将结果存入resp
		callBossInf4bizChgOperatorPwd(req, resp, loginInfo);

		// 写业务表
		updateBizdata4bizChgOperatorPwd(req, loginInfo);

		// resp.setCustorderid(req.getBizorderid());

		return returnInfo;
	}

	private void updateBizdata4bizChgOperatorPwd(BizChgOperatorPwdInterReq req, LoginInfo loginInfo) throws Exception {
		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getOperid(), "操作员id不能为空");
		CheckUtils.checkEmpty(req.getOldpwd(), "旧密码不能为空");
		CheckUtils.checkEmpty(req.getNewpwd(), "新密码不能为空");

		String oldpwdmd5 = MD5.md5(req.getOldpwd());
		String newpwdmd5 = MD5.md5(req.getNewpwd());

		PrvOperator prvOperatorVO = new PrvOperator();
		prvOperatorVO.setId(Long.valueOf(req.getOperid()));

		List<PrvOperator> prvOperatorList = getDAO().find(prvOperatorVO);
		if (BeanUtil.isListNull(prvOperatorList)) {
			throw new BusinessException("根据操作员id[" + req.getOperid() + "]查询不到操作员信息");
		}
		PrvOperator oper = prvOperatorList.get(0);

		PrvOperator oldOper = new PrvOperator();
		BeanUtils.copyProperties(oldOper, oper);

		if (!oldpwdmd5.equals(oper.getPasswd())) {
			throw new BusinessException("原密码不正确");
		}
		oper.setPasswd(newpwdmd5);
		getDAO().save(oper);

		// 添加操作日志处理--TODO,后结实现

	}

	private void callBossInf4bizChgOperatorPwd(BizChgOperatorPwdInterReq req, BizChgOperatorPwdInterResp resp,
			LoginInfo loginInfo) throws Exception {

		// 将请求做一下转换，并赋默认值
		BizChgOperatorPwdBossReq req2Boss = getBizChgOperatorPwdInterReq2Boss(req);

		String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
				BizConstant.BossInterfaceService.BIZ_CHGOPERATORPWD, req2Boss, loginInfo);

		copyBossResp2InterResp4bizChgOperatorPwd(resp, bossRespOutput);

	}

	private void copyBossResp2InterResp4bizChgOperatorPwd(BizChgOperatorPwdInterResp resp, String bossRespOutput) {
		// 因为字段是一样的，这里可以偷懒一下，
		// 后面如果接口对外字段和boss返回字段不一致，则自行写使用set方法设置值
		// BeanUtils.copyProperties(resp, bossResp);
		// resp.setAcctkind(bossResp.getAcctkind());

	}

	private BizChgOperatorPwdBossReq getBizChgOperatorPwdInterReq2Boss(BizChgOperatorPwdInterReq req) {
		BizChgOperatorPwdBossReq bizChgOperatorPwdBossReq = new BizChgOperatorPwdBossReq();
		bizChgOperatorPwdBossReq.setNewpwd(req.getNewpwd());
		bizChgOperatorPwdBossReq.setOldpwd(req.getOldpwd());
		bizChgOperatorPwdBossReq.setOperid(req.getOperid());

		return bizChgOperatorPwdBossReq;
	}

	public ReturnInfo chgPortalPwd(ChgPortalPwdReq req) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();

		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getUserid(), "用户id不能为空");
		CheckUtils.checkEmpty(req.getNewpwd(), "新密码不能为空");

		if (am5Service.resetPassword(req.getUserid(), req.getNewpwd()).code != 0) {
			throw new BusinessException("密码修改失败");
		}
		return returnInfo;
	}

	public ReturnInfo queResHouse(QueResHouseInterReq req, QueResHouseInterResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		// 调boss接口,并将结果存入resp
		callBossInf4queResHouse(req, resp, loginInfo);

		return returnInfo;
	}

	private void callBossInf4queResHouse(QueResHouseInterReq req, QueResHouseInterResp resp, LoginInfo loginInfo)
			throws Exception {

		// 将请求做一下转换，并赋默认值
		QueResHouseBossReq req2Boss = getQueResHouseInterReq2Boss(req);

		String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.QUE_RESHOUSE,
				req2Boss, loginInfo);

		copyBossResp2InterResp4queResHouse(resp, bossRespOutput);

	}

	private void copyBossResp2InterResp4queResHouse(QueResHouseInterResp resp, String jsonStr) throws Exception {
		QueResHouseBossResp bossResp = (QueResHouseBossResp) BeanUtil.jsonToObject(jsonStr, QueResHouseBossResp.class);

		// 因为字段是一样的，这里可以偷懒一下，
		// 后面如果接口对外字段和boss返回字段不一致，则自行写使用set方法设置值
		BeanUtils.copyProperties(resp, bossResp);
		// resp.setAcctkind(bossResp.getAcctkind());

	}

	private QueResHouseBossReq getQueResHouseInterReq2Boss(QueResHouseInterReq req) {

		QueResHouseBossReq queResHouseBossReq = new QueResHouseBossReq();
		queResHouseBossReq.setAddr(req.getAddr());
		queResHouseBossReq.setAreaid(req.getAreaid());
		queResHouseBossReq.setCity(req.getCity());
		queResHouseBossReq.setCurrentPage(req.getCurrentPage());
		queResHouseBossReq.setPagesize(req.getPagesize());
		queResHouseBossReq.setPatch(req.getPatch());
		queResHouseBossReq.setRightControl(req.getRightControl());

		return queResHouseBossReq;
	}

	// 获取订单编号
	public String getOrderCode() throws Exception {

		// if (orderCode == null) {
		// Object o = getDAO().findUnique(
		// "SELECT max(ordercode) FROM CustOrder");
		// String maxOrdercode = o.toString();
		//
		// orderCode = Long.valueOf(maxOrdercode);
		// if (orderCode == null) {
		// orderCode = 10000000000L;
		// }
		// }
		//
		// orderCode++;
		// return orderCode.toString();

		String orderCode = getDAO().getSequence("SEQ_BIZ_CUSTORDER_ORDERCODE").toString();
		if (StringUtils.isBlank(orderCode)) {
			throw new BusinessException("取得订单编码失败");
		}
		return orderCode;
	}

	// 随机码发送接口 begin
	public ReturnInfo sendRandomCode(SendRandomCodeInterReq req, SendRandomCodeInterResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		//		LoginInfo loginInfo = InterTest.getGridTestLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getPhoneno(), "参数[phoneno]不能为空");
		CheckUtils.checkEmpty(req.getSmscontent(), "参数[smscontent]不能为空");
		CheckUtils.checkEmpty(req.getUserip(), "参数[userip]不能为空");
		//ywp 增加手机号码校验
		Rule rule1 = ruleService.getRule("PHONE_RULE");
		if(null != rule1){
			boolean flag = CheckUtils.isPhone(req.getPhoneno(), rule1.getValue());
			if(!flag){
				throw new BusinessException("手机号码不正确");
			}
		}

		Rule rule = ruleService.getRule(loginInfo.getCity(), "SPECICAL_SEND_SMS_CITY");
		if (rule == null) {
			// 调boss接口,并将结果存入resp
			callUapInf4sendRandomCode(req, resp, loginInfo);
		} else {
			if (StringUtils.isNotBlank(SysConfig.getServiceCity())) {
				callAlibaba2sendSMS(req, loginInfo, resp);
			} else {
//				pyCallUapInf4SendRandomcode(req, resp, loginInfo);
				sendSmsMsg(req, loginInfo);
			}
		}
		return returnInfo;
	}

	/**
	 * 番禺调用短信发送
	 * 
	 * @param req
	 * @param resp
	 * @param loginInfo
	 * @throws Exception
	 */
	private void pyCallUapInf4SendRandomcode(SendRandomCodeInterReq req, SendRandomCodeInterResp resp,
			LoginInfo loginInfo) throws Exception {
		CheckUtils.checkEmpty(req.getCustid(), "客户信息不能为空");
		SendRandomCodePyUapReq req2Uap = getSendRandomCodePyUapReq(req, loginInfo);
		String uapRespOutput = getPyUapSocketInfoOutput(req.getBizorderid(),
				BizConstant.UAPInterfaceService.UAP_SOS_SMS, req2Uap, loginInfo, SysConfig.getUapClientid());
		System.out.println(uapRespOutput);
		resp.setCustorderid(req.getBizorderid());
	}
	
	/**
	 * 先用SendSmsService的发送短信方法
	 * @param req
	 * @param loginInfo
	 * @throws Exception 
	 */
	private void sendSmsMsg(SendRandomCodeInterReq req,LoginInfo loginInfo) throws Exception{
		SendRandomCodePyUapReq req2Uap = getSendRandomCodePyUapReq(req, loginInfo);
		String uapRespOutput = getSocketSend(req.getBizorderid(),
				BizConstant.UAPInterfaceService.UAP_SOS_SMS, req2Uap, loginInfo,
				SysConfig.getBossUapurl(),Environment.BOSS_ENCODING);
		//TODO 要不要保存日志？？
	}
	

	
	

	private SendRandomCodePyUapReq getSendRandomCodePyUapReq(SendRandomCodeInterReq req, LoginInfo loginInfo)
			throws Exception {
		SendRandomCodePyUapReq req2Uap = new SendRandomCodePyUapReq();
		req2Uap.setAreaid(loginInfo.getAreaid());
		req2Uap.setBusiserial(BossHttpClientImpl.createRequestid());
		req2Uap.setCity(loginInfo.getCity());
		req2Uap.setCustid(req.getCustid());
		req2Uap.setOpcode("I0");
		req2Uap.setPhonenumber(getTestPhone(req.getPhoneno()));
		req2Uap.setServid("1");
		req2Uap.setSmstext(getSmsText(req2Uap.getPhonenumber()));
		req2Uap.setSmstype("0");//以前是1   。。要改成0才可以
		return req2Uap;
	}

	private String getSmsText(String phone) {
		String smsCode = getSmsCode(phone);
		StringBuffer sb = new StringBuffer();
		sb.append("尊敬的用户，您的短信验证码为");
		sb.append(smsCode);
		sb.append("，验证码300秒内有效，感谢您的使用！");
		sb.append("详询：22888188");
		return sb.toString();
	}

	private String getTestPhone(String phone) throws Exception {
		String phoneNum = phone;
		Rule rule = ruleService.getRule("SYSTEM_PHONE");
		if (rule != null && StringUtils.isNotBlank(rule.getValue())) {
			phoneNum = rule.getValue();
		}
		return phoneNum;
	}

	private void callUapInf4sendRandomCode(SendRandomCodeInterReq req, SendRandomCodeInterResp resp,
			LoginInfo loginInfo) throws Exception {

		// SendRandomCodeUapReq uapReq = getSendRandomCodeInterReq2Uap(req);
		SendRandomCode2UapReq uapReq = getSendRandomCode2UapReq(req);

		String uapRespOutput = getUapSocketInfOutput(req.getBizorderid(),
				BizConstant.UAPInterfaceService.SEND_RANDOM_CODE, uapReq, loginInfo);

		copyUapResp2InterResp4sendRandomCode(resp, uapRespOutput);
		resp.setCustorderid(req.getBizorderid());

	}

	private SendRandomCode2UapReq getSendRandomCode2UapReq(SendRandomCodeInterReq req) {
		SendRandomCode2UapReq reqUapReq = new SendRandomCode2UapReq();
		reqUapReq.setAreaid(req.getAreaid());
		reqUapReq.setCity(req.getCity());
		reqUapReq.setPhoneNum(req.getPhoneno());
		// reqUapReq.setSmsParamJson(req.getSmscontent());
		reqUapReq.setSmsTemplate(SMSTemplateCode.RAN_DOM_CODE_SMS);
		reqUapReq.setUserIP(req.getUserip());
		return reqUapReq;
	}

	@Deprecated
	private SendRandomCodeUapReq getSendRandomCodeInterReq2Uap(SendRandomCodeInterReq req) {
		SendRandomCodeUapReq reqUapReq = new SendRandomCodeUapReq();
		reqUapReq.setAreaid(req.getAreaid());
		reqUapReq.setBranchNO(req.getCity());
		reqUapReq.setOpcode(BizConstant.UAPOpcode.SEND);
		reqUapReq.setPhoneNum(req.getPhoneno());
		reqUapReq.setSmsContent(req.getSmscontent());
		reqUapReq.setUserIP(req.getUserip());

		return reqUapReq;
	}

	private void copyUapResp2InterResp4sendRandomCode(SendRandomCodeInterResp resp, String uapRespOutput)
			throws Exception {
		SendRandomCodeUapResp uapOutput = (SendRandomCodeUapResp) socketResp2Outputobj(uapRespOutput,
				SendRandomCodeUapResp.class);

		// 设置apOutput各字段值到resp对象
		// resp.setRetData(apOutput.getData());

	}

	// 随机码发送接口 end

	// 随机码验证接口 begin
	public ReturnInfo authRandomCode(AuthRandomCodeInterReq req, AuthRandomCodeInterResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getPhoneno(), "参数[phoneno]不能为空");
		CheckUtils.checkEmpty(req.getRandomcode(), "参数[randomcode]不能为空");

		//ywp 增加手机号码校验
		Rule rule1 = ruleService.getRule("PHONE_RULE");
		if(null != rule1){
			boolean flag = CheckUtils.isPhone(req.getPhoneno(), rule1.getValue());
			if(!flag){
				throw new BusinessException("手机号码不正确");
			}
		}
		
		Rule rule = ruleService.getRule(loginInfo.getCity(), "SPECICAL_SEND_SMS_CITY");

		// 超级验证码免验证
		if (StringUtils.isNotBlank(SysConfig.getSuperRandomCode())) {
			if (SysConfig.getSuperRandomCode().equals(req.getRandomcode())) {
				return returnInfo;
			}
		}
		if (rule == null) {
			// 调boss接口,并将结果存入resp
			callUapInf4authRandomCode(req, resp, loginInfo);
		} else {
			checkLocalCode(req.getPhoneno(), req.getRandomcode(), resp, req.getBizorderid());
		}
		return returnInfo;
	}

	private void checkLocalCode(String phoneno, String code, AuthRandomCodeInterResp resp, String custorid)
			throws Exception {
		String smsCode = EhcacheUtil.get(phoneno).toString();
		CheckUtils.checkNull(smsCode, "当前手机号码并未获得验证短信");
		CheckUtils.checkEmpty(smsCode, "当前手机号码并未获得验证短信");
		if (!smsCode.equals(code)) {
			CheckUtils.checkNull(null, "短信验证码错误");
		}
		resp.setCustorderid(custorid);
	}

	private void callUapInf4authRandomCode(AuthRandomCodeInterReq req, AuthRandomCodeInterResp resp,
			LoginInfo loginInfo) throws Exception {

		AuthRandomCodeUapReq uapReq = getAuthRandomCodeInterReq2Uap(req);

		String uapRespOutput = getUapSocketInfOutput(req.getBizorderid(),
				BizConstant.UAPInterfaceService.AUTH_RANDOM_CODE, uapReq, loginInfo);

		copyUapResp2InterResp4authRandomCode(resp, uapRespOutput);
		resp.setCustorderid(req.getBizorderid());

	}

	private AuthRandomCodeUapReq getAuthRandomCodeInterReq2Uap(AuthRandomCodeInterReq req) {
		AuthRandomCodeUapReq reqUapReq = new AuthRandomCodeUapReq();
		reqUapReq.setOpcode(BizConstant.UAPOpcode.AUTH);
		reqUapReq.setPhoneNum(req.getPhoneno());
		reqUapReq.setRandomCode(req.getRandomcode());

		return reqUapReq;
	}

	private void copyUapResp2InterResp4authRandomCode(AuthRandomCodeInterResp resp, String uapRespOutput)
			throws Exception {
		AuthRandomCodeUapResp uapOutput = (AuthRandomCodeUapResp) socketResp2Outputobj(uapRespOutput,
				AuthRandomCodeUapResp.class);

		// 设置apOutput各字段值到resp对象
		// resp.setRetData(apOutput.getData());

	}

	// 随机码验证接口 end

	// 随机码作废接口 begin
	public ReturnInfo invalidRandomCode(InvalidRandomCodeInterReq req, InvalidRandomCodeInterResp resp)
			throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getPhoneno(), "参数[phoneno]不能为空");

		// 调boss接口,并将结果存入resp
		callUapInf4invalidRandomCode(req, resp, loginInfo);

		return returnInfo;
	}

	private void callUapInf4invalidRandomCode(InvalidRandomCodeInterReq req, InvalidRandomCodeInterResp resp,
			LoginInfo loginInfo) throws Exception {

		InvalidRandomCodeUapReq uapReq = getInvalidRandomCodeInterReq2Uap(req);

		String uapRespOutput = getUapSocketInfOutput(req.getBizorderid(),
				BizConstant.UAPInterfaceService.INVALID_RANDOM_CODE, uapReq, loginInfo);

		copyUapResp2InterResp4invalidRandomCode(resp, uapRespOutput);
		resp.setCustorderid(req.getBizorderid());

	}

	private InvalidRandomCodeUapReq getInvalidRandomCodeInterReq2Uap(InvalidRandomCodeInterReq req) {
		InvalidRandomCodeUapReq reqUapReq = new InvalidRandomCodeUapReq();
		reqUapReq.setOpcode(BizConstant.UAPOpcode.INVAL);
		reqUapReq.setPhoneNum(req.getPhoneno());

		return reqUapReq;
	}

	private void copyUapResp2InterResp4invalidRandomCode(InvalidRandomCodeInterResp resp, String uapRespOutput)
			throws Exception {
		InvalidRandomCodeUapResp uapOutput = (InvalidRandomCodeUapResp) socketResp2Outputobj(uapRespOutput,
				InvalidRandomCodeUapResp.class);

		// 设置apOutput各字段值到resp对象
		// resp.setRetData(apOutput.getData());

	}

	// 随机码作废接口 end

	// 用户反馈接口 begin
	public ReturnInfo bizFeedback(BizFeedbackInterReq req, BizFeedbackInterResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getContent(), "反馈内容不能为空");

		// 调boss接口,并将结果存入resp
		saveFeedbackData4bizFeedback(req, resp, loginInfo);

		resp.setCustorderid(req.getBizorderid());

		return returnInfo;
	}

	private void saveFeedbackData4bizFeedback(BizFeedbackInterReq req, BizFeedbackInterResp resp, LoginInfo loginInfo)
			throws Exception {

		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getContent(), "反馈内容不能为空");

		PrvUserFeedback feedback = new PrvUserFeedback();
		feedback.setContent(req.getContent());
		if (null != loginInfo.getDeptid()) {
			feedback.setDeptid(loginInfo.getDeptid());
		}
		feedback.setEmail(req.getEmail());
		feedback.setName(req.getName());
		if (null != loginInfo.getOperid()) {
			feedback.setOperid(loginInfo.getOperid());
		}
		feedback.setOptime(new Date());
		if (StringUtils.isNotBlank(req.getBizorderid())) {
			feedback.setOrderid(Long.valueOf(req.getBizorderid()));
		}
		feedback.setPhone(req.getPhone());
		feedback.setStatus("0");

		getDAO().save(feedback);

	}

	// 用户反馈接口 end

	// --取网格信息 begin----------------
	public List<Grid> getGridByGridobjid(String gridtype, Long gridobjid, String gridobjtype) throws Exception {
		CheckUtils.checkNull(gridobjid, "查询网格信息:网格对象id不能为空");
		CheckUtils.checkEmpty(gridobjtype, "查询网格信息:网格对象类型不能为空");

		StringBuffer sql = new StringBuffer();
		List paramList = new ArrayList();

		sql.append(" select g.* ");
		sql.append("   from biz_grid_info g, biz_grid_obj o ");
		sql.append("  where g.GRIDID = o.GRIDID ");
		sql.append("    and o.OBJID = ? ");
		paramList.add(gridobjid);
		sql.append("    and o.OBJTYPE = ? ");
		paramList.add(gridobjtype);

		if (StringUtils.isNotBlank(gridtype)) {
			sql.append("    and g.GRIDTYPE = ? ");
			paramList.add(gridtype);
		}

		List<Grid> bizGridList = getDAO().find(sql.toString(), Grid.class, paramList.toArray());

		return bizGridList;

	}

	protected List<Grid> getGrid(String gridtype, Long houseid, Long patchid, LoginInfo loginInfo) throws Exception {
		String gridobjType = bizParamCfgService.getGridobjTypeByCfg(loginInfo);
		if (StringUtils.isBlank(gridobjType)) {
			throw new BusinessException("查询网格信息:获取网格对象类型配置为空，请检查配置信息");
		}

		Long gridobjid = null;
		if (gridobjType.equals(BizConstant.BizGridObjObjtype.PATCH)) {
			CheckUtils.checkNull(patchid, "查询网格信息:片区id不能为空");
			gridobjid = patchid;
		} else if (gridobjType.equals(BizConstant.BizGridObjObjtype.ADDR)) {
			CheckUtils.checkNull(houseid, "查询网格信息:地址id不能为空");
			gridobjid = houseid;
		} else {
			throw new BusinessException("查询网格信息:暂不支持该类型的网格，请联系管理员");
		}

		List<Grid> bizGridList = getGridByGridobjid(gridtype, gridobjid, gridobjType);

		return bizGridList;

	}

	/**
	 * 获取业务级别的网格
	 * 
	 * @param houseid
	 * @param patchid
	 * @param loginInfo
	 * @return
	 * @throws Exception
	 */
	public BizGridInfo getGrid4Biz(Long houseid, Long patchid, LoginInfo loginInfo) throws Exception {
		/*
		 * mby 注释掉使用公共权限检查 String patchids = getPatchids(loginInfo) + ","; if
		 * (patchids.indexOf(patchid.toString() + ",") < 0) { throw new
		 * BusinessException( "您无权限受理该区域业务，请联系管理员进相应该操作员的网格受理权限配置"); }
		 */
		// 直接使用权限检查
		checkBizAcceptRight(houseid, patchid, loginInfo);

		String sql = "SELECT b.* FROM BIZ_GRID_INFO b WHERE   b.patchid = ? or EXISTS (SELECT * FROM  biz_grid_house_"
				+ loginInfo.getCity() + "  gh WHERE b.gridcode=gh.whgridcode AND gh.houseid = ?) ";
		List<BizGridInfo> gridList = getDAO().find(sql, BizGridInfo.class, new Object[] { patchid, houseid });
		if (gridList == null || gridList.size() == 0) {
			throw new BusinessException("通过【 patchid,houseid（" + patchid + "," + houseid + "）】未查询到网格！");
		}
		return gridList.get(0);
	}

	public String getPatchids(LoginInfo loginInfo) throws Exception {
		Object obj2 = getDAO().findUniqueObject("select length(fatherlist(2453));");
		Set<String> patchidSet = new HashSet<String>();
		String sql2 = "select ManageGridList1(?,?)";
		Object obj = getDAO().findUniqueObject(sql2, loginInfo.getOperid(), loginInfo.getCity());

		StringBuffer buffer = new StringBuffer();
		buffer.append("select patchid from biz_grid_info where gtype='1' ");
		buffer.append("and find_in_set(previd, ?) > 0");

		List objList = getDAO().findObjectList(buffer.toString(), obj.toString());

		String retval = "";
		for (Object object : objList) {
			patchidSet.add(object.toString());
		}

		StringBuffer sql = new StringBuffer();
		List paramList = new ArrayList();

		sql.append(" SELECT * FROM ( ");
		sql.append(" SELECT DISTINCT g.*  ");
		sql.append("   FROM biz_grid_info g,biz_grid_manager m ");
		sql.append("  WHERE 1=1 ");
		sql.append("    AND g.gtype='1' ");
		sql.append("    AND g.GRIDID=m.GRIDID ");
		sql.append("    AND m.OPERID=? ");
		paramList.add(loginInfo.getOperid());
		sql.append("    ) v ");

		List<BizGridInfo> gridObjList = getDAO().find(sql.toString(), BizGridInfo.class, paramList.toArray());

		for (BizGridInfo grid : gridObjList) {
			patchidSet.add(grid.getPatchid().toString());
		}

		if (patchidSet.size() > 0) {
			retval = StringUtils.join(patchidSet, ",");
		} else {
			retval = "-1";
		}

		return retval;
	}

	public List<BizGridInfo> getOperAddrGrids(LoginInfo loginInfo) throws Exception {
		StringBuffer sql = new StringBuffer();
		List paramList = new ArrayList();

		sql.append(" SELECT * FROM ( ");
		sql.append(" 		SELECT DISTINCT (g.gridid) id,g.gridcode gridcode,g.gridname gridname,  ");
		sql.append("		g.gtype gtype,g.mnrid mnrid,g.prio prio,  ");
		sql.append("		g.previd previd,g.patchid patchid,g.city city  ");
		sql.append("   		FROM biz_grid_info g,biz_grid_manager m ");
		sql.append("  		WHERE 1=1 ");
		sql.append("    	AND g.GRIDID=m.GRIDID ");
		sql.append("    	AND m.OPERID=? ");
		sql.append("    	AND g.CITY =? ");
		paramList.add(loginInfo.getOperid());
		paramList.add(loginInfo.getCity());
		sql.append("    ) v ");
		List<BizGridInfo> grids = getDAO().find(sql.toString(), BizGridInfo.class, paramList.toArray());
		if (grids == null || grids.isEmpty())
			return null;
		grids = getSmallGrids(grids);
		return grids;
	}

	private List<BizGridInfo> getSmallGrids(List<BizGridInfo> allDatas) throws Exception {
		List<BizGridInfo> managerGrids = new ArrayList<BizGridInfo>();
		List<BizGridInfo> addressGrids = new ArrayList<BizGridInfo>();
		for (BizGridInfo info : allDatas) {
			if (BizGridObjObjtype.ADDR.equals(info.getGtype().toString())
					|| BizGridObjObjtype.GRID.equals(info.getGtype().toString())) {
				addressGrids.add(info);
				continue;
			}
			if (BizGridObjObjtype.PATCH.equals(info.getGtype().toString())) {
				managerGrids.add(info);
				continue;
			}
		}
		if (!managerGrids.isEmpty()) {
			List<BizGridInfo> childs = getChildGrids(managerGrids);
			addressGrids.addAll(childs);
		}
		return addressGrids;
	}

	private List<BizGridInfo> getChildGrids(List<BizGridInfo> managers) throws Exception {
		List<BizGridInfo> childs = new ArrayList<BizGridInfo>();
		for (BizGridInfo info : managers) {
			BizGridInfo childInfo = new BizGridInfo();
			childInfo.setCity(info.getCity());
			childInfo.setPrevid(info.getId());
			List<BizGridInfo> tmps = getDAO().find(childInfo);
			if (tmps != null && !tmps.isEmpty()) {
				List<BizGridInfo> tmpchilds = getSmallGrids(tmps);
				childs.addAll(tmpchilds);
			}
		}

		return childs;
	}

	@Deprecated
	public List<BizGridInfo> getOperGrids(LoginInfo loginInfo) throws Exception {
		Object obj2 = getDAO().findUniqueObject("select length(fatherlist(2453));");
		Set<String> patchidSet = new HashSet<String>();
		/*
		 * String sql2 = "select ManageGridList(?)"; Object obj =
		 * getDAO().findUniqueObject(sql2, loginInfo.getOperid());
		 */
		String sql2 = "select ManageGridList1(?,?)";
		Object obj = getDAO().findUniqueObject(sql2, loginInfo.getOperid(), loginInfo.getCity());
		StringBuffer buffer = new StringBuffer();
		buffer.append("select gridid id,Gridcode,gridname from biz_grid_info where gtype='1' ");
		buffer.append("and find_in_set(previd, ?) > 0");

		List<BizGridInfo> gridObjs = getDAO().find(buffer.toString(), BizGridInfo.class, obj.toString());

		String retval = "";

		StringBuffer sql = new StringBuffer();
		List paramList = new ArrayList();

		sql.append(" SELECT * FROM ( ");
		sql.append(" SELECT DISTINCT g.*  ");
		sql.append("   FROM biz_grid_info g,biz_grid_manager m ");
		sql.append("  WHERE 1=1 ");
		sql.append("    AND g.gtype='1' ");
		sql.append("    AND g.GRIDID=m.GRIDID ");
		sql.append("    AND m.OPERID=? ");
		paramList.add(loginInfo.getOperid());
		sql.append("    ) v ");

		List<BizGridInfo> gridObjList = getDAO().find(sql.toString(), BizGridInfo.class, paramList.toArray());

		gridObjs.addAll(gridObjList);

		return gridObjs;
	}

	public String getGridcodes(LoginInfo loginInfo) throws Exception {
		Object obj2 = getDAO().findUniqueObject("select length(fatherlist(2453));");
		Set<String> patchidSet = new HashSet<String>();
		String sql2 = "select ManageGridList1(?,?)";
		Object obj = getDAO().findUniqueObject(sql2, loginInfo.getOperid(), loginInfo.getCity());

		StringBuffer buffer = new StringBuffer();
		buffer.append("select Gridcode from biz_grid_info where gtype!='1' ");
		buffer.append("and find_in_set(previd, ?) > 0");

		List objList = getDAO().findObjectList(buffer.toString(), obj.toString());

		String retval = "";
		for (Object object : objList) {
			if (object == null)
				continue;
			patchidSet.add(object.toString());
		}

		StringBuffer sql = new StringBuffer();
		List paramList = new ArrayList();

		sql.append(" SELECT * FROM ( ");
		sql.append(" SELECT DISTINCT g.*  ");
		sql.append("   FROM biz_grid_info g,biz_grid_manager m ");
		sql.append("  WHERE 1=1 ");
		sql.append("    AND g.gtype!='1' ");
		sql.append("    AND g.GRIDID=m.GRIDID ");
		sql.append("    AND m.OPERID=? ");
		paramList.add(loginInfo.getOperid());
		sql.append("    AND g.CITY=? ");
		paramList.add(loginInfo.getCity());
		sql.append("    ) v ");

		List<BizGridInfo> gridObjList = getDAO().find(sql.toString(), BizGridInfo.class, paramList.toArray());

		for (BizGridInfo grid : gridObjList) {
			if (grid.getGridcode() == null)
				continue;
			patchidSet.add(grid.getGridcode());

		}

		if (patchidSet.size() > 0) {
			retval = StringUtils.join(patchidSet, ",");
		} else {
			retval = "-1";
		}

		return retval;
	}
	// --取网格信息 end----------------

	// 操作员网格受理权限检查 begin
	public void checkBizAcceptRight(Long houseid, Long patchid, LoginInfo loginInfo) throws Exception {
		Rule rule = ruleService.getRule(loginInfo.getCity(), BizConstant.BizRuleParams.NOT_JUDGE_GRID);
		boolean flag = true;
		if(null!=rule){
			String areaids = rule.getAreaIds();
			if(null!=areaids){
				if("*".equals(areaids)||areaids.indexOf(loginInfo.getAreaid()+"")>-1){
					flag = false;
				}
			}
		}
		System.out.println("NOT_JUDGE_GRID==flag="+flag);
		if(flag){
			StringBuffer sql = getQueryAddressSql(loginInfo.getCity());
			sql.append(" WHERE o.houseid=?) n");

			PageImpl<BizGridHouse> pageResult = null;
			Page<BizGridHouse> page = new Page<BizGridHouse>();
			page.setPageNo(1);
			page.setPageSize(10);
			getDAO().clear();
			page = getDAO().find(page, sql.toString(), BizGridHouse.class, new Object[] { houseid });
			// 取操作员的受理权限
			String bizAcceptRight = bizParamCfgService.getBizAcceptRight(loginInfo);
			// 检查操作员权限，如果不是高权，则不允许跨网格受理业务
			// 因为跨网格受理会有个问题，比如A操作员只管理a,b，但是做了c网格的业务，这样在订单表记的是c网格。
			if (!BizConstant.RIGHTS.HIGH.equals(bizAcceptRight)) {
				if (page.getTotalCount() > 0) {
					String gridcodes = "," + getGridcodes(loginInfo) + ",";
					BizGridHouse bizGridHouse = (BizGridHouse) page.getResult().get(0);
					if (gridcodes.indexOf("," + bizGridHouse.getWhgridcode().toString() + ",") < 0) {
						throw new BusinessException("您无权限受理该区域业务，请联系管理员进相应该操作员的网格受理权限配置");
					}
				} else {
					String patchids = getPatchids(loginInfo) + ",";
					if (patchids.indexOf(patchid.toString() + ",") < 0) {
						throw new BusinessException("您无权限受理该区域业务，请联系管理员进相应该操作员的网格受理权限配置");
					}
				}
			}
		}
	}
	// 操作员网格受理权限检查 end

	private StringBuffer getQueryAddressSql(String city) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM(");
		sql.append("SELECT o.houseid,o.addrid,o.status,o.netstruct,o.whladdr,");
		sql.append("o.whgridcode,o.ywgridcode,o.gridid,o.patchid,o.areaid");
		sql.append(" FROM biz_grid_house_").append(city).append(" o");
		return sql;
	}

	/**
	 * 用户资料补录
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo editCust(EditCustInterReq req, EditCustInterResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkNull(req.getId(), "匹配条件[CUSTID]不能为空");
		CheckUtils.checkEmpty(req.getAreaid(), "匹配条件[areaid]不能为空");
		CheckUtils.checkEmpty(req.getCardno(), "匹配条件[cardno]不能为空");
		CheckUtils.checkEmpty(req.getCardtype(), "匹配条件[cardtype]不能为空");
		CheckUtils.checkEmpty(req.getName(), "匹配条件[name]不能为空");
		if (StringUtils.isBlank(req.getMobile()) && StringUtils.isBlank(req.getPhone())) {
			throw new BusinessException("匹配条件[mobile]和[phone]不能同时为空");
		}

		// 将请求做一下转换，并赋默认值
		EditCustBossReq req2Boss = getEditCustReq2Boss(req, req.getBizorderid());

		String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
				BizConstant.BossInterfaceService.BIZ_EDIT_CUST, req2Boss, loginInfo);
		// copyBossResp2InterResp4createCust(resp, bossRespOutput);
		// System.out.println(bossRespOutput);

		return returnInfo;
	}

	/**
	 * 查询公众客户信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo getCustPubInfo(CustPubInfoInterInfoReq req, CustPubInfoInterInfoResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkNull(req.getId(), "匹配条件[CUSTID]不能为空");

		// 将请求做一下转换，并赋默认值
		CustPubInfoInterInfoReq req2Boss = new CustPubInfoInterInfoReq();
		req2Boss.setId(req.getId());

		String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
				BizConstant.BossInterfaceService.QUE_CUSTPUBINF, req2Boss, loginInfo);

		copyBossResp2InterResp4CustPubInfo(resp, bossRespOutput);

		return returnInfo;
	}

	private void copyBossResp2InterResp4CustPubInfo(CustPubInfoInterInfoResp resp, String jsonStr) throws Exception {
		CustPubInfoInterInfoResp bossResp = (CustPubInfoInterInfoResp) BeanUtil.jsonToObject(jsonStr,
				CustPubInfoInterInfoResp.class);

		// 因为字段是一样的，这里可以偷懒一下，
		// 后面如果接口对外字段和boss返回字段不一致，则自行写使用set方法设置值
		BeanUtils.copyProperties(resp, bossResp);
		// resp.setAcctkind(bossResp.getAcctkind());
	}

	/**
	 * 查询业务异动
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo queryBizLogByPage(PersonCustPtReq req,ArrayList<PersonCustPtResp> respList) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req, "请求信息不能为空");

		String serverCode = "";
		if (StringUtils.isNotBlank(SysConfig.getServiceCity())) {
			serverCode = BizConstant.ServerCityBossInterface.QUE_BIZLOGBYPAGE;
		} else {
			serverCode = BizConstant.BossInterfaceService.QUE_BIZLOGBYPAGE;
		}

		String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(), serverCode, req, loginInfo);

		ObjectMapper objectMapper = new ObjectMapper();
		// System.out.println(bossRespOutput);
		try {

			JSONObject jo = new JSONObject(bossRespOutput);
			JSONObject jopage = new JSONObject(jo.getString("page"));
			String result = jopage.get("result").toString();
			List<PersonCustPtResp> resultList = new Gson().fromJson(result, new TypeToken<List<PersonCustPtResp>>() {}.getType());
//			returnInfo.setMessage(jopage.get("result").toString());
			if(resultList != null && !resultList.isEmpty()) {
				addShowPrint(resultList,loginInfo.getCity());
			}
			respList.addAll(resultList);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return returnInfo;
	}
	
	private void addShowPrint(List<PersonCustPtResp> datas,String city) throws Exception {
		
		Rule rule = ruleService.getRule(city, "PRINT_SHOW_INFO");
		/*if(rule != null && StringUtils.isNotBlank(rule.getValue())){
			if(rule.getValue().contains(bizCode)){
				condition.setPrintShow("Y");
				if(StringUtils.isBlank(condition.getBossserialno())){
					condition.setBossserialno(bossserialno);
				}
			}
		}*/
		if(rule != null && StringUtils.isNotBlank(rule.getValue())){
			for(PersonCustPtResp resp:datas) {
				try {
					if(StringUtils.isBlank(resp.getOpcode())||StringUtils.isBlank(city)||StringUtils.isBlank(resp.getSerialno())){
						return;
					}
					if(resp == null) return;
					if(rule.getValue().contains(resp.getOpcode())){
						resp.setPrintShow("Y");
					}
				}catch(Exception ex) {
					continue;
				}
			}
		}
	}

	/**
	 * 查询营销客户信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo queCustMarktInfo(QueCustMarktInfoReq req, QueCustMarktInfoResp res) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
				BizConstant.BossInterfaceService.QUE_CUST_MARKETINFO, req, loginInfo);

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			QueCustMarktInfoResp retres = (QueCustMarktInfoResp) BeanUtil.jsonToObject(bossRespOutput,
					QueCustMarktInfoResp.class);
			BeanUtils.copyProperties(res, retres);
		} catch (Exception ex) {
			returnInfo.setCode(IErrorDefConstant.ERROR_INVALID_JSON_CODE);
			returnInfo.setMessage(IErrorDefConstant.ERROR_INVALID_JSON_MSG);

		}

		return returnInfo;
	}

	/**
	 * 2.4.1.客服投诉工单查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo queCallCenterOrder(QueOrderReq req, OrderResponse res) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		checkApiRule(loginInfo.getCity(), "CALL_CENTER_ORDER", "该功能暂未开放");

		OrderRequest orderRequest = req.getOrderreq();

		try {
			// 生成请求报文
			String xml = XmlConverter.toXML(new Class[] { OrderRequest.class, QueOrder.class }, orderRequest);
			StringBuffer reqxml = new StringBuffer();
			reqxml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			reqxml.append(xml);
			// 调用客服接口，返回retxml
			String retxml = this.getWS().operateOrder(reqxml.toString());
			// 转换返回结果
			OrderResponse retres = (OrderResponse) XmlConverter.toEntity(retxml,
					new Class[] { OrderResponse.class, OrderDetail.class, Order.class });

			StringBuffer reqxml1 = new StringBuffer();
			DateTime dateTime = new DateTime(new Date());
			reqxml1.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><request>")
			.append("   <action>orderList</action>").append("   <username>cc_datang</username>")
			.append("   <passwd>26b2def2a6a5f1ef1a0672d380f931ea</passwd>").append("   <request-content>")
			.append("     <custid>").append(req.getOrderreq().getQueorder().getCustid()).append("</custid>")
			.append("     <category>appealWorkflow</category>").append("     <status>1</status>")
			.append("<effDate>")
			// .append(DateUtils.getFormatDateString(dateTime.plusDays(-15).toDate(),
			// DateUtils.DEFAULT_DATE_FORMAT))
			.append(req.getOrderreq().getQueorder().getEffDate()).append("</effDate>").append("<expDate>")
			// .append(DateUtils.getFormatDateString(new Date(),
			// DateUtils.DEFAULT_DATE_FORMAT))
			.append(req.getOrderreq().getQueorder().getExpDate()).append("</expDate>")
			.append("   </request-content>").append(" </request>");

			String retxml1 = this.getWS().operateOrder(reqxml1.toString());
			log.error("请求客服工单的参数:" + retxml1);
			OrderResponse retres1 = (OrderResponse) XmlConverter.toEntity(retxml1,
					new Class[] { OrderResponse.class, OrderDetail.class, Order.class });
			log.error("请求客服工单返回的结果:" + retres1);
			// 复制在途工单
			BeanUtils.copyProperties(res, retres);

			if (res.getResponse().getOrders() != null && res.getResponse().getOrders().size() > 0) {
				// 在途工单\竣工工单合并
				if (retres1.getResponse().getOrders() != null && res.getResponse().getOrders().size() > 0) {
					res.getResponse().getOrders().addAll(retres1.getResponse().getOrders());
				}
			} else {
				// 复制竣工工单
				BeanUtils.copyProperties(res, retres1);

			}
			return returnInfo;
		} catch (Exception ex) {
			log.error("请求客服工单返回的错误信息:" + ex.getMessage());
			returnInfo.setCode(IErrorDefConstant.ERROR_RemoteApiCallFailure_CODE);
			returnInfo.setMessage(IErrorDefConstant.ERROR_RemoteApiCallFailure_MSG);
		}

		return returnInfo;
	}

	/**
	 * 2.4.1.客服投诉工单明细查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo queCallCenterOrderDetail(QueOrderDetailReq req, OrderDetailResponse res) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		try {
			// 生成请求报文
			WorkAssignService sw = getWS();
			StringBuffer sb = new StringBuffer();

			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>").append(" <request>")
			.append("<action>orderDetail</action>").append("<username>cc_datang</username>")
			.append("<passwd>26b2def2a6a5f1ef1a0672d380f931ea</passwd>").append("<request-content>")
			.append("<orderid>").append(req.getOrderid()).append("</orderid>").append("<standby></standby>")
			.append("</request-content>").append(" </request>");
			// 调用客服接口，返回retxml
			String retxml = (String) sw.operateOrder(sb.toString());
			// 转换返回结果
			OrderDetailResponse retres = (OrderDetailResponse) XmlConverter.toEntity(retxml,
					new Class[] { OrderDetailResponse.class, OrderTasks.class, OrderRet.class });
			BeanUtils.copyProperties(res, retres);
			return returnInfo;
		} catch (Exception ex) {
			returnInfo.setCode(IErrorDefConstant.ERROR_RemoteApiCallFailure_CODE);
			returnInfo.setMessage(IErrorDefConstant.ERROR_RemoteApiCallFailure_MSG);
		}

		return returnInfo;
	}

	/**
	 * 2.4.2.装维工单查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo queOssOrder(WorkAssignServiceOrderListRequest req, OrderResponse res) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		checkApiRule(loginInfo.getCity(), "OSS_ORDER", "该功能暂未开放");
		req.setEffDate(null);
		try {
			// 取服务
			Channel2IomCallService sw = getOssWS();
			// 请求报文
			String xml = "<request><action>orderList</action>" + XmlConverter.toQueWSXML(req) + "</request>";
			// 发送报文
			String retxml = sw.queryOrderFromChannel(xml);
			// 返回结果转换成对象
			OrderResponse retres = (OrderResponse) XmlConverter.toEntity(retxml,
					new Class[] { OrderResponse.class, OrderDetail.class, Order.class });
			BeanUtils.copyProperties(res, retres);
			StringBuffer reqxml1 = new StringBuffer();
			DateTime dateTime = new DateTime(new Date());
			reqxml1.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><request>")
			.append("   <action>orderList</action>").append("   <request-content>").append("     <custid>")
			.append(req.getCustid()).append("</custid>").append("     <category>10S</category>")
			.append("     <status>1</status>").append("<effDate>")
			// .append(DateUtils.getFormatDateString(dateTime
			// .plusDays(-15).toDate(),
			// DateUtils.DEFAULT_DATE_FORMAT))
			.append(req.getEffDate()).append("</effDate>").append("<expDate>")
			// .append(DateUtils.getFormatDateString(new Date(),
			// DateUtils.DEFAULT_DATE_FORMAT))
			.append(req.getExpDate()).append("</expDate>").append("   </request-content>")
			.append(" </request>");

			String retxml1 = sw.queryOrderFromChannel(reqxml1.toString());
			OrderResponse retres1 = (OrderResponse) XmlConverter.toEntity(retxml1,
					new Class[] { OrderResponse.class, OrderDetail.class, Order.class });

			// 复制在途工单
			BeanUtils.copyProperties(res, retres);

			if (res.getResponse().getOrders() != null && res.getResponse().getOrders().size() > 0) {
				// 在途工单\竣工工单合并
				if (retres1.getResponse().getOrders() != null && res.getResponse().getOrders().size() > 0) {
					res.getResponse().getOrders().addAll(retres1.getResponse().getOrders());
				}
			} else {
				// 复制竣工工单
				BeanUtils.copyProperties(res, retres1);

			}
			return returnInfo;
		} catch (Exception ex) {
			returnInfo.setCode(IErrorDefConstant.ERROR_RemoteApiCallFailure_CODE);
			returnInfo.setMessage(IErrorDefConstant.ERROR_RemoteApiCallFailure_MSG);
		}

		return returnInfo;
	}

	private void checkApiRule(String city, String ruleName, String notice) throws Exception {
		Rule rule = new Rule();
		rule.setCity(city);
		rule.setRule(ruleName);
		List<Rule> rules = getDAO().find(rule);
		if (rules != null && !rules.isEmpty()) {
			CheckUtils.checkNull(null, notice);
		}
	}

	/**
	 * 2.4.2.装维工单明细查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo queOssOrderDetail(QueOrderDetailReq req, OssOrderDetailResponse res) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		try {
			// 生成请求报文
			Channel2IomCallService sw = getOssWS();
			StringBuffer sb = new StringBuffer();

			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>").append("<request>").append("<action>")
			.append("orderDetail").append("</action>").append("<request-content>").append("<orderid>")
			.append(req.getOrderid()).append("</orderid>").append("</request-content>").append(" </request>");

			// 调用客服接口，返回retxml
			String retxml = (String) sw.queryOrderFromChannel(sb.toString());
			// 转换返回结果
			OssOrderDetailResponse retres = (OssOrderDetailResponse) XmlConverter.toEntity(retxml,
					new Class[] { OssOrderDetailResponse.class, OssOrderTasks.class, OssOrderRet.class });
			BeanUtils.copyProperties(res, retres);

			return returnInfo;
		} catch (Exception ex) {
			returnInfo.setCode(IErrorDefConstant.ERROR_RemoteApiCallFailure_CODE);
			returnInfo.setMessage(IErrorDefConstant.ERROR_RemoteApiCallFailure_MSG);
		}

		return returnInfo;
	}

	private Channel2IomCallService getOssWS() throws Exception {
		Channel2IomCallService sw = (Channel2IomCallService) new Channel2IomCallServiceServiceLocator(this.getDAO())
		.getChannelOrderService();
		return sw;
	}

	private WorkAssignService getWS() throws Exception {
		WorkAssignService sw = (WorkAssignService) new WorkAssignServiceServiceLocator(this.getDAO())
		.getWorkAssignService();
		return sw;
	}

	/**
	 * 2.6.1.4.查询即将到期用户资料
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo queServExpire(QueServExpireReq req, QueSoonExpcustResp res) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		if (StringUtils.isEmpty(req.getPatchids())) {
			req.setPatchids(getGridcodes(loginInfo));
		}

		int pagesize = StringUtils.isBlank(req.getPagesize()) ? 100 : Integer.parseInt(req.getPagesize());
		int currentpage = StringUtils.isBlank(req.getCurrentpage()) ? 0 : Integer.parseInt(req.getCurrentpage());

		ExpriringManager mgr = ExpriringManager.getInstance();
		List<QueSoonExpcustBO> datas = mgr.queServExpire(req.getPatchids(), req.getCustname(), req.getObjid(),
				req.getSdate(), req.getEdate(), loginInfo.getCity(), pagesize, currentpage,
				SpringBeanUtil.getPersistentService());

		res.setList(datas);
		return returnInfo;
	}

	/**
	 * 2.6.1.6.获取将到期用户详情
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo queSoonExpcust(QueSoonExpcustReq req, QueSoonExpcustResp res) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		return returnInfo;
	}

	/**
	 * 
	 * 2.6.1.3.获取即将到期用户数量
	 * 
	 * @param installbo
	 * @param loginInfo
	 * @throws Exception
	 */
	public ReturnInfo queServExpirenum(QueServExpireNumReq req, QueServExpireNumResp res) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");
		if (StringUtils.isEmpty(req.getPatchids())) {
			req.setPatchids(getGridcodes(loginInfo));

		}
		// 暂时先屏蔽访问数据库方面的
		ExpriringManager exManager = ExpriringManager.getInstance();
		QueServExpireNumResp resp = exManager.getExpireNum(loginInfo, req, SpringBeanUtil.getPersistentService());
		BeanUtils.copyProperties(res, resp);
		return returnInfo;
	}

	/**
	 * 
	 * 1.1.1.1. 获取操作员负责网格
	 * 
	 * @param installbo
	 * @param loginInfo
	 * @throws Exception
	 */
	public ReturnInfo queOperGrids(QueOperGridsResp res) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		List<BizGridInfo> bizgrids = getOperAddrGrids(loginInfo);
		res.getBizgrids().addAll(bizgrids);

		return returnInfo;
	}

	// addAddress
	public ReturnInfo addTempAddress(TempAdrReq req, QueProductResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		Tmpbuildaddr addr = new Tmpbuildaddr();
		addr.setCustid(Long.parseLong(req.getCustomerId()));
		addr.setOperid(loginInfo.getOperid());
		List<Tmpbuildaddr> list = getDAO().find(addr);
		if (list != null && list.size() > 0) {
			addr = list.get(0);
		}
		addr.setHouseid(Long.parseLong(req.getHouserId()));
		addr.setLevel7(req.getLevel7());
		addr.setLevel8(req.getLevel8());
		addr.setLevel9(req.getLevel9());
		addr.setLevel10(req.getLevel10());
		addr.setLevel11(req.getLevel11());
		getDAO().save(addr);
		resp.setMsg("操作成功");
		return returnInfo;
	}

	public ReturnInfo getGRcode(QueGrCodeReq req, QueGrcodeResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		String url = SysConfig.getGrCodeUrl();

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		//		LoginInfo loginInfo = InterTest.getGridTestLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		PrvSysparam cityPublishNum = paramService.getData("CITY_PUBLISH_NUM", loginInfo.getCity());
		CheckUtils.checkNull(cityPublishNum, "该地市微厅数据配置为空,请联系管理员进行配置");
		CheckUtils.checkEmpty(cityPublishNum.getData(), "该地市微厅数据配置为空,请联系管理员进行配置");
		QueGrCodeReq req2 = new QueGrCodeReq();
		req2.setCustomerid(req.getCustomerid());
		if (StringUtils.isNotBlank(req.getKeyno())) {
			req2.setKeyno(req.getKeyno());
		}
		req2.setOPER_LOGNAME(loginInfo.getLoginname());

		Map<String, String> params = getParamMap(JSONUtil.serialize(req2), cityPublishNum.getData());

		RemotecallLog remotecallLog = BossHttpClientImpl.requestGet(loginInfo.getCity(), url, params);
		if (StringUtils.isEmpty(remotecallLog.getResponse())) {
			throw new Exception("接口调用出错，没有返回数据");
		}
		remotecallLog.setOrderid(Long.parseLong(req.getBizorderid()));
		getDAO().save(remotecallLog);
		JSONObject obj = new JSONObject(remotecallLog.getResponse());
		resp.setErrcode(obj.getString("errcode"));
		resp.setErrmsg(obj.getString("errmsg"));
		resp.setTicket(obj.getString("ticket"));
		resp.setUrl(obj.getString("url"));

		return returnInfo;
	}

	private Map<String, String> getParamMap(String keyno, String publishNum) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("publishnum", publishNum);
		param.put("clientcode", SysConfig.getClientcode());
		param.put("clientpwd", SysConfig.getClientpwd());
		param.put("funckey", SysConfig.getFunkey());
		param.put("funcparams", keyno);
		log.info("请求二维码的参数信息：" + param.toString());
		return param;
	}

	// 调用阿里大于短信网管发送短信
	private void sendSMSByAlibaba(AlibabaSMSReq smsreq, String url, String appkey, String secret,
			SendRandomCodeInterResp resp, SendRandomCodeInterReq sendreq) throws Exception {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend(smsreq.getExtend());
		req.setSmsType(smsreq.getSms_type());
		req.setSmsFreeSignName(smsreq.getSms_free_sign_name());
		req.setSmsParamString(smsreq.getSms_param());
		req.setRecNum(smsreq.getRec_num());
		req.setSmsTemplateCode(smsreq.getSms_template_code());
		try {
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
			if (!rsp.isSuccess()) {
				JSONObject rspJson = new JSONObject(rsp.getBody());
				JSONObject failJson = rspJson.getJSONObject("error_response");
				if (failJson != null) {
					CheckUtils.checkNull(null, failJson.getString("msg"));
				}
			}
		} catch (ApiException apiEx) {
			apiEx.printStackTrace();
			log.error("alibaba=====================" + apiEx.getErrMsg());
		} catch (org.codehaus.jettison.json.JSONException e) {
			e.printStackTrace();
			log.error("alibaba=====================" + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("alibaba=====================" + e.getMessage());
		}
		resp.setCustorderid(sendreq.getBizorderid());
	}

	private void callAlibaba2sendSMS(SendRandomCodeInterReq req, LoginInfo loginInfo, SendRandomCodeInterResp resp)
			throws Exception {
		AlibabaSmsParams params = new AlibabaSmsParams(getSmsCode(req.getPhoneno()), "83700000");
		AlibabaSMSReq aliReq = new AlibabaSMSReq();
		aliReq.setRec_num(req.getPhoneno());
		aliReq.setSms_type("normal");
		aliReq.setSms_free_sign_name("大连天途有线");
		aliReq.setSms_template_code("SMS_16330113");
		aliReq.setSms_param(JSONUtil.serialize(params));
		String url = PropertyUtil.getValueFromProperites("sysconfig", "ALIBABA_SMS_URL");
		String appKey = SysConfig.getAli_app_key();
		String appSercet = SysConfig.getAli_app_sercet();
		sendSMSByAlibaba(aliReq, url, appKey, appSercet, resp, req);
	}

	private String getSmsCode(String mobile) {
		Object code = EhcacheUtil.get(mobile);
		if (code == null) {
			code = getCode(1, 100000);
			EhcacheUtil.put(mobile, code);
		}
		return code.toString();
	}

	private String getCode(int max, int min) {
		Random rand = new Random();
		int tmp = Math.abs(rand.nextInt());
		Integer integer = tmp % (max - min + 1) + min;
		return integer.toString();
	}

	/**
	 * 记录用户行为
	 * 
	 * @throws Exception
	 */
	public ReturnInfo recordOpertor(RecordOpertReq req) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		if (req.getOperId() != null && StringUtils.isNotBlank(req.getBizCode())) {
			OperAction action = new OperAction();
			action.setOpcode(req.getBizCode());
			action.setOptid(req.getOperId());
			action.setCity(loginInfo.getCity());
			List<OperAction> operActions = getDAO().find(action);
			if (operActions != null && !operActions.isEmpty()) {
				action = operActions.get(0);
				action.setNums(action.getNums() + 1);
				action.setUpdateTime(new Date());
				getDAO().update(action);
			} else {
				action.setNums(1l);
				action.setUpdateTime(new Date());
				getDAO().save(action);
			}
		}

		return returnInfo;
	}

	public ReturnInfo bizUpdateCmPwd(BizUpdateCmPwdReq req) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();

		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getServid(), "用户不能为空");
		CheckUtils.checkEmpty(req.getNewPasswd(), "新密码不能为空");

		BizUpdateCmPwdBossReq req2Boss = new BizUpdateCmPwdBossReq();
		req2Boss.setServid(req.getServid());
		req2Boss.setNewPasswd(req.getNewPasswd());

		String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
				BizConstant.BossInterfaceService.BIZ_UPDATE_CM_PWD, req2Boss, loginInfo);

		return returnInfo;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public ReturnInfo scanCodeAndSign(ScanCodeAndSignReq req, ScanCodeAndSignResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();

		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getCode(), "二维码不能为空");

		Map<String, String> params = new HashMap<String, String>();
		params.put("code", req.getCode());
		String output = getMcrHttpOutPut(BizConstant.McrPath.TV_QRCODE_ANALYZE, params);
		String keyno = new JSONObject(output).optString("keyno");
		if (StringUtils.isBlank(keyno)) {
			throw new BusinessException("未找到相应智能卡，无法签到，详情请咨询系统管理员");
		}
		CustInfosBO custInfosBO = queCustInfoByKeyNo(keyno);
		if (custInfosBO == null) {
			throw new BusinessException("该智能卡可能不属于您的管辖区域，无法签到，详情请咨询系统管理员");
		}
		String now = DateUtils.formatDateNow();
		resp.setCustomer(custInfosBO);
		resp.setDate(now);
		resp.setSmno(keyno);
		resp.setMsg("签到成功！");

		BizManagerSign entity = new BizManagerSign();
		entity.setOpid(loginInfo.getOperid());
		entity.setOptime(DateUtils.parseDate(now));
		entity.setKeyno(keyno);
		List list = getDAO().find(entity);
		if (list == null || list.size() == 0) {
			entity.setAddr(custInfosBO.getLinkaddr());
			entity.setCustname(custInfosBO.getCustname());
			entity.setOpdeptid(loginInfo.getDeptid());
			getDAO().save(entity);
		}
		return returnInfo;
	}

	private CustInfosBO queCustInfoByKeyNo(String keyno) throws Exception {
		QueServstInfoInterResp resp = new QueServstInfoInterResp();
		QueServstInfoInterReq req = new QueServstInfoInterReq();
		req.setCurrentPage("1");
		req.setPagesize("1");
		req.setBizorderid(getNewBizOrderid());

		List<QueConditionsBO> queConditions = new ArrayList<QueConditionsBO>();
		QueConditionsBO conditionsBO = new QueConditionsBO();
		conditionsBO.setKeywordtype("smno");
		conditionsBO.setQuekeyword(keyno);
		queConditions.add(conditionsBO);
		req.setQueConditions(queConditions);
		try {
			ReturnInfo returnInfo = queServstInfo(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getCusts() != null && resp.getCusts().size() > 0) {
			return resp.getCusts().get(0);
		}
		return null;
	}

	private String getNewBizOrderid() throws Exception {
//		return getDAO().getSequence("SEQ_BIZ_CUSTORDER_ID").toString();
		return getBizorderid();
	}

	/**
	 * 针对大连提出的针对到个人的支付方式需求开发
	 * 
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queOperPayWay(ArrayList<PrvSysparam> payways) throws Exception {

		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		List<PrvSysparam> sysparams = paramService.getData("SPECIAL_PAYWAY");
		Rule rule = ruleService.getRule(loginInfo.getCity(), "SPECIAL_PAYWAY");
		if (sysparams != null && !sysparams.isEmpty() && rule != null && StringUtils.isNotBlank(rule.getValue())) {
			List<TmpPayWay> datas = getTmpPayWays(rule.getValue());
			if (!datas.isEmpty()) {
				boolean found = false;
				String mcode = "";
				for (PrvSysparam sysparam : sysparams) {
					if (StringUtils.isNotBlank(sysparam.getData())
							&& sysparam.getData().contains(loginInfo.getOperid().toString())) {
						mcode = sysparam.getMcode();
						break;
					}
				}
				if (StringUtils.isNotBlank(mcode)) {
					TmpPayWay selectPay = null;
					for (TmpPayWay data : datas) {
						if (data.mcode.equals(mcode)) {
							selectPay = data;
							break;
						}
					}
					if (selectPay != null) {
						String[] operPayways = selectPay.payways.split("~");
						for (String way : operPayways) {
							PrvSysparam payParam = paramService.getData("SYS_PAYWAY", way);
							if (payParam != null) {
								payways.add(payParam);
							}
						}
					}
				}
			}

		}

		return returnInfo;
	}

	private List<TmpPayWay> getTmpPayWays(String values) {
		List<TmpPayWay> payWays = new ArrayList<PubService.TmpPayWay>();
		String[] datas = values.split(",");
		for (String data : datas) {
			String[] tmps = data.split(":");
			payWays.add(new TmpPayWay(tmps[0], tmps[1]));
		}

		return payWays;
	}

	class TmpPayWay {
		String mcode;
		String payways;

		public TmpPayWay(String mcode, String payways) {
			super();
			this.mcode = mcode;
			this.payways = payways;
		}
	}

	/**
	 * 微厅绑定智能卡
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public WechatBindingResp bindWechatDevno(WechatBindingReq req, WechatBindingResp resp) throws Exception{
		CheckUtils.checkNull(req, "请求信息不能为空");
		Map<String, String> params = new HashMap<String, String>();
		params.put("devno",req.getDevno());
		params.put("openid",req.getOpenid());
		params.put("city",req.getCity());
		params.put("areaid",req.getAreaid());
		params.put("permark",req.getPermark());
		String output = getMcrHttpOutPut(BizConstant.McrPath.WECHAT_BINDING,params);
		String errcode = new JSONObject(output).optString("errcode");
		String errmsg = new JSONObject(output).optString("errmsg");
		if(!errcode.equals("0")){
			throw new BusinessException("智能卡绑定微厅失败，接口调用异常");
		}
		resp.setErrcode(errcode);
		resp.setErrmsg(errmsg);
		return resp;

	}

	/**
	 * 查询所在city是否支持身份证识别

	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo queEnableIdcardRecognition() throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);


		Rule rule=ruleService.getRule(loginInfo.getCity(),"IDCARD_RECOGNITION_CITY");
		if (rule==null){

			returnInfo.setMessage("N");
		}else {
			returnInfo.setMessage("Y");
		}

		return returnInfo;
	}
}
