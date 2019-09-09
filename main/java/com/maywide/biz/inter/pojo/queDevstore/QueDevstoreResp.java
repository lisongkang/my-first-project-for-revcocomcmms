package com.maywide.biz.inter.pojo.queDevstore;

import java.util.List;

public class QueDevstoreResp {

	private int cntPageNo;
	
	private boolean hasNext;
	
	private boolean hasPre;
	
	private int pageSize;
	
	private int totalCount;
	
	private int totalPages;
	
	private List<ResDevice> result;

	public int getCntPageNo() {
		return cntPageNo;
	}

	public void setCntPageNo(int cntPageNo) {
		this.cntPageNo = cntPageNo;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public boolean isHasPre() {
		return hasPre;
	}

	public void setHasPre(boolean hasPre) {
		this.hasPre = hasPre;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public List<ResDevice> getResult() {
		return result;
	}

	public void setResult(List<ResDevice> result) {
		this.result = result;
	}
	
	
}
