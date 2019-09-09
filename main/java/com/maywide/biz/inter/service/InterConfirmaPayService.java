package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.cons.BizConstant.BossInterfaceService;
import com.maywide.biz.cons.BizConstant.SysPayway;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.core.servlet.SpringBeanUtil;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.bizConfPay.BizConfirmaPayBossReq;
import com.maywide.biz.inter.pojo.bizConfPay.BizConfirmaPayBossResp;
import com.maywide.biz.inter.pojo.bizConfPay.BizConfirmaPayReq;
import com.maywide.biz.inter.pojo.bizConfPay.BizConfirmaPayResp;
import com.maywide.biz.inter.pojo.crtConfPOrder.CreatBizConfirmPayOrderReq;
import com.maywide.biz.inter.pojo.crtConfPOrder.CreatBizConfirmPayOrderResp;
import com.maywide.biz.inter.pojo.crtConfPOrder.Invoice;
import com.maywide.biz.inter.pojo.queConfirmPayInfo.QueConfirmPayBossReq;
import com.maywide.biz.inter.pojo.queConfirmPayInfo.QueConfirmPayBossResp;
import com.maywide.biz.inter.pojo.queConfirmPayInfo.QueConfirmPayInfoReq;
import com.maywide.biz.inter.pojo.queConfirmPayInfo.QueConfirmPayInfoResp;
import com.maywide.biz.inter.pojo.queNotCustList.NotPaidCustomerBean;
import com.maywide.biz.inter.pojo.queNotCustList.QueNotCustListReq;
import com.maywide.biz.inter.pojo.queNotCustList.QueNotCustListResp;
import com.maywide.biz.market.entity.BizPortalOrder;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.biz.pay.unify.manager.ParamsManager;
import com.maywide.core.dao.support.Page;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.CheckUtils;

@Service
public class InterConfirmaPayService extends CommonService {

	@Autowired
	private PubService pubService;

	/**
	 * 查询未确认收款项
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queConfirmPayInfo(QueConfirmPayInfoReq req, QueConfirmPayInfoResp resp) throws Exception {

		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkEmpty(req.getCustid(), "客户编号不能为空");
		QueConfirmPayBossReq req2Boss = getQueConfirmPayBossReq(req, loginInfo);
		String resultInfo = getBossHttpInfOutput(req.getBizorderid(), BossInterfaceService.QUE_CONFIRMPAY, req2Boss,
				loginInfo);
		hanlderResp(resultInfo, resp);
		return returnInfo;
	}

	private void hanlderResp(String resultInfo, QueConfirmPayInfoResp resp) throws Exception {
		QueConfirmPayBossResp boss2Resp = (QueConfirmPayBossResp) BeanUtil.jsonToObject(resultInfo,
				QueConfirmPayBossResp.class);
		resp.setDatas(boss2Resp.getConfirmInvoices());
	}

	private QueConfirmPayBossReq getQueConfirmPayBossReq(QueConfirmPayInfoReq req, LoginInfo loginInfo) {
		QueConfirmPayBossReq req2Boss = new QueConfirmPayBossReq();
		req2Boss.setCity(loginInfo.getCity());
		req2Boss.setCustid(req.getCustid());
		req2Boss.setEdate(req.getEdate());
		req2Boss.setMarkno(req.getMarkno());
		req2Boss.setSdate(req.getSdate());
		req2Boss.setSmartcardno(req.getSmartcardno());
		return req2Boss;
	}

	/**
	 * 生成未确认收款订单
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo creatBizConfirmPayOrder(CreatBizConfirmPayOrderReq req, CreatBizConfirmPayOrderResp resp)
			throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CustOrder custOrder = regist4custOrder(req, loginInfo);
		resp.setCustorderid(custOrder.getId().toString());

		return returnInfo;
	}

	private CustOrder regist4custOrder(CreatBizConfirmPayOrderReq req, LoginInfo loginInfo) throws Exception {
		CustOrder custOrder = new CustOrder();
		custOrder.setAddr(req.getAddress());
		custOrder.setAreaid(loginInfo.getAreaid());
		custOrder.setCity(loginInfo.getCity());
		custOrder.setCustid(Long.parseLong(req.getCustid()));
		custOrder.setId(Long.parseLong(req.getBizorderid()));
		custOrder.setName(req.getCustname());
		custOrder.setOperator(loginInfo.getOperid());
		custOrder.setOprdep(loginInfo.getDeptid());
		custOrder.setOptime(new Date());
		custOrder.setPortalOrder(getBizPortalOrder(req));
		custOrder.setOrdercode(pubService.getOrderCode());
		custOrder.setOpcode(BizConstant.BizOpcode.BIZ_UP_NOTPAIDFEES);
		custOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.INIT);
		custOrder.setSyncmode(BizConstant.BizCustorderSyncmode.SYNC);
		custOrder.setVerifyphone(req.getVerifyphone());
		custOrder.setBusinessdescipt(new Gson().toJson(req.getInvoices()));
		getDAO().save(custOrder);
		return custOrder;
	}

	private BizPortalOrder getBizPortalOrder(CreatBizConfirmPayOrderReq req) throws Exception {
		BizPortalOrder portalOrder = new BizPortalOrder();
		portalOrder.setCreatetime(new Date());
		portalOrder.setFees(req.getFees());
		portalOrder.setStatus(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_NOPAY);
		portalOrder.setId(Long.parseLong(req.getBizorderid()));
		getDAO().save(portalOrder);
		return portalOrder;
	}

	/**
	 * 提交未确认收款订单
	 * 
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo bizConfirmaPay(BizConfirmaPayReq req, BizConfirmaPayResp resp) throws Exception {
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		CustOrder custOrder = (CustOrder) getDAO().find(CustOrder.class, req.getOrderid());
		CheckUtils.checkNull(custOrder, "查找不到当前订单信息");
		String descip = custOrder.getBusinessdescipt();
		CheckUtils.checkEmpty(descip, "未查询到支付用的收费明细");
		BizPortalOrder portalOrder = (BizPortalOrder) getDAO().find(BizPortalOrder.class, req.getOrderid());
		CheckUtils.checkNull(portalOrder, "查找不到当前订单信息");
		if (!req.getFees().equalsIgnoreCase(portalOrder.getFees())) {
			CheckUtils.checkNull(null, "支付金额跟订单金额不符合,请重新核对");
		}

		resp.setSuccess(true);
		resp.setMsg("已确认收款");
		BizConfirmaPayBossReq req2Boss = getBizConfirmaPayBossReq(custOrder, req);
		if(null != req)
			ParamsManager.isCorrectData(req.getPayway(),req.getPaycode());
		try {
			String jsonStr = getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.BIZ_CONFIRMPAY,
					req2Boss, loginInfo);
			BizConfirmaPayBossResp bossResp = (BizConfirmaPayBossResp) BeanUtil.jsonToObject(jsonStr,
					BizConfirmaPayBossResp.class);
			//getWgpaywayChangegetPayway
			portalOrder.setWgpayway(req.getPayway());
			portalOrder.setPayway(req.getPayway());
			portalOrder.setPaycode(req.getPaycode());
			portalOrder.setPaytime(new Date());
			portalOrder.setResporderid(Long.parseLong(bossResp.getOrderid()));
			portalOrder.setStatus(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_PAYED);
			getDAO().update(portalOrder);
		} catch (Exception e) {
			if (req.getPayway().equalsIgnoreCase(SysPayway.SYS_PAYWAY_UNIFY)) {
				portalOrder.setStatus(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_UNIFY);
				getDAO().update(portalOrder);
			}
			String msg = StringUtils.isBlank(e.getMessage()) ? "确认收款失败,请重试" : e.getMessage();
			CheckUtils.checkNull(null, msg);
		}
		return returnInfo;
	}

	private BizConfirmaPayBossReq getBizConfirmaPayBossReq(CustOrder custOrder, BizConfirmaPayReq req) {
		BizConfirmaPayBossReq req2Boss = new BizConfirmaPayBossReq();
		req2Boss.setBankaccno(req.getBankaccno());
		req2Boss.setCustid(custOrder.getCustid().toString());
		req2Boss.setPaycode(req.getPaycode());
		req2Boss.setPayreqid(req.getPayreqid());
		req2Boss.setPayway(req.getPayway());
		req2Boss.setRpay(req.getRpay());
		List<Invoice> invoices = new Gson().fromJson(custOrder.getBusinessdescipt(), new TypeToken<List<Invoice>>() {
		}.getType());
		req2Boss.setInvoices(invoices);
		return req2Boss;
	}

	/**
	 * 未确认收款用户列表查询接口
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queNotPaidCustList(QueNotCustListReq req, QueNotCustListResp resp) throws Exception {
		
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo(); 
		CheckUtils.checkEmpty(req.getGridcodes(), "网格编码不能为空");
		List params = new ArrayList();
		
		String[] codelist = req.getGridcodes().split(",");
		
		StringBuffer sb = new StringBuffer();
		sb.append("		select distinct(a.custid) custId,a.whgridcode gridcode");
		sb.append("		,a.whgridname gridname,a.custName custname,a.mobile linkMobile");
		sb.append("		,a.fees account,a.addr linkAddr");
		sb.append("		from neds.tw2_wgwsk_day_dg a");
		sb.append("		where a.whgridcode in (");
		for(int i = 0; i < codelist.length; i++){
			sb.append("?");
			params.add(codelist[i]);
			if(i != codelist.length - 1){
				sb.append(",");
			}
		}
		sb.append("	)");
		sb.append("		and a.city = ?");
		params.add(loginInfo.getCity());
		sb.append("		order by a.fees desc");
		
		Page page = new Page();
		page.setPageNo(req.getCurrentpage());
		page.setPageSize(req.getPagesize());
		page = SpringBeanUtil.getDBPersistentService().find(page, sb.toString(), NotPaidCustomerBean.class, params.toArray());
		resp.setTotalcount(page.getTotalCount());
		resp.setPageSize(page.getPageSize());
		resp.setCunpager(page.getPageNo());
		resp.setDatas(page.getResult());
		return returnInfo;
	}
}
