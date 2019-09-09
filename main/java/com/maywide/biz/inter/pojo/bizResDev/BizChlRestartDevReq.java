package com.maywide.biz.inter.pojo.bizResDev;

import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizChlRestartDevReq extends BaseApiRequest{

	private String custid;
	
	private String houseid;
	
	private String restartTime;
	
	private String custName;
	
	private String address;
	
	private List<BizChlRestartParams> params;

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getHouseid() {
		return houseid;
	}

	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}

	public String getRestartTime() {
		return restartTime;
	}

	public void setRestartTime(String restartTime) {
		this.restartTime = restartTime;
	}

	public List<BizChlRestartParams> getParams() {
		return params;
	}

	public void setParams(List<BizChlRestartParams> params) {
		this.params = params;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
