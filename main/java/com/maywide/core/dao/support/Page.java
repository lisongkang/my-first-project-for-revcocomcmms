package com.maywide.core.dao.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page<T> extends QueryParameter implements Serializable {
	private List<T> result = null;

	public Page() {
	}

	public Page(int pageSize) {
		this.pageSize = pageSize;
		this.orderBy = null;
	}

	public Page(int pageSize, boolean autoCount) {
		this.pageSize = pageSize;
		this.autoCount = autoCount;
		this.orderBy = null;
	}

	public Page(int pageNo, int pageSize, List<T> list) {
		if ((list == null) || (list.size() == 0))
			return;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalCount = list.size();
		this.orderBy = null;
		int sindex = (pageNo - 1) * pageSize;
		int eindex = sindex + pageSize;
		if (eindex > this.totalCount)
			eindex = this.totalCount;
		this.result = new ArrayList();
		for (int i = sindex; i < eindex; ++i)
			this.result.add(list.get(i));
	}

	public String getInverseOrder() {
		if (this.order.endsWith("desc")) {
			return "asc";
		}
		return "desc";
	}

	public List<T> getResult() {
		return this.result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public int getTotalCount() {
		if (this.totalCount == -1) {
			return 0;
		}
		return this.totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPages() {
		if (this.totalCount == -1) {
			return 0;
		}
		int count = this.totalCount / this.pageSize;
		if (this.totalCount % this.pageSize > 0) {
			++count;
		}
		return count;
	}

	public boolean isHasNext() {
		return (this.pageNo + 1 < getTotalPages());
	}

	public int getNextPage() {
		if (isHasNext()) {
			return (this.pageNo + 1);
		}
		return this.pageNo;
	}

	public boolean isHasPre() {
		return (this.pageNo - 1 >= 1);
	}

	public int getPrePage() {
		if (isHasPre()) {
			return (this.pageNo - 1);
		}
		return this.pageNo;
	}

	public int getPageNo() {
		int max = this.totalCount / this.pageSize + 1;
		return ((this.pageNo > max) ? max : this.pageNo);
	}

	public int getCntPageNo() {
		return this.pageNo;
	}
}