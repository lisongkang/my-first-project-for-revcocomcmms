package com.maywide.biz.dl.pojo.queUserCount;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueUserCountReq extends BaseApiRequest {

	private int pagesize    = 1;
	
	private int currentPage = 10;
	
	private String permark;
	
	private String custtype;
	
	private String netids;

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getNetids() {
		return netids;
	}

	public void setNetids(String netids) {
		this.netids = netids;
	}

	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}

	public String getCusttype() {
		return custtype;
	}

	public void setCusttype(String custtype) {
		this.custtype = custtype;
	}
	
	
	
}
