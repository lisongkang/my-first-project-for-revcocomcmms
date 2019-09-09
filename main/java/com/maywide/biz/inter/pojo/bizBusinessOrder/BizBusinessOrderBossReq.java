package com.maywide.biz.inter.pojo.bizBusinessOrder;

import java.util.List;

public class BizBusinessOrderBossReq {

	private String orderid;
	
	private String custid;
	
	private String linkname;
	
	private String linkphone;
	
	private String linkaddr;
	
	private Long erector;
	
	private String erectorname;
	
	private Long erectordept;
	
	private Long areaid;
	
	private String city;
	
	private String memo;
	
	private String businesstype;
	
	private String prods;
	
	private List<EquipBean> equiplists;
	
	private String authtype;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getLinkname() {
		return linkname;
	}

	public void setLinkname(String linkname) {
		this.linkname = linkname;
	}

	public String getLinkphone() {
		return linkphone;
	}

	public void setLinkphone(String linkphone) {
		this.linkphone = linkphone;
	}

	public String getLinkaddr() {
		return linkaddr;
	}

	public void setLinkaddr(String linkaddr) {
		this.linkaddr = linkaddr;
	}

	public Long getErector() {
		return erector;
	}

	public void setErector(Long erector) {
		this.erector = erector;
	}

	public String getErectorname() {
		return erectorname;
	}

	public void setErectorname(String erectorname) {
		this.erectorname = erectorname;
	}

	public Long getErectordept() {
		return erectordept;
	}

	public void setErectordept(Long erectordept) {
		this.erectordept = erectordept;
	}

	public Long getAreaid() {
		return areaid;
	}

	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getBusinesstype() {
		return businesstype;
	}

	public void setBusinesstype(String businesstype) {
		this.businesstype = businesstype;
	}

	public String getProds() {
		return prods;
	}

	public void setProds(String prods) {
		this.prods = prods;
	}

	public List<EquipBean> getEquiplists() {
		return equiplists;
	}

	public void setEquiplists(List<EquipBean> equiplists) {
		this.equiplists = equiplists;
	}

	public String getAuthtype() {
		return authtype;
	}

	public void setAuthtype(String authtype) {
		this.authtype = authtype;
	}
	
	
}
