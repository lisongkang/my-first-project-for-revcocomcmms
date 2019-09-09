package com.maywide.biz.dl.pojo.queWoekDayReport;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueWorkDayReportReq extends BaseApiRequest {

	private String stime;
	
	private String etime;
	
	private String payways;
	
	private String rfeecode;
	
	private String permark;

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}


	public String getPayways() {
		return payways;
	}

	public void setPayways(String payways) {
		this.payways = payways;
	}

	public String getRfeecode() {
		return rfeecode;
	}

	public void setRfeecode(String rfeecode) {
		this.rfeecode = rfeecode;
	}

	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}
	
	
}
