package com.maywide.biz.dl.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.cons.BizConstant.ServerCityBossInterface;
import com.maywide.biz.cons.BizConstant.SysPayway;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.dl.pojo.bizUpNotPaidFees.BizUpNotPaidFees2BossReq;
import com.maywide.biz.dl.pojo.bizUpNotPaidFees.BizUpNotPaidFeesReq;
import com.maywide.biz.dl.pojo.bizUpNotPaidFees.BizUpNotPaidFeesResp;
import com.maywide.biz.dl.pojo.bizUpNotPaidFees.Bizfeeid;
import com.maywide.biz.dl.pojo.creatNotPaidFeesOrder.CreatNotPaidFeesOrderReq;
import com.maywide.biz.dl.pojo.creatNotPaidFeesOrder.CreatNotPaidFeesOrderResp;
import com.maywide.biz.dl.pojo.queNotPaidInfo.InvlistBean;
import com.maywide.biz.dl.pojo.queNotPaidInfo.QueNotPaidFeesInfo2BossReq;
import com.maywide.biz.dl.pojo.queNotPaidInfo.QueNotPaidFeesInfo2BossResp;
import com.maywide.biz.dl.pojo.queNotPaidInfo.QueNotPaidFeesInfoBean;
import com.maywide.biz.dl.pojo.queNotPaidInfo.QueNotPaidFeesInfoReq;
import com.maywide.biz.dl.pojo.queNotPaidInfo.QueNotPaidFeesInfoResp;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.service.PubService;
import com.maywide.biz.market.entity.BizPortalOrder;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.CheckUtils;

@Service
public class NotPaidFeesService extends CommonService {

	@Autowired
	private PubService pubService;

	/**
	 * 未收款信息查询接口
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queNotPaidFeesInfo(QueNotPaidFeesInfoReq req, QueNotPaidFeesInfoResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkEmpty(req.getCustid(), "客户信息不能为空");

		QueNotPaidFeesInfo2BossReq req2Boss = getQueNotPaidFeesInfo2BossReq(req);

		String resultStr = getBossHttpInfOutput(req.getBizorderid(), ServerCityBossInterface.QUE_NOTPAIDFEES, req2Boss,
				loginInfo);
		handlerQueNotPaidInfo2BossResp(resultStr, resp);
		return returnInfo;
	}

	private QueNotPaidFeesInfo2BossReq getQueNotPaidFeesInfo2BossReq(QueNotPaidFeesInfoReq req) {
		QueNotPaidFeesInfo2BossReq req2Boss = new QueNotPaidFeesInfo2BossReq();
		req2Boss.setCustid(Long.parseLong(req.getCustid()));
		return req2Boss;
	}

	private void handlerQueNotPaidInfo2BossResp(String jsonStr, QueNotPaidFeesInfoResp resp) throws Exception {
		QueNotPaidFeesInfo2BossResp boss2Resp = (QueNotPaidFeesInfo2BossResp) BeanUtil.jsonToObject(jsonStr,
				QueNotPaidFeesInfo2BossResp.class);
		resp.setCust(boss2Resp.getCust());
		resp.setDataList(getDataList(boss2Resp));
	}

	private List<QueNotPaidFeesInfoBean> getDataList(QueNotPaidFeesInfo2BossResp boss2Resp) {
		List<QueNotPaidFeesInfoBean> dataList = new ArrayList<QueNotPaidFeesInfoBean>();
		Map<String, List<InvlistBean>> dataMap = new HashMap<String, List<InvlistBean>>();
		for (InvlistBean bean : boss2Resp.getInvlist()) {
			String key = bean.getBookno() + "_" + bean.getInvno();
			List<InvlistBean> childList = dataMap.get(key);
			if (childList == null) {
				childList = new ArrayList<InvlistBean>();
				dataMap.put(key, childList);
			}
			childList.add(bean);
		}
		Iterator<Entry<String, List<InvlistBean>>> iterator = dataMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, List<InvlistBean>> entry = iterator.next();
			QueNotPaidFeesInfoBean bean = getQueNotPaidFeesInfoBean(entry.getValue());
			dataList.add(bean);
		}
		return dataList;
	}

	private QueNotPaidFeesInfoBean getQueNotPaidFeesInfoBean(List<InvlistBean> datas) {
		QueNotPaidFeesInfoBean bean = new QueNotPaidFeesInfoBean();
		bean.setBookno(datas.get(0).getBookno());
		bean.setInvno(datas.get(0).getInvno());
		bean.setInvlist(datas);
		double total = 0d;
		for (InvlistBean childbean : datas) {
			total += childbean.getFees();
		}
		bean.setTotal(total);
		return bean;
	}

	/**
	 * 创建未收款订单接口
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo creatNotPaidFeesOrder(CreatNotPaidFeesOrderReq req, CreatNotPaidFeesOrderResp resp)
			throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CustOrder custOrder = regist4custOrder(req, loginInfo);
		resp.setCustorderid(custOrder.getId());

		return returnInfo;
	}

	private CustOrder regist4custOrder(CreatNotPaidFeesOrderReq req, LoginInfo loginInfo) throws Exception {
		CustOrder custOrder = new CustOrder();
		custOrder.setAddr(req.getAddress());
		custOrder.setAreaid(loginInfo.getAreaid());
		custOrder.setCity(loginInfo.getCity());
		custOrder.setCustid(req.getCustid());
		custOrder.setId(Long.parseLong(req.getBizorderid()));
		custOrder.setName(req.getCustName());
		custOrder.setOperator(loginInfo.getOperid());
		custOrder.setOprdep(loginInfo.getDeptid());
		custOrder.setOptime(new Date());
		custOrder.setPortalOrder(getBizPortalOrder(req));
		custOrder.setOrdercode(pubService.getOrderCode());
		custOrder.setOpcode(BizConstant.BizOpcode.BIZ_UP_NOTPAIDFEES);
		custOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.INIT);
		custOrder.setSyncmode(BizConstant.BizCustorderSyncmode.SYNC);
		custOrder.setVerifyphone(req.getVerifyphone());
		custOrder.setBusinessdescipt(new Gson().toJson(req.getBizfeeids()));
		getDAO().save(custOrder);
		return custOrder;
	}

	private BizPortalOrder getBizPortalOrder(CreatNotPaidFeesOrderReq req) throws Exception {
		BizPortalOrder portalOrder = new BizPortalOrder();
		portalOrder.setCreatetime(new Date());
		portalOrder.setFees(req.getFees());
		portalOrder.setStatus(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_NOPAY);
		portalOrder.setId(Long.parseLong(req.getBizorderid()));
		// portalOrder.setOrdertype(ordertype);
		getDAO().save(portalOrder);
		return portalOrder;
	}

	/**
	 * 未收款确认接口
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo bizUpNotPaidFees(BizUpNotPaidFeesReq req, BizUpNotPaidFeesResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
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
		BizUpNotPaidFees2BossReq req2Boss = getBizUpNotPaidFees2BossReq(req,descip);
		try {
			String jsonStr = getBossHttpInfOutput(req.getBizorderid(),
					BizConstant.ServerCityBossInterface.UP_NOTPAIDFEES, req2Boss, loginInfo);
			portalOrder.setPaytime(new Date());
			portalOrder.setStatus(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_PAYED);
			getDAO().update(portalOrder);
		} catch (Exception e) {
			if (req.getPayway().equalsIgnoreCase(SysPayway.SYS_PAYWAY_ELECTIONS)) {
				portalOrder.setStatus(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_ELECTION);
				getDAO().update(portalOrder);
			}
			String msg = StringUtils.isBlank(e.getMessage())?"确认收款失败,请重试":e.getMessage();
			CheckUtils.checkNull(null, msg);
		}

		return returnInfo;
	}

	private BizUpNotPaidFees2BossReq getBizUpNotPaidFees2BossReq(BizUpNotPaidFeesReq req,String descip) {
		BizUpNotPaidFees2BossReq req2Boss = new BizUpNotPaidFees2BossReq();
		req2Boss.setAssist(req.getAssist());
		req2Boss.setBizfeeids(req.getBizfeeids());
		req2Boss.setCustid(req.getCustid());
		req2Boss.setFees(req.getFees());
		req2Boss.setPayway(req.getPayway());
		List<Bizfeeid> bizfeeids = new Gson().fromJson(descip, new TypeToken<List<Bizfeeid>>() {}.getType());
		req2Boss.setBizfeeids(bizfeeids);
		return req2Boss;
	}
	

}
