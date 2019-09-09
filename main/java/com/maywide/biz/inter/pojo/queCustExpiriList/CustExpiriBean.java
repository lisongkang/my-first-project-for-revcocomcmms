package com.maywide.biz.inter.pojo.queCustExpiriList;

public class CustExpiriBean {

	private String custid;
	
	private String custName;
	
	private String linkAddr;
	
	private String mobile;
	
	private String pid;
	
	private String servid;
	
	private String houseid;
	
	private String permark;
	
	private String salesid;
	
	private String salesname;
	
	private String arrearFees;
	
	private String gridname;
	
	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getLinkAddr() {
		return linkAddr;
	}

	public void setLinkAddr(String linkAddr) {
		this.linkAddr = linkAddr;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public CustExpiriBean(String custid, String custName, String linkAddr, String mobile) {
		super();
		this.custid = custid;
		this.custName = custName;
		this.linkAddr = linkAddr;
		this.mobile = mobile;
	}

	public CustExpiriBean() {
		super();
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getServid() {
		return servid;
	}

	public void setServid(String servid) {
		this.servid = servid;
	}

	public String getHouseid() {
		return houseid;
	}

	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}

	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}

	public String getSalesid() {
		return salesid;
	}

	public void setSalesid(String salesid) {
		this.salesid = salesid;
	}

	public String getSalesname() {
		return salesname;
	}

	public void setSalesname(String salesname) {
		this.salesname = salesname;
	}

	public String getArrearFees() {
		return arrearFees;
	}

	public void setArrearFees(String arrearFees) {
		this.arrearFees = arrearFees;
	}

	public String getGridname() {
		return gridname;
	}

	public void setGridname(String gridname) {
		this.gridname = gridname;
	}
	
	
	
}
