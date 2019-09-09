package com.maywide.biz.inter.pojo.cryptTicketUse;

import com.maywide.biz.inter.pojo.cryptTicket.BossCryptTicketReq;

public class BossCryptTicketUseReq extends BossCryptTicketReq {

	private String serialno;
	
	private String custid;
	
	private String custname;
	
	private String keyno;

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
	
	
	
}
