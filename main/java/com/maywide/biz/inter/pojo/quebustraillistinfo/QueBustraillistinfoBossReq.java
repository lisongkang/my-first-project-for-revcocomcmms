package com.maywide.biz.inter.pojo.quebustraillistinfo;

import java.io.Serializable;

public class QueBustraillistinfoBossReq implements Serializable {
	private long busid;
	private String pagesize;
	private String currentPage;
	public long getBusid() {
		return busid;
	}
	public void setBusid(long busid) {
		this.busid = busid;
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
}
