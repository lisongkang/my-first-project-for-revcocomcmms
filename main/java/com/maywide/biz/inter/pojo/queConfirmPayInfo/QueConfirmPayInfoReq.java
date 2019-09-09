package com.maywide.biz.inter.pojo.queConfirmPayInfo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueConfirmPayInfoReq extends BaseApiRequest {

	private String custid;
	
	private String markno;
	
	private String smartcardno;
	
	private String sdate;
	
	private String edate;

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getMarkno() {
		return markno;
	}

	public void setMarkno(String markno) {
		this.markno = markno;
	}

	public String getSmartcardno() {
		return smartcardno;
	}

	public void setSmartcardno(String smartcardno) {
		this.smartcardno = smartcardno;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}
	
	
}
