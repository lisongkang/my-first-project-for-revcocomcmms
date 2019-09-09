package com.maywide.biz.inter.pojo.queCmccAcctno;

import java.util.List;

public class QueAcctnoBossResp {

	private long nextPage;
	
	private int pageNo;
	
	private int pageSize;
	
	private long prePage;
	
	private List<AcctnoResultBean> result;
	
	private long totalCount;
	
	private long totalPages;

	public long getNextPage() {
		return nextPage;
	}

	public void setNextPage(long nextPage) {
		this.nextPage = nextPage;
	}

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

	public long getPrePage() {
		return prePage;
	}

	public void setPrePage(long prePage) {
		this.prePage = prePage;
	}

	public List<AcctnoResultBean> getResult() {
		return result;
	}

	public void setResult(List<AcctnoResultBean> result) {
		this.result = result;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}
	
	
}
