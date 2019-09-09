package com.maywide.biz.inter.pojo.queNotCustList;

public class QueNotCustListReq {

	private int currentpage;
	
	private String gridcodes;

	private int pagesize = 15;
	
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

	public String getGridcodes() {
		return gridcodes;
	}

	public void setGridcodes(String gridcodes) {
		this.gridcodes = gridcodes;
	}

	
	
	
}
