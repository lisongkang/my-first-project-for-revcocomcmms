package com.maywide.biz.dl.pojo.queConfirmFee;

import java.util.List;

public class QueConfirmFeeResp {

	private List<CustFeeBean> custfeelist;

	public List<CustFeeBean> getCustfeelist() {
		return custfeelist;
	}

	public void setCustfeelist(List<CustFeeBean> custfeelist) {
		this.custfeelist = custfeelist;
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
