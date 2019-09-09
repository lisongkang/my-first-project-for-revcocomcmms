package com.maywide.biz.inter.pojo.quetasklist;

public class QueTaskListReq {

	private String gridCode;

	private String activityid;

	private int pageSize;

	private int index = -1;
	
	private String status = "-1";
	
	private String address;

	public String getGridCode() {
		return gridCode;
	}

	public void setGridCode(String gridCode) {
		this.gridCode = gridCode;
	}

	public String getActivityid() {
		return activityid;
	}

	public void setActivityid(String activityid) {
		this.activityid = activityid;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
}
