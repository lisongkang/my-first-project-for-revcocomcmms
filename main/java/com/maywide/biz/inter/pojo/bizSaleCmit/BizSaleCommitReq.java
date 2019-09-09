package com.maywide.biz.inter.pojo.bizSaleCmit;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizSaleCommitReq extends BaseApiRequest {

	private String orderid;

	private String payway;
	
	private String bankaccno;
	
	private String payreqid;
	
	private String paycode;
	
	private String wgpayway;
	
	private String multipaywayflag;
	
	private String cashe;
	
	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
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

	public String getWgpayway() {
		return wgpayway;
	}

	public void setWgpayway(String wgpayway) {
		this.wgpayway = wgpayway;
	}

	public String getMultipaywayflag() {
		return multipaywayflag;
	}

	public void setMultipaywayflag(String multipaywayflag) {
		this.multipaywayflag = multipaywayflag;
	}

	public String getCashe() {
		return cashe;
	}

	public void setCashe(String cashe) {
		this.cashe = cashe;
	}
	
	
	
}
