package com.maywide.biz.inter.pojo.queSyncPercomb;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueSyncPercombReq extends BaseApiRequest{

	private String opmode;
	
	private String custid;
	
	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getOpmode() {
		return opmode;
	}

	public void setOpmode(String opmode) {
		this.opmode = opmode;
	}
	
	
	
}
