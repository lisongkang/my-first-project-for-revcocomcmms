package com.maywide.biz.inter.pojo.quebustraillistinfo;

import java.io.Serializable;
import java.util.List;

public class QueBustraillistinfoBossResp implements Serializable {
	private String pagesize;
	private String totalRecords;
	private String currentPage;
	private List<QueBustraillistinfoBossChildResp> buslist;
	public String getPagesize() {
		return pagesize;
	}
	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
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
	public List<QueBustraillistinfoBossChildResp> getBuslist() {
		return buslist;
	}
	public void setBuslist(List<QueBustraillistinfoBossChildResp> buslist) {
		this.buslist = buslist;
	}
}
