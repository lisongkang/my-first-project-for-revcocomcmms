package com.maywide.biz.inter.pojo.oss.order;

import com.maywide.biz.inter.pojo.callcenter.orderdetail.OrderTasks;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("response")
public class OssOrderDetailResponse {
	 private String  retCode;
	 private String  retMsg ;
	 
	@XStreamAlias("response-content")
	private  OssOrderTasks response = new OssOrderTasks();



	public OssOrderTasks getResponse() {
		return response;
	}

	public void setResponse(OssOrderTasks response) {
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
