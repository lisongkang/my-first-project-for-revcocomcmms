package com.maywide.biz.inter.pojo.queBizBusiness;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueBizBusinessReq extends BaseApiRequest {

	private String custid;
	
	private String status;
	
	private String stime;
	
	private String etime;
	
	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

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

	
}
