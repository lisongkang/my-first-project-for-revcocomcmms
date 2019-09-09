package com.maywide.biz.inter.pojo.queServOrdDetail;

import java.util.Date;

public class ServiceBaseOrderDetailBean {

	private String staTime;
	
	private String gridCode;
	
	private String gridName;
	
	private String orderCode;
	
	private String isHitch;
	
	private Date sendTime;
	
	private String custId;
	
	private Date finisTime;
	
	private String orderStatus;

	public String getStaTime() {
		return staTime;
	}

	public void setStaTime(String staTime) {
		this.staTime = staTime;
	}

	public String getGridCode() {
		return gridCode;
	}

	public void setGridCode(String gridCode) {
		this.gridCode = gridCode;
	}

	public String getGridName() {
		return gridName;
	}

	public void setGridName(String gridName) {
		this.gridName = gridName;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getIsHitch() {
		return isHitch;
	}

	public void setIsHitch(String isHitch) {
		this.isHitch = isHitch;
	}


	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}


	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getFinisTime() {
		return finisTime;
	}

	public void setFinisTime(Date finisTime) {
		this.finisTime = finisTime;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	
	
	
}
