package com.maywide.biz.inter.pojo.queCartOrder;

import java.util.List;

public class CartOrderTmpBean {
	
	private String ordercode;
	
	private String orderid;
	
	private String logicdevno;
	
	private String addr;
	
	private String custname;
	
	private String custorderid;
	
	private double fees;
	
	private String time;
	
	private String paystatusname;
	
	private String optime;
	
	private List<CartOrderProductResp> products;

	public String getLogicdevno() {
		return logicdevno;
	}

	public void setLogicdevno(String logicdevno) {
		this.logicdevno = logicdevno;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public String getCustorderid() {
		return custorderid;
	}

	public void setCustorderid(String custorderid) {
		this.custorderid = custorderid;
	}

	public double getFees() {
		return fees;
	}

	public void setFees(double fees) {
		this.fees = fees;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPaystatusname() {
		return paystatusname;
	}

	public void setPaystatusname(String paystatusname) {
		this.paystatusname = paystatusname;
	}

	public String getOptime() {
		return optime;
	}

	public void setOptime(String optime) {
		this.optime = optime;
	}

	public List<CartOrderProductResp> getProducts() {
		return products;
	}

	public void setProducts(List<CartOrderProductResp> products) {
		this.products = products;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getOrdercode() {
		return ordercode;
	}

	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}
	
	

}
