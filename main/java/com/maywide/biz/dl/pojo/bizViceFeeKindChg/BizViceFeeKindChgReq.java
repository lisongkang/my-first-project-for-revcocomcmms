package com.maywide.biz.dl.pojo.bizViceFeeKindChg;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizViceFeeKindChgReq extends BaseApiRequest{

	private String custid;
	
	private String custName;
	
	private String address;
	
	private String servids;
	
	private String newChargetype;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getServids() {
		return servids;
	}

	public void setServids(String servids) {
		this.servids = servids;
	}

	public String getNewChargetype() {
		return newChargetype;
	}

	public void setNewChargetype(String newChargetype) {
		this.newChargetype = newChargetype;
	}

	
	
	
}
