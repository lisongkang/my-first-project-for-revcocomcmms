package com.maywide.biz.inter.pojo.queuserpkg;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueUserPkgInterReq extends BaseApiRequest implements
		java.io.Serializable {
	private String custid;//客户id
	private String servid;//用户id
	
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getServid() {
		return servid;
	}
	public void setServid(String servid) {
		this.servid = servid;
	}
	
	
}
