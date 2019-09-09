package com.maywide.biz.inter.pojo.queCommCust;

public class QueCommCustBossReq {

	private String custid;

	private String gridcodes;

	private String pageSize;

	private String currentPage;

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getGridcodes() {
		return gridcodes;
	}

	public void setGridcodes(String gridcodes) {
		this.gridcodes = gridcodes;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

}
