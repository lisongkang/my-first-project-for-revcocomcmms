package com.maywide.biz.inter.pojo.savebustrackinfo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class SaveBustrackinfoInterReq extends BaseApiRequest {
	private SaveBustrackinfoInterBustrackReq bustrack;
	private SaveBustrackinfoInterCustReq cust;
	public SaveBustrackinfoInterBustrackReq getBustrack() {
		return bustrack;
	}
	public void setBustrack(SaveBustrackinfoInterBustrackReq bustrack) {
		this.bustrack = bustrack;
	}
	public SaveBustrackinfoInterCustReq getCust() {
		return cust;
	}
	public void setCust(SaveBustrackinfoInterCustReq cust) {
		this.cust = cust;
	}
}
