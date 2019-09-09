package com.maywide.biz.dl.pojo.bizUpCardRel;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizUpCardRelationReq extends BaseApiRequest {

	private String custName;
	
	private String custid;
	
	private String servid;
	
	private String pservid;
	
	private String chouseid;
	
	private String newservtype;
	
	private String address;

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

	public String getChouseid() {
		return chouseid;
	}

	public void setChouseid(String chouseid) {
		this.chouseid = chouseid;
	}

	public String getNewservtype() {
		return newservtype;
	}

	public void setNewservtype(String newservtype) {
		this.newservtype = newservtype;
	}

	public String getPservid() {
		return pservid;
	}

	public void setPservid(String pservid) {
		this.pservid = pservid;
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
