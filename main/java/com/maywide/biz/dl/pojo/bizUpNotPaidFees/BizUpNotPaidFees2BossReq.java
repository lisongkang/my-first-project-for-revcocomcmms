package com.maywide.biz.dl.pojo.bizUpNotPaidFees;

import java.util.List;

public class BizUpNotPaidFees2BossReq {

	private String custid;
	
	private String fees;
	
	private String payway;
	
	private String assist;
	
	private List<Bizfeeid> bizfeeids;

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public String getPayway() {
		return payway;
	}

	public void setPayway(String payway) {
		this.payway = payway;
	}

	public String getAssist() {
		return assist;
	}

	public void setAssist(String assist) {
		this.assist = assist;
	}

	public List<Bizfeeid> getBizfeeids() {
		return bizfeeids;
	}

	public void setBizfeeids(List<Bizfeeid> bizfeeids) {
		this.bizfeeids = bizfeeids;
	}
	
	
	
}
