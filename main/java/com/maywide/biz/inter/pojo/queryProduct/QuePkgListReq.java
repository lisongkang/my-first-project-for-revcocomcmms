package com.maywide.biz.inter.pojo.queryProduct;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QuePkgListReq extends BaseApiRequest {

	private String custid;
	
	private String catalogid;
	
	private String bizcode = "BIZ_PRD_ORDER";

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getCatalogid() {
		return catalogid;
	}

	public void setCatalogid(String catalogid) {
		this.catalogid = catalogid;
	}

	public String getBizcode() {
		return bizcode;
	}

	public void setBizcode(String bizcode) {
		this.bizcode = bizcode;
	}
	
	
	
}
