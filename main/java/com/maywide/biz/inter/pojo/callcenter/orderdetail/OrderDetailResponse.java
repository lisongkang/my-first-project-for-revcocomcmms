package com.maywide.biz.inter.pojo.callcenter.orderdetail;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("response")
public class OrderDetailResponse {
	 private String  retCode;
	 private String  retMsg ;
	 
	@XStreamAlias("response-content")
	private  OrderTasks response = new OrderTasks();



	public OrderTasks getResponse() {
		return response;
	}

	public void setResponse(OrderTasks response) {
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
