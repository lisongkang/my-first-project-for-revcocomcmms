package com.maywide.biz.inter.pojo.queCommCust;

import java.util.List;

public class QueCommCustResp {

	private String pageSize;
	
	private String totalRecords;
	
	private String currentPage;
	
	private List<CustlistBean> custlist;

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public List<CustlistBean> getCustlist() {
		return custlist;
	}

	public void setCustlist(List<CustlistBean> custlist) {
		this.custlist = custlist;
	}
	
	
	
}
