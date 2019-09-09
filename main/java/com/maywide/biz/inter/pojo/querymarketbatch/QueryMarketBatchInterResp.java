package com.maywide.biz.inter.pojo.querymarketbatch;

import java.util.List;

public class QueryMarketBatchInterResp implements java.io.Serializable {
	private String pagesize;//分页大小
	private String totalRecords;//总页数
	private String currentPage;//当前页码
	private List<MarketBatchInfoBO> marketBatchs;	//营销批次节点	数组
	
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
	public List<MarketBatchInfoBO> getMarketBatchs() {
		return marketBatchs;
	}
	public void setMarketBatchs(List<MarketBatchInfoBO> marketBatchs) {
		this.marketBatchs = marketBatchs;
	}

}
