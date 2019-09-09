package com.maywide.biz.core.pojo.bosshttpinf;

import java.io.Serializable;

public class BossHttpBaseRespBO implements Serializable {
	//用于解析boss http接口返回共公信息

	private String requestid = null;
	private String status = null;
	private String message = null;
	private BossHttpBaseOutputBO output = new BossHttpBaseOutputBO();
	public String getRequestid() {
		return requestid;
	}
	public void setRequestid(String requestid) {
		this.requestid = requestid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public BossHttpBaseOutputBO getOutput() {
		return output;
	}
	public void setOutput(BossHttpBaseOutputBO output) {
		this.output = output;
	}

}
