package com.maywide.biz.inter.pojo.quebustrackinfo;

import java.io.Serializable;

public class QueBustrackinfoInterResp implements Serializable {
	private QueBustrackinfoBossBusinfoResp businfo;
	private QueBustrackinfoBossCustResp cust;
	public QueBustrackinfoBossBusinfoResp getBusinfo() {
		return businfo;
	}
	public void setBusinfo(QueBustrackinfoBossBusinfoResp businfo) {
		this.businfo = businfo;
	}
	public QueBustrackinfoBossCustResp getCust() {
		return cust;
	}
	public void setCust(QueBustrackinfoBossCustResp cust) {
		this.cust = cust;
	}
}
