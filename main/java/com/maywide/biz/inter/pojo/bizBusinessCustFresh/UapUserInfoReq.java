package com.maywide.biz.inter.pojo.bizBusinessCustFresh;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class UapUserInfoReq {

	private String type;
	
	private String devNo;
	
	private String city;
	
	private Long area;
	
	private String permark;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDevNo() {
		return devNo;
	}

	public void setDevNo(String devNo) {
		this.devNo = devNo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getArea() {
		return area;
	}

	public void setArea(Long area) {
		this.area = area;
	}

	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}
	
	
	
}
