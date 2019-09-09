package com.maywide.biz.inter.pojo.cryptTicket;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class CryptTicketReq extends BaseApiRequest {
	
	private String passwd;

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	

}
