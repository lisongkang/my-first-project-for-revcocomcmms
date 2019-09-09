package com.maywide.biz.inter.pojo.queServOrdDetail;

import java.util.Date;

public class ServiceOutTimeOrderDetail extends ServiceBaseOrderDetailBean {

	private Date archTime;
	
	private Date limitTime;
	
	private Character flag;

	public Date getArchTime() {
		return archTime;
	}

	public void setArchTime(Date archTime) {
		this.archTime = archTime;
	}

	public Date getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(Date limitTime) {
		this.limitTime = limitTime;
	}

	public Character getFlag() {
		return flag;
	}

	public void setFlag(Character flag) {
		this.flag = flag;
	}

	
	
}
