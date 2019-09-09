package com.maywide.biz.inter.pojo.callcenter.orderdetail;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueOrderDetailReq extends BaseApiRequest {
	private String orderid ;
	private String standby	;
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getStandby() {
		return standby;
	}
	public void setStandby(String standby) {
		this.standby = standby;
	}

	
	

	
	
}
