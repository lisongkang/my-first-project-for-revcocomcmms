package com.maywide.biz.dl.pojo.queUserCount;

public class QueUserCountBossReq {

	private String netids;
	
	private int pagesize = 1;
	
	private int currentPage = 10;

	private String custtype;
	
	private String permark;

	public String getNetids() {
		return netids;
	}

	public void setNetids(String netids) {
		this.netids = netids;
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

	public String getCusttype() {
		return custtype;
	}

	public void setCusttype(String custtype) {
		this.custtype = custtype;
	}

	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}
	
	
}
