package com.maywide.biz.inter.pojo.savebustrackinfo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class SaveBustrackinfoInterBustrackReq extends BaseApiRequest {
	private long busid;
	private String busstatus;
	private String tdate;
	private String trackmemo;
	public long getBusid() {
		return busid;
	}
	public void setBusid(long busid) {
		this.busid = busid;
	}
	public String getBusstatus() {
		return busstatus;
	}
	public void setBusstatus(String busstatus) {
		this.busstatus = busstatus;
	}
	public String getTdate() {
		return tdate;
	}
	public void setTdate(String tdate) {
		this.tdate = tdate;
	}
	public String getTrackmemo() {
		return trackmemo;
	}
	public void setTrackmemo(String trackmemo) {
		this.trackmemo = trackmemo;
	}
}
