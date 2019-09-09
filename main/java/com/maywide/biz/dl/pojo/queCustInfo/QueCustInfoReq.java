package com.maywide.biz.dl.pojo.queCustInfo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueCustInfoReq extends BaseApiRequest {

	private String addrjd;
	
	private String addrlh;
	
	private int pagesize = 10;
	
	private int currentPage = 1;

	public String getAddrjd() {
		return addrjd;
	}

	public void setAddrjd(String addrjd) {
		this.addrjd = addrjd;
	}

	public String getAddrlh() {
		return addrlh;
	}

	public void setAddrlh(String addrlh) {
		this.addrlh = addrlh;
	}

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
	
}
