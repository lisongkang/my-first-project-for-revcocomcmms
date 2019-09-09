package com.maywide.biz.inter.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.entity.TmpSuppementGathering;
import com.maywide.biz.inter.pojo.handlerSupplementGather.HandlerSupplementGatherReq;
import com.maywide.biz.inter.pojo.handlerSupplementGather.HandlerSupplementGatherResp;
import com.maywide.biz.inter.pojo.queSupplementCustOrderInfo.QueSupplementCustOrderInfoReq;
import com.maywide.biz.inter.pojo.queSupplementCustOrderInfo.QueSupplementCustOrderInfoResp;
import com.maywide.biz.inter.pojo.supplementFees.SupplementFeesReq;
import com.maywide.biz.inter.pojo.supplementFees.SupplementFeesResp;
import com.maywide.biz.pay.unify.pojo.QueUnifyPayInfoResp;
import com.maywide.biz.pay.unify.service.UnifyPayService;
import com.maywide.core.mcr.BusinessException;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.CheckUtils;


/**
 * 订单收款补收类
 * @author qiujiongtian
 *
 */
@Service
public class SupplementGatheringService extends CommonService {

	@Autowired
	private UnifyPayService unifyPayService;
	
	/**
	 * 查询该用户的该补缴费用
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queSupplementCustOrderInfo(QueSupplementCustOrderInfoReq req,
			QueSupplementCustOrderInfoResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req.getCustId(), "客户编号不能为空");
		
		TmpSuppementGathering tmpGather = new TmpSuppementGathering();
		tmpGather.setCustid(req.getCustId());
		tmpGather.setOrderStatus("Y");
		
		List<TmpSuppementGathering> lists = DAO.find(tmpGather);
		if(BeanUtil.isListNull(lists) || lists.isEmpty()) {
			CheckUtils.checkNull(null, "当前客户不存在未收费或已缴费完成");
		}
		tmpGather = lists.get(0);
		resp.setBizOrderId(tmpGather.getBizOrderId().toString());
		resp.setCustId(tmpGather.getCustid());
		resp.setCustName(tmpGather.getCustName());
		resp.setFees(tmpGather.getFees());
		resp.setAddr(tmpGather.getAddr());
		resp.setOperName(tmpGather.getOperName());
		resp.setCustPhone(tmpGather.getCustPhone());
		resp.setOperPhone(tmpGather.getOperPhone());
		resp.setDepartName(tmpGather.getOperDepartment());
		return returnInfo;
	}
	
	
	/**
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo supplementFees(SupplementFeesReq req,
			SupplementFeesResp resp) throws Exception {
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req.getCustId(), "客户编号不能为空");
		CheckUtils.checkNull(req.getBizOrderId(), "订单id不能为空");
		
		TmpSuppementGathering tmpGather = (TmpSuppementGathering) DAO.find(TmpSuppementGathering.class, 
				req.getBizOrderId()); 
		CheckUtils.checkNull(tmpGather, "查询不到该订单!");
		if(!tmpGather.getOrderStatus().equalsIgnoreCase("y")){
			CheckUtils.checkNull(null, "该订单状态不可支付");
		}
		QueUnifyPayInfoResp unifyPayInfo = unifyPayService.generatePayReq(tmpGather.getFees(), tmpGather.getBizOrderId().toString(),
				"费用补缴", tmpGather.getCustName(), tmpGather.getCustid().toString());
		resp.setAdUrl(unifyPayInfo.getUrl());
		return returnInfo;
	}
	
	
	public ReturnInfo handlerSupplementGather(HandlerSupplementGatherReq req,HandlerSupplementGatherResp resp) throws Exception {
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req.getBizOrderId(), "订单编号不能为空");
		
		TmpSuppementGathering tmpGather = (TmpSuppementGathering) DAO.find(TmpSuppementGathering.class, 
				req.getBizOrderId()); 
		CheckUtils.checkNull(tmpGather, "查询不到该订单!");
		if(!tmpGather.getOrderStatus().equalsIgnoreCase("y")) {
			CheckUtils.checkNull(null, "该订单状态不可支付");
		}
		if(!req.getFees().equals(tmpGather.getFees())) {
			log.error("订单金额跟支付金额不一致!");
		}
		tmpGather.setOrderStatus("N");
		tmpGather.setPayOperid(loginInfo.getOperid());
		tmpGather.setPayDate(new Date());
		DAO.saveOrUpdate(tmpGather);
		resp.setCustorderid(req.getBizOrderId());
		return returnInfo;
	}
	
}
