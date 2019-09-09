package com.maywide.biz.dl.pojo.queConfirmFee;

public class QueConfirmFeeBossReq {

	private String netids;
	
	private int pagesize = 1;
	
	private int currentPage = 10;

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
	
	
	
	
}
