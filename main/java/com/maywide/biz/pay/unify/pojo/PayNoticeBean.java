package com.maywide.biz.pay.unify.pojo;

import java.io.Serializable;

public class PayNoticeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String orderNo;

	private String requestid;

	private String status;

	private String message;
	
	private String payway;
	
	private String paycode;
	
	private String payname;
	
	private String paytime;

	private String openid;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	private String paySeriaNo;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getRequestid() {
		return requestid;
	}

	public void setRequestid(String requestid) {
		this.requestid = requestid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPayway() {
		return payway;
	}

	public void setPayway(String payway) {
		this.payway = payway;
	}

	public String getPaycode() {
		return paycode;
	}

	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}

	public String getPayname() {
		return payname;
	}

	public void setPayname(String payname) {
		this.payname = payname;
	}

	public String getPaytime() {
		return paytime;
	}

	public void setPaytime(String paytime) {
		this.paytime = paytime;
	}

	public String getPaySeriaNo() {
		return paySeriaNo;
	}

	public void setPaySeriaNo(String paySeriaNo) {
		this.paySeriaNo = paySeriaNo;
	}
	
	

}
