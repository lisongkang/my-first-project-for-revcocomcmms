package com.maywide.biz.pay.unify.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.pojo.bizConfPay.BizConfirmaPayReq;
import com.maywide.biz.inter.pojo.bizConfPay.BizConfirmaPayResp;
import com.maywide.biz.inter.pojo.bizInstallCommit.BizInstallCommitReq;
import com.maywide.biz.inter.pojo.bizInstallCommit.BizInstallCommitResp;
import com.maywide.biz.inter.pojo.bizSaleCmit.BizSaleCommitReq;
import com.maywide.biz.inter.pojo.chargeFeeBook.QuePayOrderResp;
import com.maywide.biz.inter.pojo.chgdev.ChgDevWGReq;
import com.maywide.biz.inter.pojo.equipInfoSubmit.EquipInfoSubmitReq;
import com.maywide.biz.inter.pojo.equipInfoSubmit.EquipInfoSubmitResp;
import com.maywide.biz.inter.pojo.handlerSupplementGather.HandlerSupplementGatherReq;
import com.maywide.biz.inter.pojo.handlerSupplementGather.HandlerSupplementGatherResp;
import com.maywide.biz.inter.pojo.ordercommit.OrderCommitInterReq;
import com.maywide.biz.inter.pojo.ordercommit.OrderCommitInterResp;
import com.maywide.biz.inter.service.ChgDevService;
import com.maywide.biz.inter.service.CustBizOrderPoolService;
import com.maywide.biz.inter.service.InterConfirmaPayService;
import com.maywide.biz.inter.service.InterPrdService;
import com.maywide.biz.inter.service.InterResSaleService;
import com.maywide.biz.inter.service.InterSyncApplyService;
import com.maywide.biz.inter.service.SupplementGatheringService;
import com.maywide.biz.pay.unify.entity.PayRecordLog;
import com.maywide.core.exception.BusinessException;

@Service
public class PayNoticeService extends CommonService {

	@Autowired
	private InterPrdService interPrdService;
	@Autowired
	private ChgDevService chgDevService;
	@Autowired
	private InterConfirmaPayService interConfirmPayService;
	@Autowired
	private InterSyncApplyService interSyncApplyService;
	@Autowired
	private InterResSaleService resSaleService;
	
	@Autowired
	private CustBizOrderPoolService custBizOrderPoolService;
	
	@Autowired
	private SupplementGatheringService supplementGatheringService;

	public void bizSaleCommit(String orderid,QuePayOrderResp order, String extra,String multipaywayflag,String cashe) throws Exception {
		BizSaleCommitReq req = new BizSaleCommitReq();
		req.setOrderid(orderid);
		req.setPayway(order.getPayway());
		req.setWgpayway(BizConstant.SysPayway.SYS_PAYWAY_UNIFY);
		req.setPaycode(order.getBossPaycode());
		req.setPayreqid(order.getPaySerialNo());
		req.setBizorderid(getNewBizOrderid());
		req.setMultipaywayflag(multipaywayflag);
		req.setCashe(cashe);
		ReturnInfo returnInfo = resSaleService.bizSaleCommit(req);
		if (returnInfo.getCode() != IErrorDefConstant.ERROR_SUCCESS_CODE) {
			throw new BusinessException("订单确认失败：" + returnInfo.getMessage());
		}
	}

	public void orderCommit(QuePayOrderResp order, String extra,String multipaywayflag,String cashe) throws Exception {
		OrderCommitInterReq req = new OrderCommitInterReq();
		OrderCommitInterResp resp = new OrderCommitInterResp();
		req.setWgpayway(BizConstant.SysPayway.SYS_PAYWAY_UNIFY);
		req.setBizorderid(getNewBizOrderid());
		req.setCustorderid(order.getOrderNo());
		req.setPayway(order.getPayway());
		req.setPaycode(order.getBossPaycode());
		req.setPayreqid(order.getPaySerialNo());
		req.setMultipaywayflag(multipaywayflag);
		req.setCashe(cashe);
		if (StringUtils.isNotBlank(extra)) {
			ObjectMapper jsonMapper = new ObjectMapper();
			jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String extra2 = extra.replace("\\\"", "\"");
			JSONObject extraObj = new JSONObject(extra2);
			req.setMobile(extraObj.optString("mobile"));
			req.setSmartCardNo(extraObj.optString("smartCardNo"));
		}

		ReturnInfo returnInfo = interPrdService.doOrderCommit(req, resp);
		if (returnInfo.getCode() != IErrorDefConstant.ERROR_SUCCESS_CODE) {
			throw new BusinessException("订单确认失败：" + returnInfo.getMessage());
		}
	}

	public void changeDevice(QuePayOrderResp order, String extra,String multipaywayflag,String cashe) throws Exception {
		ChgDevWGReq req = new ChgDevWGReq();
		req.setWgpayway(BizConstant.SysPayway.SYS_PAYWAY_UNIFY);
		req.setBizorderid(getNewBizOrderid());
		req.setCustorderid(order.getOrderNo());
		req.setPayway(order.getPayway());
		req.setPaycode(order.getBossPaycode());
		req.setPayreqid(order.getPaySerialNo());
		req.setMultipaywayflag(multipaywayflag);
		req.setCashe(cashe);
		String installtype = "0";
		req.setInstalltype(installtype);
		if (StringUtils.isNotBlank(extra)) {
			String extra2 = extra.replace("\\\"", "\"");
			JSONObject extraObj = new JSONObject(extra2);
			req.setCustid(extraObj.optString("custid"));
			req.setKeyno(extraObj.optString("keyno"));
		}

		ReturnInfo returnInfo = chgDevService.save(req);
		if (returnInfo.getCode() != IErrorDefConstant.ERROR_SUCCESS_CODE) {
			throw new BusinessException("设备更换确认失败：" + returnInfo.getMessage());
		}
	}

	public void bizConfirmaPay(QuePayOrderResp order, String extra) throws Exception {
		BizConfirmaPayReq req = new BizConfirmaPayReq();
		BizConfirmaPayResp resp = new BizConfirmaPayResp();
		req.setWgpayway(BizConstant.SysPayway.SYS_PAYWAY_UNIFY);
		req.setBizorderid(getNewBizOrderid());
		req.setOrderid(Long.parseLong(order.getOrderNo()));
		req.setPayway(order.getPayway());
		req.setPaycode(order.getBossPaycode());
		req.setPayreqid(order.getPaySerialNo());
		req.setFees(String.valueOf(order.getTotalFee()));
		req.setRpay(String.valueOf(order.getTotalFee()));

		ReturnInfo returnInfo = interConfirmPayService.bizConfirmaPay(req, resp);
		if (returnInfo.getCode() != IErrorDefConstant.ERROR_SUCCESS_CODE) {
			throw new BusinessException("未收款确认失败：" + returnInfo.getMessage());
		}
	}

	public void bizInstallCommit(QuePayOrderResp order, String extra,String multipaywayflag,String cashe) throws Exception {
		BizInstallCommitReq req = new BizInstallCommitReq();
		BizInstallCommitResp resp = new BizInstallCommitResp();
		req.setWgpayway(BizConstant.SysPayway.SYS_PAYWAY_UNIFY);
		req.setBizorderid(getNewBizOrderid());
		req.setCustOrderid(Long.parseLong(order.getOrderNo()));
		req.setPayway(order.getPayway());
		req.setPaycode(order.getBossPaycode());
		req.setPayreqid(order.getPaySerialNo());
		req.setMultipaywayflag(multipaywayflag);
		req.setCashe(cashe);
		ReturnInfo returnInfo = interSyncApplyService.bizInstallCommit(req, resp);
		if (returnInfo.getCode() != IErrorDefConstant.ERROR_SUCCESS_CODE) {
			throw new BusinessException("开户失败：" + returnInfo.getMessage());
		}
	}

	private String getNewBizOrderid() throws Exception {
//		return getDAO().getSequence("SEQ_BIZ_CUSTORDER_ID").toString();
		return getBizorderid();
	}

	public void handlerSupplementGather(String bizorderId, String fees) throws Exception {
		HandlerSupplementGatherReq req = new HandlerSupplementGatherReq();
		req.setBizOrderId(Long.parseLong(bizorderId));
		req.setFees(fees);
		HandlerSupplementGatherResp resp = new HandlerSupplementGatherResp();
		ReturnInfo returnInfo = supplementGatheringService.handlerSupplementGather(req, resp);
		if (returnInfo.getCode() != IErrorDefConstant.ERROR_SUCCESS_CODE) {
			throw new BusinessException("补缴失败：" + returnInfo.getMessage());
		}
	}

	public PayRecordLog recordPay(String orderid) {
		try {
			PayRecordLog recordLog = new PayRecordLog();
			recordLog.setOrderid(Long.valueOf(orderid));
			recordLog.setPayresult("0");
			recordLog.setPaydate(new Date());
			DAO.save(recordLog);
			return recordLog;
		} catch (Exception e) {
			log.error("=============create paylog fail ================:"+e.getMessage());
		}
		return null;
	}

	public void updatePayRecordStatus(PayRecordLog recordLog) {
		if(recordLog == null) return;
		try {
			DAO.saveOrUpdate(recordLog);
		} catch (Exception e) {
			log.error("=============update recordlog fail ================"+e.getMessage());
		}
		
	}
	/**
	 * 业务工单提交接口
	 * @param orderid
	 * @param rpay
	 * @param payway
	 * @param subpay
	 * @throws Exception
	 */
	public void submitBizCustOrder(String orderid,String rpay,String payway,String subpay) throws Exception {
		EquipInfoSubmitReq apiReq = new EquipInfoSubmitReq();
		apiReq.setBizorderid(getNewBizOrderid());
//		apiReq.setRecid(Long.parseLong(orderid));
		apiReq.setServorderid(Long.parseLong(orderid));
		apiReq.setPayway(payway);
		apiReq.setRpay(rpay);
		apiReq.setSubpay(subpay);
		EquipInfoSubmitResp resp = new EquipInfoSubmitResp();
		ReturnInfo returnInfo = custBizOrderPoolService.equipInfoSubmit(apiReq,resp);
		if (returnInfo.getCode() != IErrorDefConstant.ERROR_SUCCESS_CODE) {
			throw new BusinessException("业务工单设备分配失败：" + returnInfo.getMessage());
		}
	}

}
