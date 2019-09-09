package com.maywide.biz.inter.pojo.bizInstallCommit;

public class BizInstallCommit2BossReq {

	private String orderid;
	
	private String payway;
	
	private String bankaccno;
	
	private String payreqid;
	
	private String paycode;
	
	private String custid;
	
	private String installtype;
	
	private String houseid;

	private Integer feebacktype; //负金额退费方式 0-退现金，1-预存款
	
	private String multipaywayflag;

	public Integer getFeebacktype() {
		return feebacktype;
	}

	public void setFeebacktype(Integer feebacktype) {
		this.feebacktype = feebacktype;
	}

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

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getInstalltype() {
		return installtype;
	}

	public void setInstalltype(String installtype) {
		this.installtype = installtype;
	}

	public String getHouseid() {
		return houseid;
	}

	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}

	public String getMultipaywayflag() {
		return multipaywayflag;
	}

	public void setMultipaywayflag(String multipaywayflag) {
		this.multipaywayflag = multipaywayflag;
	}
	
	
	
}
