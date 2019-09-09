package com.maywide.biz.inter.pojo.checkAcpt;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class CheckAcceptReq extends BaseApiRequest {

	private String custid;
	
	private String devno;

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getDevno() {
		return devno;
	}

	public void setDevno(String devno) {
		this.devno = devno;
	}
	
	
	
}
