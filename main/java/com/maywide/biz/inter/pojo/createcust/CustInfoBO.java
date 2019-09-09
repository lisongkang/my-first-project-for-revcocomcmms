package com.maywide.biz.inter.pojo.createcust;

import java.util.LinkedHashSet;
import java.util.Set;

public class CustInfoBO implements java.io.Serializable {
	
	private Long id;
	private String name;
	private String custtype;
	private String subtype;
	private String custstatus;
	private String servkind;
	private String markno;
	private String linkaddr;
	private String zip;
	private String linkman;
	private String cardtype;
	private String cardno;
	private String phone;
	private String mobile;
	private String faxno;
	private String email;
	private String credit;
	private String service;
	private java.util.Date intime;
	private java.util.Date optime;
	private String memo;
	private Long areaid;
	private String secsubtype;// 客户二级子类别
	private String city;
	
	/** 公众客户信息 */
	private CustPubInfoInterInfo pubinfo;
	
	private Set<CustAttrInterInfo> attrs = new LinkedHashSet<CustAttrInterInfo>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCusttype() {
		return custtype;
	}
	public void setCusttype(String custtype) {
		this.custtype = custtype;
	}
	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
	public String getCuststatus() {
		return custstatus;
	}
	public void setCuststatus(String custstatus) {
		this.custstatus = custstatus;
	}
	public String getServkind() {
		return servkind;
	}
	public void setServkind(String servkind) {
		this.servkind = servkind;
	}
	public String getMarkno() {
		return markno;
	}
	public void setMarkno(String markno) {
		this.markno = markno;
	}
	public String getLinkaddr() {
		return linkaddr;
	}
	public void setLinkaddr(String linkaddr) {
		this.linkaddr = linkaddr;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
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
	public String getFaxno() {
		return faxno;
	}
	public void setFaxno(String faxno) {
		this.faxno = faxno;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public java.util.Date getIntime() {
		return intime;
	}
	public void setIntime(java.util.Date intime) {
		this.intime = intime;
	}
	public java.util.Date getOptime() {
		return optime;
	}
	public void setOptime(java.util.Date optime) {
		this.optime = optime;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Long getAreaid() {
		return areaid;
	}
	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}
	public String getSecsubtype() {
		return secsubtype;
	}
	public void setSecsubtype(String secsubtype) {
		this.secsubtype = secsubtype;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public CustPubInfoInterInfo getPubinfo() {
		return pubinfo;
	}
	public void setPubinfo(CustPubInfoInterInfo pubinfo) {
		this.pubinfo = pubinfo;
	}
	public Set<CustAttrInterInfo> getAttrs() {
		return attrs;
	}
	public void setAttrs(Set<CustAttrInterInfo> attrs) {
		this.attrs = attrs;
	}
	
	
}
