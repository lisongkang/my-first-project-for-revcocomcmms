package com.maywide.biz.core.pojo.api;

import java.io.Serializable;

public class BaseApiRequest implements Serializable {
	/*用于保存接口层传到业务层的公共请求参数，后续可扩充*/
	private String api;
	private String requestBody; // 请求串
	private String bizorderid; // 业务订单号,相当于业务流水号

	public String getBizorderid() {
		return bizorderid;
	}

	public void setBizorderid(String bizorderid) {
		this.bizorderid = bizorderid;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

}
