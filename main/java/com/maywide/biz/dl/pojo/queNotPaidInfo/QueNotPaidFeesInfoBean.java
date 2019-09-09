package com.maywide.biz.dl.pojo.queNotPaidInfo;

import java.util.List;

public class QueNotPaidFeesInfoBean {
	
	private String bookno;
	
	private String invno;
	
	private double total;
	
	private List<InvlistBean> invlist;

	public String getBookno() {
		return bookno;
	}

	public void setBookno(String bookno) {
		this.bookno = bookno;
	}

	public String getInvno() {
		return invno;
	}

	public void setInvno(String invno) {
		this.invno = invno;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public List<InvlistBean> getInvlist() {
		return invlist;
	}

	public void setInvlist(List<InvlistBean> invlist) {
		this.invlist = invlist;
	}
	
	
	
}
