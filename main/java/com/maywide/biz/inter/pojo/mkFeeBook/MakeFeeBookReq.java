package com.maywide.biz.inter.pojo.mkFeeBook;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class MakeFeeBookReq extends BaseApiRequest{

	private String houseid;
	
	private String patchid;
	
	private String addr;
	
	private String custid;
	
	private String custName;
	
	private String fee;
	
	private String keyno;

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

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getKeyno() {
		return keyno;
	}

	public void setKeyno(String keyno) {
		this.keyno = keyno;
	}
	
	
	
}
