package com.maywide.biz.inter.pojo.bizConfPay;

import com.googlecode.jsonplugin.annotations.JSON;
import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizConfirmaPayReq extends BaseApiRequest {

	private Long orderid;
	
	private String payway;
	
	private String bankaccno;
	
	private String payreqid;
	
	private String paycode;
	
	private String rpay;
	
	private String fees;
	
	private String wgpayway;
    
	@JSON(serialize = false)
    public String getWgpayway() {
		return wgpayway;
	}

	public void setWgpayway(String wgpayway) {
		this.wgpayway = wgpayway;
	}
	
	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	public String getPayway() {
		return payway;
	}

	public void setPayway(String payway) {
		this.payway = payway;
	}

	public String getBankaccno() {
		return bankaccno;
	}

	public void setBankaccno(String bankaccno) {
		this.bankaccno = bankaccno;
	}

	public String getPayreqid() {
		return payreqid;
	}

	public void setPayreqid(String payreqid) {
		this.payreqid = payreqid;
	}

	public String getPaycode() {
		return paycode;
	}

	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}

	public String getRpay() {
		return rpay;
	}

	public void setRpay(String rpay) {
		this.rpay = rpay;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}
	
	
}
