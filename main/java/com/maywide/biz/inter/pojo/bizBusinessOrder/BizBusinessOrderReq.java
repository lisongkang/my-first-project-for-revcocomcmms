package com.maywide.biz.inter.pojo.bizBusinessOrder;

import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizBusinessOrderReq extends BaseApiRequest {
	
	private String custid;
	
	private String linkname;
	
	private String linkphone;
	
	private String linkaddr;
	
	private String memo;
	
	private String businesstype;
	
	private String prods;
	
	private List<EquipBean> equiplists;
	
	private String orderid;
	
	private String authtype;

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

	public List<EquipBean> getEquiplists() {
		return equiplists;
	}

	public void setEquiplists(List<EquipBean> equiplists) {
		this.equiplists = equiplists;
	}

	public String getProds() {
		return prods;
	}

	public void setProds(String prods) {
		this.prods = prods;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getAuthtype() {
		return authtype;
	}

	public void setAuthtype(String authtype) {
		this.authtype = authtype;
	}
	
	

}

