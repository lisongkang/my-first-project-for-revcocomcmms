package com.maywide.core.socket;

public class ServiceRequest {
	private String clientid;
	private String service; 
	private Object input; 
	private String city;
	
	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}

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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
}
