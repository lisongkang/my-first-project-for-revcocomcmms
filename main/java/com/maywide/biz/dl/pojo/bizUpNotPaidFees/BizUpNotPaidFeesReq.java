package com.maywide.biz.dl.pojo.bizUpNotPaidFees;

import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizUpNotPaidFeesReq extends BaseApiRequest {
	
	private Long orderid;
	
	private String custid;

	private String assist;
	
	private String payway;
	
	private String fees;
	
	private List<Bizfeeid> bizfeeids;


	public String getPayway() {
		return payway;
	}

	public void setPayway(String payway) {
		this.payway = payway;
	}


	public List<Bizfeeid> getBizfeeids() {
		return bizfeeids;
	}

	public void setBizfeeids(List<Bizfeeid> bizfeeids) {
		this.bizfeeids = bizfeeids;
	}

	public String getAssist() {
		return assist;
	}

	public void setAssist(String assist) {
		this.assist = assist;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	
}
