package com.maywide.biz.inter.pojo.callcenter.orderdetail;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueOrderReq extends BaseApiRequest {
	private OrderRequest orderreq = new OrderRequest();

	public OrderRequest getOrderreq() {
		return orderreq;
	}

	public void setOrderreq(OrderRequest orderreq) {
		this.orderreq = orderreq;
	}


	
	
}
