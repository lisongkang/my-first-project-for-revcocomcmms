package com.maywide.core.security.remote.socket;

public class ServiceRequest {
	private String clientid;
	private String service; 
	private Object input; 
//	private String city;
	private String clientpwd;
	
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

	public String getClientpwd() {
		return clientpwd;
	}

	public void setClientpwd(String clientpwd) {
		this.clientpwd = clientpwd;
	}

//	public String getCity() {
//		return city;
//	}
//
//	public void setCity(String city) {
//		this.city = city;
//	}
	
	
	
}
