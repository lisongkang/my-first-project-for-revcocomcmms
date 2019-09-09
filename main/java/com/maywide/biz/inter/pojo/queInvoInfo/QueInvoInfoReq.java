package com.maywide.biz.inter.pojo.queInvoInfo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueInvoInfoReq extends BaseApiRequest{

	private String invno;
	
	private String bookNo;
	
	private String macInfos;

	public String getInvno() {
		return invno;
	}

	public void setInvno(String invno) {
		this.invno = invno;
	}

	public String getBookNo() {
		return bookNo;
	}

	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}

	public String getMacInfos() {
		return macInfos;
	}

	public void setMacInfos(String macInfos) {
		this.macInfos = macInfos;
	}
	
	
	
}
