package com.maywide.biz.dl.pojo.creatNotPaidFeesOrder;

import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;
import com.maywide.biz.dl.pojo.bizUpNotPaidFees.Bizfeeid;

public class CreatNotPaidFeesOrderReq extends BaseApiRequest{

	private Long custid;
	
	private String custName;
	
	private String address;
	
	private String fees;
	
	private List<Bizfeeid> bizfeeids;
	
	private String verifyphone;

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
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

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public String getVerifyphone() {
		return verifyphone;
	}

	public void setVerifyphone(String verifyphone) {
		this.verifyphone = verifyphone;
	}

	public List<Bizfeeid> getBizfeeids() {
		return bizfeeids;
	}

	public void setBizfeeids(List<Bizfeeid> bizfeeids) {
		this.bizfeeids = bizfeeids;
	}
	
	
}
