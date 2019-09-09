package com.maywide.biz.inter.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.jsonplugin.JSONUtil;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.cons.BizConstant.BossInterfaceService;
import com.maywide.biz.core.entity.Rule;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.BossHttpClientService;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.IPrintCondition;
import com.maywide.biz.inter.pojo.bizOrderDel.BizOrderDel2BossReq;
import com.maywide.biz.inter.pojo.bizOrderDel.BizOrderDelReq;
import com.maywide.biz.inter.pojo.bizOrderDel.BizOrderDelResp;
import com.maywide.biz.inter.pojo.printAccInfo.Biz2BossPrintInfo;
import com.maywide.biz.inter.pojo.printAccInfo.BizPrintAccInfoReq;
import com.maywide.biz.inter.pojo.queAsyncOrder.QueAsyncOrderList2BossReq;
import com.maywide.biz.inter.pojo.queAsyncOrder.QueAsyncOrderList2BossResp;
import com.maywide.biz.inter.pojo.queAsyncOrder.QueAsyncOrderListReq;
import com.maywide.biz.inter.pojo.queInvoInfo.QueBossInvoInfoReq;
import com.maywide.biz.inter.pojo.queInvoInfo.QueInvoInfoReq;
import com.maywide.biz.inter.pojo.queInvoInfo.QueInvoInfoResp;
import com.maywide.biz.inter.pojo.queNextInv.QueBossNextInvReq;
import com.maywide.biz.inter.pojo.queNextInv.QueNextInvReq;
import com.maywide.biz.inter.pojo.queNextInv.QueNextInvResp;
import com.maywide.biz.inter.pojo.quePageContent.QuePageContent2BossReq;
import com.maywide.biz.inter.pojo.quePageContent.QuePageContentReq;
import com.maywide.biz.inter.pojo.quePageContent.QuePageContentResp;
import com.maywide.biz.inter.pojo.quePrintInvo.PrintBossInvoInfoReq;
import com.maywide.biz.inter.pojo.quePrintInvo.PrintInvoInfoReq;
import com.maywide.biz.inter.pojo.quePrintInvo.PrintInvoInfoResp;
import com.maywide.biz.inter.pojo.queUnPrint.QueUnprintBossReq;
import com.maywide.biz.inter.pojo.queUnPrint.QueUnprintInvinfoReq;
import com.maywide.biz.inter.pojo.queUnPrint.QueUnprintInvinfoResp;
import com.maywide.biz.inter.pojo.quebossorder.QueBossorderInterReq;
import com.maywide.biz.inter.pojo.quebossorder.QueBossorderInterResp;
import com.maywide.biz.inter.pojo.recordPrintOrder.RecordPrintOrderReq;
import com.maywide.biz.market.entity.BizPortalOrder;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class PrintService extends CommonService{
	
	@Autowired
	private BossHttpClientService bossHttpClientService;

	@Autowired
	private PersistentService perService;

	@Autowired
	private InterPrdService interPrdService;

	@Autowired
	private PubService pubService;
	
	@Autowired
	private RuleService ruleService;

	Logger log = LoggerFactory.getLogger(getClass());
	
	private final String CITY_PRINT_RULE_NAME = "PRINT_SHOW_INFO";

	/**
	 * 获取客户未开票信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queUnprintInVinfo(QueUnprintInvinfoReq req, QueUnprintInvinfoResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getCustid(), "查询客户id不能为空");

		QueUnprintBossReq req2Boss = new QueUnprintBossReq(req.getCustid());

		String bossOutPut = getBossOutPut(req.getBizorderid(), BizConstant.BossInterfaceService.QUE_UNPRINT_INVINFO,
				req2Boss, loginInfo);

		resp.setMsg(bossOutPut);

		return returnInfo;
	}

	private String getBossOutPut(String id, String serverCode, Object req2Boss, LoginInfo loginInfo) throws Exception {
		String requestContent = JSONUtil.serialize(req2Boss);
		if (StringUtils.isNotBlank(requestContent)) {
			log.info("=========>servicecode:" + serverCode + "=========>" + "requestContent:" + requestContent);
		}
		return bossHttpClientService.requestPost(id, serverCode, requestContent, loginInfo);
	}

	/**
	 * 获取下一个发票号
	 * 
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queNextInvo(QueNextInvReq req, QueNextInvResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getIndustrys(), "行业类型不能为空");

		QueBossNextInvReq req2Boss = getQueBossNextInvReq(req, loginInfo);

		String bossOutPut = getBossOutPut(req.getBizorderid(), BizConstant.BossInterfaceService.GET_OPER_NEXT_INVO,
				req2Boss, loginInfo);
		resp.setMsg(bossOutPut);
		return returnInfo;
	}

	private QueBossNextInvReq getQueBossNextInvReq(QueNextInvReq req, LoginInfo loginInfo) {
		QueBossNextInvReq req2Boss = new QueBossNextInvReq();
		req2Boss.setAreaid(req.getAreaid());
		req2Boss.setCity(loginInfo.getCity());
		req2Boss.setDeptid(req.getDeptid());
		req2Boss.setLoginname(loginInfo.getLoginname());
		req2Boss.setName(loginInfo.getName());
		req2Boss.setOperid(req.getOperid());
		req2Boss.setIndustrys(req.getIndustrys());
		return req2Boss;
	}

	/**
	 * 查询发票信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queInvoInfo(QueInvoInfoReq req, QueInvoInfoResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getInvno(), "发票信息不能为空");

		QueBossInvoInfoReq req2Boss = getQueBossInvoInfo(req);

		String result = getBossOutPut(req.getBizorderid(), BizConstant.BossInterfaceService.QUE_INVINFO, req2Boss,
				loginInfo);
		resp.setMsg(result);
		return returnInfo;
	}

	private QueBossInvoInfoReq getQueBossInvoInfo(QueInvoInfoReq req) {
		QueBossInvoInfoReq req2Boss = new QueBossInvoInfoReq();
		req2Boss.setBookNo(req.getBookNo());
		req2Boss.setInvno(req.getInvno());
		req2Boss.setMacInfos(req.getMacInfos());
		return req2Boss;
	}

	/**
	 * 打印发票接口
	 * 
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo printInvInfo(PrintInvoInfoReq req, PrintInvoInfoResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getInvno(), "发票编号不能为空");
		CheckUtils.checkEmpty(req.getPayway(), "发票支付方式不能为空");
		CheckUtils.checkEmpty(req.getInvcontids(), "打印商品项目不能为空");
		CheckUtils.checkEmpty(req.getMac(), "请选定一台设备");
		CheckUtils.checkEmpty(req.getBookno(), "发票本编号不能为空");

		PrintBossInvoInfoReq req2Boss = getPrintBossInvoInfoReq(req);

		String result = getBossOutPut(req.getBizorderid(), BizConstant.BossInterfaceService.BIZ_PRINT_INVINFO, req2Boss,
				loginInfo);
		resp.setMsg(result);
		return returnInfo;
	}

	private PrintBossInvoInfoReq getPrintBossInvoInfoReq(PrintInvoInfoReq req) {
		PrintBossInvoInfoReq req2Boss = new PrintBossInvoInfoReq();
		req2Boss.setInvno(req.getInvno());
		req2Boss.setBookno(req.getBookno());
		req2Boss.setInvcontids(req.getInvcontids());
		req2Boss.setMac(req.getMac());
		req2Boss.setPayway(req.getPayway());
		req2Boss.setPosSerialno(req.getPosSerialno());
		return req2Boss;
	}

	public ReturnInfo bizPrintAccinfo(BizPrintAccInfoReq req, PrintInvoInfoResp resp) throws Exception {

		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getOrderId(), "订单编号不能为空");

		CustOrder custOrder = (CustOrder) perService.find(CustOrder.class, Long.parseLong(req.getOrderId()));
		CheckUtils.checkNull(custOrder, "根据订单信息无法查询到该订单");
		BizPortalOrder portalOrder = custOrder.getPortalOrder();
		CheckUtils.checkNull(portalOrder, "根据订单信息无法查询到该订单");
		CheckUtils.checkNull(portalOrder.getResporderid(), "根据订单信息无法查询到该订单");

		Biz2BossPrintInfo req2Boss = getBiz2BossPrintInfo(portalOrder.getResporderid().toString());

		String result = getBossOutPut(req.getBizorderid(), BizConstant.BossInterfaceService.BIZ_PRINT_ACCINFO, req2Boss,
				loginInfo);
		resp.setMsg(result);

		return returnInfo;
	}

	private Biz2BossPrintInfo getBiz2BossPrintInfo(String orderid) throws Exception {

		Biz2BossPrintInfo req2Boss = new Biz2BossPrintInfo();
		QueBossorderInterResp bossorderResp = new QueBossorderInterResp();
		QueBossorderInterReq bossorderReq = new QueBossorderInterReq();
//		bossorderReq.setBizorderid(perService.getSequence("SEQ_BIZ_CUSTORDER_ID").toString());
		bossorderReq.setBizorderid(getBizorderid());
		bossorderReq.setBossorderid(orderid);
		interPrdService.queBossorder(bossorderReq, bossorderResp);
		if (BeanUtil.isListNotNull(bossorderResp.getOrders())) {
			req2Boss.setOrderid(bossorderResp.getOrders().get(0).getOrderid());
		} else {
			CheckUtils.checkNull(null, "BOSS端查询不到该订单信息");
		}

		return req2Boss;
	}

	/**
	 * 查询异步订单信息列表接口
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queAsyncOrderList(QueAsyncOrderListReq req, QueAsyncOrderList2BossResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		QueAsyncOrderList2BossReq req2Boss = getQueAsyncOrderList2BossReq(req, loginInfo);
		String jsonResultStr = pubService.getBossHttpInfOutput(req.getBizorderid(),
				BossInterfaceService.QUE_ASYNC_ORDERINFO, req2Boss, loginInfo);
		copyValue2Resp(jsonResultStr, resp);

		return returnInfo;
	}

	private void copyValue2Resp(String jsonStr, QueAsyncOrderList2BossResp resp) throws Exception {
		QueAsyncOrderList2BossResp value = (QueAsyncOrderList2BossResp) BeanUtil.jsonToObject(jsonStr,
				QueAsyncOrderList2BossResp.class);
		BeanUtils.copyProperties(resp, value);
	}

	private QueAsyncOrderList2BossReq getQueAsyncOrderList2BossReq(QueAsyncOrderListReq req, LoginInfo loginInfo) {
		QueAsyncOrderList2BossReq req2Boss = new QueAsyncOrderList2BossReq();
		req2Boss.setCurrentPage(req.getCurrentPage());
		req2Boss.setDealstatus(req.getDealstatus());
		req2Boss.setDeptid(loginInfo.getDeptid().toString());
		req2Boss.setFromDate(req.getFromDate());
		req2Boss.setOperid(loginInfo.getOperid().toString());
		req2Boss.setOrderstatus(req.getOrderstatus());
		req2Boss.setPagesize(req.getPageSize());
		req2Boss.setToDate(req.getToDate());
		return req2Boss;
	}

	/**
	 * 失败订单处理提交接口
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo bizOrderDel(BizOrderDelReq req, BizOrderDelResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CustOrder order = registCustOrder4OrderDel(req, loginInfo);
		BizOrderDel2BossReq req2Boss = getBizOrderDel2BossReq(req);
		String resultStr = pubService.getBossHttpInfOutput(req.getBizorderid(), BossInterfaceService.BIZ_ORDERDEAL, req2Boss,
				loginInfo);
		order.setOrderstatus(BizConstant.BizCustorderOrderstatus.SYNC);
		resp.setCustorderid(order.getId());
		return returnInfo;
	}

	private BizOrderDel2BossReq getBizOrderDel2BossReq(BizOrderDelReq req) {
		BizOrderDel2BossReq req2Boss = new BizOrderDel2BossReq();
		req2Boss.setDealdesc(req.getDealdesc());
		req2Boss.setDealtype(req.getDealtype());
		req2Boss.setOrdercode(req.getOrdercode());
		return req2Boss;
	}

	private CustOrder registCustOrder4OrderDel(BizOrderDelReq req, LoginInfo loginInfo) throws Exception {
		CustOrder custOrder = new CustOrder();
		custOrder.setId(Long.parseLong(req.getBizorderid()));
		custOrder.setAreaid(loginInfo.getAreaid());
		custOrder.setCity(loginInfo.getCity());
		custOrder.setOprdep(loginInfo.getDeptid());
		custOrder.setName(req.getCustName());
		custOrder.setCustid(req.getCustid());
		custOrder.setOpcode(BizConstant.BizOpcode.BIZ_ORDERDEAL);
		custOrder.setAddr(req.getAddress());
		custOrder.setSyncmode(BizConstant.BizCustorderSyncmode.SYNC);
		custOrder.setOperator(loginInfo.getOperid());
		custOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.INIT);
		custOrder.setOptime(new Date());
		custOrder.setOrdercode(pubService.getOrderCode());
		custOrder.setPortalOrder(null);
		perService.save(custOrder);
		perService.flushSession();
		return custOrder;
	}
	
	/***
	 * 无纸化打印数据接口查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo quePageContent(QuePageContentReq req,QuePageContentResp resp) throws Exception{
		
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getSerialno(), "业务流水号信息不能为空");
		
		QuePageContent2BossReq req2Boss = new QuePageContent2BossReq();
		BeanUtils.copyPropertiesNotSuperClass(req2Boss, req);
		
		String outInfo = pubService.getBossHttpInfOutput(req.getBizorderid(), BossInterfaceService.QUE_PAPERLESS_CONTENT, req2Boss, loginInfo);
		transportObject(outInfo,resp);
		resp.setDeptId(loginInfo.getDeptid().toString());
		return returnInfo;
	}
	
	
	private void transportObject(String json,QuePageContentResp resp) throws Exception{
		QuePageContentResp resp1 = (QuePageContentResp) BeanUtil.jsonToObject(json, QuePageContentResp.class);
		BeanUtils.copyProperties(resp, resp1);
	}
	
	/**
	 * 记录订单无纸化打印信息
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo recordPrintOrder(RecordPrintOrderReq req) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getOrderid(), "订单信息不能为空");
		
		CustOrder order = (CustOrder) pubService.getDAO().find(CustOrder.class, Long.parseLong(req.getOrderid()));
		if(order != null){
			String content = order.getPrinted();
			if(StringUtils.isNotBlank(content)){
				String[] contents = content.split(",");
				int count = Integer.parseInt(contents[1]);
				content = contents[0]+","+(++count);
				order.setPrinted(content);
			}else{
				order.setPrinted("Y,1");
			}
			pubService.getDAO().update(order);
		}
		return returnInfo;
	}
	
	public void judgePrintCondition(String bizCode,String city,String bossserialno,IPrintCondition condition) throws Exception{
		if(StringUtils.isBlank(bizCode)||StringUtils.isBlank(city)||StringUtils.isBlank(bossserialno)){
			return;
		}
		if(condition == null) return;
		Rule rule = ruleService.getRule(city, CITY_PRINT_RULE_NAME);
		if(rule != null && StringUtils.isNotBlank(rule.getValue())){
			if(rule.getValue().contains(bizCode)){
				condition.setPrintShow("Y");
				if(StringUtils.isBlank(condition.getBossserialno())){
					condition.setBossserialno(bossserialno);
				}
			}
		}
	}
}
