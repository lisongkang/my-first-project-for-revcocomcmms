package com.maywide.biz.inter.pojo.queCustAttr;

import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class FillCustAttrReq extends BaseApiRequest{

	private String custid;
	
	private String operid;
	
	private List<Attrudie> attrs;

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getOperid() {
		return operid;
	}

	public void setOperid(String operid) {
		this.operid = operid;
	}

	public List<Attrudie> getAttrs() {
		return attrs;
	}

	public void setAttrs(List<Attrudie> attrs) {
		this.attrs = attrs;
	}
	
	
	
}
