package com.maywide.core.socket;


public class ServiceResponse {
	private String status; 
	//private String code; 
	private String message; 
	private Object output; 

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

//	public String getCode() {
//		return code;
//	}
//
//	public void setCode(String code) {
//		this.code = code;
//	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getOutput() {
		return output;
	}

	public void setOutput(Object output) {
		this.output = output;
	}
}
