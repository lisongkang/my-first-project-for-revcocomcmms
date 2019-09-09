package com.maywide.biz.inter.pojo.exempt;

import java.util.List;

public class QueExemptApplyResp {

	private int totalrecords;
	private int pagesize;
	private int currentpage;
	private List<ExemptOrderBean> orders;

	public int getTotalrecords() {
		return totalrecords;
	}

	public void setTotalrecords(int totalrecords) {
		this.totalrecords = totalrecords;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}

	public List<ExemptOrderBean> getOrders() {
		return orders;
	}

	public void setOrders(List<ExemptOrderBean> orders) {
		this.orders = orders;
	}
}
