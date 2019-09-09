package com.maywide.biz.inter.pojo.pay;

import java.io.Serializable;

public class PayPackageReq implements Serializable {
	private String productname;
	private String orderid;
	private Integer fee;
	
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public Integer getFee() {
		return fee;
	}
	public void setFee(Integer fee) {
		this.fee = fee;
	}
}
