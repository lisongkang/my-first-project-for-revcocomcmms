package com.maywide.biz.inter.pojo.bizResDev;

import java.util.List;

public class BizChlRestart2BossReq {

	private String custid;
	
	private List<BizChlRestartParams> servstList;
	
	private String houseid;
	
	private String restartTime;
	
	private String installtype;

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public List<BizChlRestartParams> getServstList() {
		return servstList;
	}

	public void setServstList(List<BizChlRestartParams> servstList) {
		this.servstList = servstList;
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

	public String getInstalltype() {
		return installtype;
	}

	public void setInstalltype(String installtype) {
		this.installtype = installtype;
	}
	
	
}
