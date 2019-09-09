package com.maywide.biz.inter.pojo.quearreardet;

import java.util.List;

public class QueArreardetInterResp implements java.io.Serializable {

	private String arrearsun;// 欠费总额
	private int totalRecords;// 总条数
	private int pagesize;// 当前每页条数
	private int currentPage;// 当前页数
	private List<EarDetsBO> arreardets;// 欠费列表

	public String getArrearsun() {
		return arrearsun;
	}

	public void setArrearsun(String arrearsun) {
		this.arrearsun = arrearsun;
	}

	public List<EarDetsBO> getArreardets() {
		return arreardets;
	}

	public void setArreardets(List<EarDetsBO> arreardets) {
		this.arreardets = arreardets;
	}

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

}
