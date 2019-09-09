package com.maywide.core.security.remote.socket;

public class ServiceOrgRequest {

	private String service;
	
	private Object input;
	
	private String clientid;

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public Object getInput() {
		return input;
	}

	public void setInput(Object input) {
		this.input = input;
	}

	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	
	

}
