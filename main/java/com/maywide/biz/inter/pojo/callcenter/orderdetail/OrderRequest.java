package com.maywide.biz.inter.pojo.callcenter.orderdetail;

import com.maywide.biz.core.pojo.api.BaseApiRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("request")
public class OrderRequest {

	String action	;
	String username   ;
	String passwd     ;
	 
	@XStreamAlias("request-content")
	private  QueOrder queorder = new QueOrder();

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public QueOrder getQueorder() {
		return queorder;
	}

	public void setQueorder(QueOrder queorder) {
		this.queorder = queorder;
	}




	
}
