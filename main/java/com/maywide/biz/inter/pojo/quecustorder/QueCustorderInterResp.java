package com.maywide.biz.inter.pojo.quecustorder;

import java.util.List;

public class QueCustorderInterResp  implements java.io.Serializable {
	private String totalRecords;// 总条数
	private String pagesize;// 当前每页条数
	private String currentPage;// 当前页数
	private List<CustordersBO> custorders;

	
	public String getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}

	public String getPagesize() {
		return pagesize;
	}

	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public List<CustordersBO> getCustorders() {
		return custorders;
	}

	public void setCustorders(List<CustordersBO> custorders) {
		this.custorders = custorders;
	}

}
