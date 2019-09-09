package com.maywide.biz.inter.pojo.payCart;

import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class PayCartReq extends BaseApiRequest{

	private String payway;

	private String bankaccno;

	private String payreqid;

	private String paycode;

	private String mobile;

	private List<PayChildCartReq> orderInfos;

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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List<PayChildCartReq> getOrderInfos() {
		return orderInfos;
	}

	public void setOrderInfos(List<PayChildCartReq> orderInfos) {
		this.orderInfos = orderInfos;
	}
	
	
	
}
