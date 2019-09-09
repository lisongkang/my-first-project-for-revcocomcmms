package com.maywide.biz.inter.pojo.quePrintInvo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class PrintInvoInfoReq extends BaseApiRequest{

	private String invno;
	
	private String bookno;
	
	private String invcontids;
	
	private String mac;
	
	private String payway;
	
	private String posSerialno;

	public String getInvno() {
		return invno;
	}

	public void setInvno(String invno) {
		this.invno = invno;
	}

	public String getBookno() {
		return bookno;
	}

	public void setBookno(String bookno) {
		this.bookno = bookno;
	}

	public String getInvcontids() {
		return invcontids;
	}

	public void setInvcontids(String invcontids) {
		this.invcontids = invcontids;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getPayway() {
		return payway;
	}

	public void setPayway(String payway) {
		this.payway = payway;
	}

	public String getPosSerialno() {
		return posSerialno;
	}

	public void setPosSerialno(String posSerialno) {
		this.posSerialno = posSerialno;
	}

}
