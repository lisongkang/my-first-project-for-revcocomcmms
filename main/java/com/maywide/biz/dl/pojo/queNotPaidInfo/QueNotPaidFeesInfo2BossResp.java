package com.maywide.biz.dl.pojo.queNotPaidInfo;

import java.util.List;

public class QueNotPaidFeesInfo2BossResp {

	private InvlistCustBean cust;
	
	private List<InvlistBean> invlist;

	public InvlistCustBean getCust() {
		return cust;
	}

	public void setCust(InvlistCustBean cust) {
		this.cust = cust;
	}

	public List<InvlistBean> getInvlist() {
		return invlist;
	}

	public void setInvlist(List<InvlistBean> invlist) {
		this.invlist = invlist;
	}
	
	
	
}
