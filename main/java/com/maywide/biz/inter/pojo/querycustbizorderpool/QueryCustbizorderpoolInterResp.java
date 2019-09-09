package com.maywide.biz.inter.pojo.querycustbizorderpool;

import java.io.Serializable;
import java.util.List;

import com.maywide.biz.inter.entity.CustBizOrderPool;

public class QueryCustbizorderpoolInterResp implements Serializable {
	private String totalRecords;// 总条数
	private String pagesize;// 当前每页条数
	private String currentPage;// 当前页数
	private List<CustBizOrderPool> custBizOrderPoolList;

	
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

	public List<CustBizOrderPool> getCustBizOrderPoolList() {
		return custBizOrderPoolList;
	}

	public void setCustBizOrderPoolList(List<CustBizOrderPool> custBizOrderPoolList) {
		this.custBizOrderPoolList = custBizOrderPoolList;
	}
}
