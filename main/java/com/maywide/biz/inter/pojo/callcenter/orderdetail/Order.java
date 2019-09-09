package com.maywide.biz.inter.pojo.callcenter.orderdetail;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("order")  
public class Order {
	String  orderid         ;                              
	String  category        ;
	String  subtype         ;  
	String  orderDate       ;
	String  status          ; 
	String  custId          ;
	String  standby1        ;
	String  standby2        ;
	String  standby3        ; 
	String  limitDate       ; 
	String  alertDate       ;
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getStandby1() {
		return standby1;
	}
	public void setStandby1(String standby1) {
		this.standby1 = standby1;
	}
	public String getStandby2() {
		return standby2;
	}
	public void setStandby2(String standby2) {
		this.standby2 = standby2;
	}
	public String getStandby3() {
		return standby3;
	}
	public void setStandby3(String standby3) {
		this.standby3 = standby3;
	}
	public String getLimitDate() {
		return limitDate;
	}
	public void setLimitDate(String limitDate) {
		this.limitDate = limitDate;
	}
	public String getAlertDate() {
		return alertDate;
	}
	public void setAlertDate(String alertDate) {
		this.alertDate = alertDate;
	}

}
