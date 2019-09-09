package com.maywide.biz.inter.pojo.queAsyncOrder;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueAsyncOrderListReq extends BaseApiRequest {

	private int pageSize;
	
	private int currentPage = 1;
	
	private String fromDate;
	
	private String toDate;
	
	private String orderstatus;
	
	private String dealstatus;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
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
