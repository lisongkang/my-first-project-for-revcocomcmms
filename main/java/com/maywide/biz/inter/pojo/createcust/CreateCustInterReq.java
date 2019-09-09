package com.maywide.biz.inter.pojo.createcust;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class CreateCustInterReq extends BaseApiRequest implements java.io.Serializable {
	
	public CreateCustInterReq() {
		super();
	}
	public CreateCustInterReq(String areaid, String name, String cardtype,
			String cardno, String phone, String mobile) {
		super();
		this.areaid = areaid;
		this.name = name;
		this.cardtype = cardtype;
		this.cardno = cardno;
		this.phone = phone;
		this.mobile = mobile;
	}
	private String areaid;
	private String name;
	private String cardtype;
	private String cardno;
	private String phone;
	private String mobile;
	private String linkaddr;
	private String custstatus;
	
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
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
	public String getLinkaddr() {
		return linkaddr;
	}
	public void setLinkaddr(String linkaddr) {
		this.linkaddr = linkaddr;
	}
	public String getCuststatus() {
		return custstatus;
	}
	public void setCuststatus(String custstatus) {
		this.custstatus = custstatus;
	}
	
	

}