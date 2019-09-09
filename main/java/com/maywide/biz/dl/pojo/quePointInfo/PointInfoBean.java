package com.maywide.biz.dl.pojo.quePointInfo;

public class PointInfoBean {

	private String netid;
	
	private String date;

	public String getNetid() {
		return netid;
	}

	public void setNetid(String netid) {
		this.netid = netid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public PointInfoBean(String netid) {
		super();
		this.netid = netid;
	}

	public PointInfoBean() {
		super();
	}
	
	
	
}
