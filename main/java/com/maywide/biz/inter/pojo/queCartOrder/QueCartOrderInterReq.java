package com.maywide.biz.inter.pojo.queCartOrder;

import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueCartOrderInterReq extends BaseApiRequest{

	private String custid;
	
	private String custname;
	
	private List<CartOrderInterReq> orderIds;

	public List<CartOrderInterReq> getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(List<CartOrderInterReq> orderIds) {
		this.orderIds = orderIds;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}
	
	
	
}
