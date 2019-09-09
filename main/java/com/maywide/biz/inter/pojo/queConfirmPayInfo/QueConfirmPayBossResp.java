package com.maywide.biz.inter.pojo.queConfirmPayInfo;

import java.util.List;

public class QueConfirmPayBossResp {

	private int currentPage;
	
	private int pagesize;
	
	private int totalRecords;
	
	private List<ConfirmInvoice> confirmInvoices;

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public List<ConfirmInvoice> getConfirmInvoices() {
		return confirmInvoices;
	}

	public void setConfirmInvoices(List<ConfirmInvoice> confirmInvoices) {
		this.confirmInvoices = confirmInvoices;
	}
	
	
	
}
