package com.maywide.biz.inter.pojo.taskfeedback;

public class TaskFeedbackReq {

	private String activityid;
	private String policyid;
	private String compid;
	private String custid;
	private String itemid;
	private String ostatus;
	private String rdesc;
	private String memo;

	public String getActivityid() {
		return activityid;
	}

	public void setActivityid(String activityid) {
		this.activityid = activityid;
	}

	public String getPolicyid() {
		return policyid;
	}

	public void setPolicyid(String policyid) {
		this.policyid = policyid;
	}

	public String getCompid() {
		return compid;
	}

	public void setCompid(String compid) {
		this.compid = compid;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public String getRdesc() {
		return rdesc;
	}

	public void setRdesc(String rdesc) {
		this.rdesc = rdesc;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOstatus() {
		return ostatus;
	}

	public void setOstatus(String ostatus) {
		this.ostatus = ostatus;
	}

}
