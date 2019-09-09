package com.maywide.biz.inter.pojo.goods.queGoodsNodes;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueGoodsNodesReq extends BaseApiRequest{
	private String goodsCode;
	private String custid;
	private String city;

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	

}
