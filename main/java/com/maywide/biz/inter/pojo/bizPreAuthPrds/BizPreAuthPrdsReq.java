package com.maywide.biz.inter.pojo.bizPreAuthPrds;

import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizPreAuthPrdsReq extends BaseApiRequest {

	private String permark;
	
	private String authsetid;
	
	private String regincode;
	
	private String stime;
	
	private String etime;
	
	private String memo;
	
	private String custid;
	
	private String custname;
	
	private String address;
	
	private String gatewayAuth;
	
	private List<PreauthKeynos> keynoList;

	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}

	public String getAuthsetid() {
		return authsetid;
	}

	public void setAuthsetid(String authsetid) {
		this.authsetid = authsetid;
	}

	public String getRegincode() {
		return regincode;
	}

	public void setRegincode(String regincode) {
		this.regincode = regincode;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public List<PreauthKeynos> getKeynoList() {
		return keynoList;
	}

	public void setKeynoList(List<PreauthKeynos> keynoList) {
		this.keynoList = keynoList;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGatewayAuth() {
		return gatewayAuth;
	}

	public void setGatewayAuth(String gatewayAuth) {
		this.gatewayAuth = gatewayAuth;
	}
	
	
	
}
