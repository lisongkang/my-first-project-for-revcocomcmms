package com.maywide.biz.inter.pojo.queSurveylist;

import java.util.Date;

public class SurveyBean {
	
	private Long sId;
	
	private String sName;
	
	private Date inTime;
	
	private String isReal;
	
	private Long status;
	
	private Long total = 0l;

	public Long getsId() {
		return sId;
	}

	public void setsId(Long sId) {
		this.sId = sId;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public Date getInTime() {
		return inTime;
	}

	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}

	public String getIsReal() {
		return isReal;
	}

	public void setIsReal(String isReal) {
		this.isReal = isReal;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
	
	
	
}
