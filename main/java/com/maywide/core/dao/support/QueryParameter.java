package com.maywide.core.dao.support;

import java.io.Serializable;

public class QueryParameter implements Serializable {
	public static final String ASC = "asc";
	public static final String DESC = "desc";
	protected int pageNo;
	protected int pageSize;
	protected String orderBy;
	protected String order;
	protected boolean autoCount;
	protected int totalCount;

	public QueryParameter() {
		this.pageNo = 1;

		this.pageSize = 0;

		this.orderBy = null;

		this.order = "asc";

		this.autoCount = true;

		this.totalCount = 0;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isPageSizeSetted() {
		return (this.pageSize > -1);
	}

	public int getPageNo() {
		return this.pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getFirst() {
		if ((this.pageNo < 1) || (this.pageSize < 1)) {
			return -1;
		}
		int first = (this.pageNo - 1) * this.pageSize;
		if (this.totalCount <= first)
			first -= this.pageSize;
		return first;
	}

	public boolean isFirstSetted() {
		return ((this.pageNo > 0) && (this.pageSize > 0));
	}

	public String getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isOrderBySetted() {
		return ((this.orderBy != null) && (!(this.orderBy.trim().equals(""))));
	}

	public String getOrder() {
		return this.order;
	}

	public void setOrder(String order) {
		if (("asc".equalsIgnoreCase(order)) || ("desc".equalsIgnoreCase(order)))
			this.order = order.toLowerCase();
		else if ((null == order) || ("".equalsIgnoreCase(order)))
			this.order = "desc";
		else
			throw new IllegalArgumentException(
					"order should be 'desc' or 'asc'");
	}

	public boolean isAutoCount() {
		return this.autoCount;
	}

	public void setAutoCount(boolean autoCount) {
		this.autoCount = autoCount;
	}
}