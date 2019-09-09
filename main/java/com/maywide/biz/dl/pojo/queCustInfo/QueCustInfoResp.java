package com.maywide.biz.dl.pojo.queCustInfo;

import java.util.List;

public class QueCustInfoResp {

	private List<CustServBean> custserv;

	public List<CustServBean> getCustserv() {
		return custserv;
	}

	public void setCustserv(List<CustServBean> custserv) {
		this.custserv = custserv;
	}
	
	private int totalcount;
	
	private int pagesize;
	
	private int cntpageno;

	public int getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getCntpageno() {
		return cntpageno;
	}

	public void setCntpageno(int cntpageno) {
		this.cntpageno = cntpageno;
	}
	
	
	
}
