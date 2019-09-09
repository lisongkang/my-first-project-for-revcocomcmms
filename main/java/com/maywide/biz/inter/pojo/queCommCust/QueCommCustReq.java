package com.maywide.biz.inter.pojo.queCommCust;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueCommCustReq extends BaseApiRequest {

	private String custid;
	
	private String pageSize;
	
	private String currentPage;
	
	private String gridcode;

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
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

	public String getGridcode() {
		return gridcode;
	}

	public void setGridcode(String gridcode) {
		this.gridcode = gridcode;
	}
	
	
}
