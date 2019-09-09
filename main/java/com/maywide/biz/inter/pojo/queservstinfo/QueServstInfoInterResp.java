package com.maywide.biz.inter.pojo.queservstinfo;

import java.util.List;

public class QueServstInfoInterResp implements java.io.Serializable {

	private String totalRecords;// 总条数
	private String pagesize;// 当前每页条数
	private String currentPage;// 当前页数
	private List<CustInfosBO> custs;
	
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
	public List<CustInfosBO> getCusts() {
		return custs;
	}
	public void setCusts(List<CustInfosBO> custs) {
		this.custs = custs;
	}

}
