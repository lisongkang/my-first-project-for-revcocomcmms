package com.maywide.biz.inter.pojo.printAccInfo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizPrintAccInfoReq extends BaseApiRequest{

	private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	

}
