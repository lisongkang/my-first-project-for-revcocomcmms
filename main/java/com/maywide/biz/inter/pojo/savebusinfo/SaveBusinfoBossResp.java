package com.maywide.biz.inter.pojo.savebusinfo;

import java.io.Serializable;

public class SaveBusinfoBossResp implements Serializable {
	private String message;
	private String output;
	private String requestid;
	private String status;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
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
}
