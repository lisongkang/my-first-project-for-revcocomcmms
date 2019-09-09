package com.maywide.core.security.remote;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "prv_remote_call")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class RemoteCall implements Serializable {
	private static final long serialVersionUID = -8865085940901062668L;

    private String interfaceName = null;
    private String remoteIP = null;
    private Integer remotePort = null;
    private String protocol = null;
    private String protocolParam = null;
    private String callUrl = null;
    private String remark = null;
    
    @Id	
	@Column(name="interface_name")
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getRemoteIP() {
		return remoteIP;
	}
	public void setRemoteIP(String remoteIP) {
		this.remoteIP = remoteIP;
	}
	public Integer getRemotePort() {
		return remotePort;
	}
	public void setRemotePort(Integer remotePort) {
		this.remotePort = remotePort;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getProtocolParam() {
		return protocolParam;
	}
	public void setProtocolParam(String protocolParam) {
		this.protocolParam = protocolParam;
	}
	public String getCallUrl() {
		return callUrl;
	}
	public void setCallUrl(String callUrl) {
		this.callUrl = callUrl;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
