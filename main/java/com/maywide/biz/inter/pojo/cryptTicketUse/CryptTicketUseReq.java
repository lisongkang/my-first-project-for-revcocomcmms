package com.maywide.biz.inter.pojo.cryptTicketUse;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class CryptTicketUseReq extends BaseApiRequest {

	private String passwd;
	
	private String serialno;
	
	private String custid;
	
	private String custname;
	
	private String keyno;
	
	private String addr;

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public String getKeyno() {
		return keyno;
	}

	public void setKeyno(String keyno) {
		this.keyno = keyno;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	
	
}
