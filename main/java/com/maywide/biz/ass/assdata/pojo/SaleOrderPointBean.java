package com.maywide.biz.ass.assdata.pojo;

import java.util.Date;

public class SaleOrderPointBean {

	private Long orderId;
	
	private String sMode;
	
	private String opCode;
	
	private Date opTime;
	
	private Long operator;
	
	private String operType;
	
	private String city;
	
	private Long salesId;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getsMode() {
		return sMode;
	}

	public void setsMode(String sMode) {
		this.sMode = sMode;
	}

	public String getOpCode() {
		return opCode;
	}

	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}

	public Date getOpTime() {
		return opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public Long getSalesId() {
		return salesId;
	}

	public void setSalesId(Long salesId) {
		this.salesId = salesId;
	}
	
	
	
}
