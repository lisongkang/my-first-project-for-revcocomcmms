package com.maywide.biz.pay.unify.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.maywide.biz.cons.BizConstant;
import com.maywide.core.util.DateUtils;

public class UnifyPayBillBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String orderid;
	private String resporderid;
	private String custid;
	private Date paytime;
	private String fees;
	private String status;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getResporderid() {
		return resporderid;
	}

	public void setResporderid(String resporderid) {
		this.resporderid = resporderid;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public Date getPaytime() {
		return paytime;
	}

	public String getPaytimeStr() {
		return DateUtils.formatDate(paytime, "yyyyMMddHHmmss");
	}

	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}

	public String getFees() {
		return fees;
	}

	// 分为单位
	public String getFeesStr() {
		return String.valueOf(new BigDecimal(fees).multiply(new BigDecimal(100)).intValue());
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// 0：正常扣费，1：已充值，2：冲正失败，3：扣费失败
	public String getPayStatus() {
		return BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_PAYED.equals(status) ? "0" : "3";
	}

}
