package com.maywide.biz.inter.pojo.scancodeandsign;

import com.maywide.biz.inter.pojo.queservstinfo.CustInfosBO;

public class ScanCodeAndSignResp {

	private String date;

	private String msg;

	private String smno;

	private CustInfosBO customer;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSmno() {
		return smno;
	}

	public void setSmno(String smno) {
		this.smno = smno;
	}

	public CustInfosBO getCustomer() {
		return customer;
	}

	public void setCustomer(CustInfosBO customer) {
		this.customer = customer;
	}

}
