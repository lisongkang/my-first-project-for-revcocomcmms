package com.maywide.biz.core.pojo.uapsocketinf;

import com.maywide.core.security.remote.socket.ServiceOrgRequest;

public class PyUapSocketReqBO {

	private String ip;
	
	private int port;
	
	private ServiceOrgRequest request;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public ServiceOrgRequest getRequest() {
		return request;
	}

	public void setRequest(ServiceOrgRequest request) {
		this.request = request;
	}
	
	
}
