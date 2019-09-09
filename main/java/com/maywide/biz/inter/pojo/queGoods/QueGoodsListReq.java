package com.maywide.biz.inter.pojo.queGoods;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueGoodsListReq extends BaseApiRequest{

	private String catalogId;
	
	private String custid;

	private String salespkgcode;

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getSalespkgcode() {
		return salespkgcode;
	}

	public void setSalespkgcode(String salespkgcode) {
		this.salespkgcode = salespkgcode;
	}
	
	
	
}
