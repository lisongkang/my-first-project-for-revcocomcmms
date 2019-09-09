package com.maywide.biz.inter.pojo.querycatalog;

import java.util.ArrayList;
import java.util.List;

public class QuePage {
	protected int pageNo;
	protected int pageSize;
	protected String orderBy;
	protected String order;
	protected boolean autoCount;
	protected int totalCount;
//	private   List  result = new ArrayList();
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public boolean isAutoCount() {
		return autoCount;
	}
	public void setAutoCount(boolean autoCount) {
		this.autoCount = autoCount;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
//	public List getResult() {
//		return result;
//	}
//	public void setResult(List result) {
//		this.result = result;
//	}

	
	
	
}
