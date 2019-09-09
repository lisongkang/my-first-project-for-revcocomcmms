package com.maywide.biz.inter.pojo.queAsyncOrder;

public class QueAsyncOrderList2BossReq {

	private String operid;
	
	private String deptid;
	
	private int pagesize;
	
	private int currentPage = 1;
	
	private String fromDate;
	
	private String toDate;
	
	private String orderstatus;
	
	private String dealstatus;

	public String getOperid() {
		return operid;
	}

	public void setOperid(String operid) {
		this.operid = operid;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getDealstatus() {
		return dealstatus;
	}

	public void setDealstatus(String dealstatus) {
		this.dealstatus = dealstatus;
	}
	
	
	
}
