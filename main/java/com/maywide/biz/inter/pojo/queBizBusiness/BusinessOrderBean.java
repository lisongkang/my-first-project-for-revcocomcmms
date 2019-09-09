package com.maywide.biz.inter.pojo.queBizBusiness;

import java.util.List;

import com.maywide.biz.inter.pojo.bizBusinessOrder.EquipBean;

public class BusinessOrderBean {

	private String orderid;
	
	private String linkname;
	
	private String linkphone;
	
	private String linkaddr;
	
	private String erector;
	
	private String erectorname;
	
	private String erectordept;
	
	private String businesstype;
	
	private String prods;
	
	private String memo;
	
	private String optime;
	
	private String status;
	
	private String statusdesc;
	
	private Long operid;
	
	private String opername;
	
	private String operdept;
	
	private List<EquipBean> equiplists;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
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

	public String getErector() {
		return erector;
	}

	public void setErector(String erector) {
		this.erector = erector;
	}

	public String getErectorname() {
		return erectorname;
	}

	public void setErectorname(String erectorname) {
		this.erectorname = erectorname;
	}

	public String getErectordept() {
		return erectordept;
	}

	public void setErectordept(String erectordept) {
		this.erectordept = erectordept;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOptime() {
		return optime;
	}

	public void setOptime(String optime) {
		this.optime = optime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusdesc() {
		return statusdesc;
	}

	public void setStatusdesc(String statusdesc) {
		this.statusdesc = statusdesc;
	}

	public Long getOperid() {
		return operid;
	}

	public void setOperid(Long operid) {
		this.operid = operid;
	}

	public String getOpername() {
		return opername;
	}

	public void setOpername(String opername) {
		this.opername = opername;
	}

	public String getOperdept() {
		return operdept;
	}

	public void setOperdept(String operdept) {
		this.operdept = operdept;
	}

	public List<EquipBean> getEquiplists() {
		return equiplists;
	}

	public void setEquiplists(List<EquipBean> equiplists) {
		this.equiplists = equiplists;
	}
	
	
	
}
