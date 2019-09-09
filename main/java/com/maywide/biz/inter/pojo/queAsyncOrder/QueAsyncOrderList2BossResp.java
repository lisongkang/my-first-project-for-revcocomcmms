package com.maywide.biz.inter.pojo.queAsyncOrder;

import java.util.List;

public class QueAsyncOrderList2BossResp {

	private int totalRecords;
	
	private int pagesize;
	
	private int currentPage;
	
	private List<AsyncOrderBean> orders;

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
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

	public List<AsyncOrderBean> getOrders() {
		return orders;
	}

	public void setOrders(List<AsyncOrderBean> orders) {
		this.orders = orders;
	}
	
	
	
}
