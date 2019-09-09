package com.maywide.core.nio.msg;

public class CommonMsg {
	private String clientId;
	private String type;
	private String cmdtype;
	private String content;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCmdtype() {
		return cmdtype;
	}
	public void setCmdtype(String cmdtype) {
		this.cmdtype = cmdtype;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
