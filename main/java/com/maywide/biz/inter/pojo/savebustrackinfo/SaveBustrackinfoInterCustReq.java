package com.maywide.biz.inter.pojo.savebustrackinfo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class SaveBustrackinfoInterCustReq extends BaseApiRequest {
	private long id;
	private String phone;
	private String mobile;
	private String markno;
	private String name;
	private String cardtype;
	private String cardno;
	private String subtype;
	private String secsubtype;
	private String linkman;
	private String linkaddr;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMarkno() {
		return markno;
	}
	public void setMarkno(String markno) {
		this.markno = markno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
	public String getSecsubtype() {
		return secsubtype;
	}
	public void setSecsubtype(String secsubtype) {
		this.secsubtype = secsubtype;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getLinkaddr() {
		return linkaddr;
	}
	public void setLinkaddr(String linkaddr) {
		this.linkaddr = linkaddr;
	}
}
