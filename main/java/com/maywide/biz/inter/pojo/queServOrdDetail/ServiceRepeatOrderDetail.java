package com.maywide.biz.inter.pojo.queServOrdDetail;

public class ServiceRepeatOrderDetail extends ServiceBaseOrderDetailBean {

	private String custName;
	
	private String address;
	
	private String trackStaffId;

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTrackStaffId() {
		return trackStaffId;
	}

	public void setTrackStaffId(String trackStaffId) {
		this.trackStaffId = trackStaffId;
	}
	
	
	
}
