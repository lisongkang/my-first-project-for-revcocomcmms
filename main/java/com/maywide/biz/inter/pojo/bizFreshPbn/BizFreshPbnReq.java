package com.maywide.biz.inter.pojo.bizFreshPbn;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizFreshPbnReq extends BaseApiRequest {

	private long custid;
	
	private String address;
	
	private String custname;
	
	private String servid;

	public String getServid() {
		return servid;
	}

	public void setServid(String servid) {
		this.servid = servid;
	}

	public long getCustid() {
		return custid;
	}

	public void setCustid(long custid) {
		this.custid = custid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}
	
	
	
}
