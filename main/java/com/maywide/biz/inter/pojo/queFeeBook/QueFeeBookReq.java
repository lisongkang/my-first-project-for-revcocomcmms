package com.maywide.biz.inter.pojo.queFeeBook;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueFeeBookReq extends BaseApiRequest{
	
	private String custid;
	
	private String permark;
	
	private String keyno;
	
	private String houseid;
	
	private String patchid;
	
	private String feecodestr;

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}

	public String getKeyno() {
		return keyno;
	}

	public void setKeyno(String keyno) {
		this.keyno = keyno;
	}

	public String getHouseid() {
		return houseid;
	}

	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}

	public String getPatchid() {
		return patchid;
	}

	public void setPatchid(String patchid) {
		this.patchid = patchid;
	}

	public String getFeecodestr() {
		return feecodestr;
	}

	public void setFeecodestr(String feecodestr) {
		this.feecodestr = feecodestr;
	}
	
	

}
