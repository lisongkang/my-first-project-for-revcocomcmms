package com.maywide.biz.dl.pojo.queCustInfo;

public class QueCustInfoBossReq {

	private String netids;
	
	private String addrjd;
	
	private String addrlh;
	
	private int pagesize    = 10;
	
	private int currentPage = 1;


	public String getNetids() {
		return netids;
	}

	public void setNetids(String netids) {
		this.netids = netids;
	}

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
