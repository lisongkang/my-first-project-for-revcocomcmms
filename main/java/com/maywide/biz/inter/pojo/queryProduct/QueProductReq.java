package com.maywide.biz.inter.pojo.queryProduct;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueProductReq extends BaseApiRequest{

	private String permark;
	
	private Long catalogId;
	
	private String objType;
	
	private String custid;
	

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

	public Long getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(Long catalogId) {
		this.catalogId = catalogId;
	}

	public String getObjType() {
		return objType;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}
	
	
	
}
