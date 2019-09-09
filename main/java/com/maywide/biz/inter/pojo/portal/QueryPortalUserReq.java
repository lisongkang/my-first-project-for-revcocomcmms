package com.maywide.biz.inter.pojo.portal;

public class QueryPortalUserReq {
	private String clientid; //版本号 默认 4
	private String userid;
	
	public String getClientid() {
		return clientid;
	}
	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
}
