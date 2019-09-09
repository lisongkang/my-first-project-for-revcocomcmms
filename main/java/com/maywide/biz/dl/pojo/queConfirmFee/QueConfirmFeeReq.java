package com.maywide.biz.dl.pojo.queConfirmFee;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueConfirmFeeReq extends BaseApiRequest {
	
	private String gridcode;

	private int pagesize;
	
	private int currentPage;

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

	public String getGridcode() {
		return gridcode;
	}

	public void setGridcode(String gridcode) {
		this.gridcode = gridcode;
	}
	
	
	
}
