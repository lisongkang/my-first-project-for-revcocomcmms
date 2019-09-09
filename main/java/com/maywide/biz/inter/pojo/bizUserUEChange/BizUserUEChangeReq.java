package com.maywide.biz.inter.pojo.bizUserUEChange;

import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizUserUEChangeReq extends BaseApiRequest {

	private String custid;
	
	private List<SvdBean> svdList;
	
	private String custname;
	
	private String address;

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public List<SvdBean> getSvdList() {
		return svdList;
	}

	public void setSvdList(List<SvdBean> svdList) {
		this.svdList = svdList;
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
	
	
}
