package com.maywide.biz.inter.pojo.callcenter.orderdetail;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("request-content")  
public class QueOrder {
	String custid			;
	String category   ;
	String status     ;
	private String  effDate    ;
	private String  expDate    ;
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEffDate() {
		return effDate;
	}
	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	
}
