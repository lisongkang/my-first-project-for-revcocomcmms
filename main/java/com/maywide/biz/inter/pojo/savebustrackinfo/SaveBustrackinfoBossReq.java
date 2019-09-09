package com.maywide.biz.inter.pojo.savebustrackinfo;

import java.io.Serializable;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class SaveBustrackinfoBossReq implements Serializable {
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
