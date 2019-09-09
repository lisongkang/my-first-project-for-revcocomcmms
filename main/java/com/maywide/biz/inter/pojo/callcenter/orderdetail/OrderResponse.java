package com.maywide.biz.inter.pojo.callcenter.orderdetail;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("response")
public class OrderResponse {
	 private String  retCode;
	 private String  retMsg ;
	 
	@XStreamAlias("response-content")
	private  OrderDetail response = new OrderDetail();

	public OrderDetail getResponse() {
		return response;
	}

	public void setResponse(OrderDetail response) {
		this.response = response;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	
}
