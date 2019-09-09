package com.maywide.biz.inter.pojo.queAsyncOrder;

public class AsyncOrderBean {

	private Long orderid;
	
	private String ordercode;
	
	private String name;
	
	private String orderstatus;
	
	private String failmemo;
	
	private String optime;
	
	private Long custid;
	
	private String dealstatus;
	
	private String dealtype;
	
	private String dealdesc;

	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	public String getOrdercode() {
		return ordercode;
	}

	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}

	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getFailmemo() {
		return failmemo;
	}

	public void setFailmemo(String failmemo) {
		this.failmemo = failmemo;
	}

	public String getOptime() {
		return optime;
	}

	public void setOptime(String optime) {
		this.optime = optime;
	}

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	public String getDealstatus() {
		return dealstatus;
	}

	public void setDealstatus(String dealstatus) {
		this.dealstatus = dealstatus;
	}

	public String getDealtype() {
		return dealtype;
	}

	public void setDealtype(String dealtype) {
		this.dealtype = dealtype;
	}

	public String getDealdesc() {
		return dealdesc;
	}

	public void setDealdesc(String dealdesc) {
		this.dealdesc = dealdesc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
