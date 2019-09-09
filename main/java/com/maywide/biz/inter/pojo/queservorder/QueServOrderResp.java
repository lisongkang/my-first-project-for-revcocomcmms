package com.maywide.biz.inter.pojo.queservorder;

import java.util.List;

public class QueServOrderResp {

	private int totalrecords;
	private int pagesize;
	private int currentpage;
	private List<ServOrderBean> orders;

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

	public List<ServOrderBean> getOrders() {
		return orders;
	}

	public void setOrders(List<ServOrderBean> orders) {
		this.orders = orders;
	}

}
