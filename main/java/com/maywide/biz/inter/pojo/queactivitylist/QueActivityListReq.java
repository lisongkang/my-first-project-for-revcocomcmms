package com.maywide.biz.inter.pojo.queactivitylist;

public class QueActivityListReq {

	private String gridCode;

	private int pageSize;

	private int index = -1;
	
	private String status = "-1";
	
	private boolean issum; //是否合计

	public String getGridCode() {
		return gridCode;
	}

	public void setGridCode(String gridCode) {
		this.gridCode = gridCode;
	}

	public int getPageSize() {
		return pageSize <= 0 ? 15 : pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean getIssum() {
		return issum;
	}

	public void setIssum(boolean issum) {
		this.issum = issum;
	}

}
