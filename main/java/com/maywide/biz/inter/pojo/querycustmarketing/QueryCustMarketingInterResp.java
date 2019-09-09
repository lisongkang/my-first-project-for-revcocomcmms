package com.maywide.biz.inter.pojo.querycustmarketing;

import java.util.List;

public class QueryCustMarketingInterResp implements java.io.Serializable {
	private String pagesize;//分页大小
	private String totalRecords;//总页数
	private String currentPage;//当前页码
	private List<CustMarketingInfoBO> custMarketings;	//客户营销信息节点	数组
	
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
	public List<CustMarketingInfoBO> getCustMarketings() {
		return custMarketings;
	}
	public void setCustMarketings(List<CustMarketingInfoBO> custMarketings) {
		this.custMarketings = custMarketings;
	}

	
}
