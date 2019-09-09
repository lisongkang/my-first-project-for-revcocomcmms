package com.maywide.biz.inter.pojo.quetasklist;

public class QueTaskListByCustidReq {

	private int pageSize;

	private int index = -1;

	private String status = "0";

	private String custid;
	
	private String custAreaid;

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

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getCustAreaid() {
		return custAreaid;
	}

	public void setCustAreaid(String custAreaid) {
		this.custAreaid = custAreaid;
	}
	
	

}
