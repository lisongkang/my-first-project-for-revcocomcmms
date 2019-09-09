package com.maywide.biz.core.pojo.uapsocketinf;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.maywide.core.security.remote.socket.ServiceRequest;

public class UapSocketReqBO implements Serializable {
	
    private String ip;
	private int port;
	private ServiceRequest serviceReq;
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
    public ServiceRequest getServiceReq() {
        return serviceReq;
    }
    public void setServiceReq(ServiceRequest serviceReq) {
        this.serviceReq = serviceReq;
    }

	
}
