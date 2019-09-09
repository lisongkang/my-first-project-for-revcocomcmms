package com.maywide.biz.inter.pojo.sycnChlInstall;

public class GetAccton2BossReq {
	
	public GetAccton2BossReq() {
		super();
	}

	public GetAccton2BossReq(String custid) {
		super();
		this.custid = custid;
	}

	private String custid;

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}
	
	
	
}
