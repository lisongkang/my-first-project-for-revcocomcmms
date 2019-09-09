package com.maywide.biz.inter.pojo.quesalesordercontent;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueSalesorderContentInterReq extends BaseApiRequest {
	private String orderid;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
}