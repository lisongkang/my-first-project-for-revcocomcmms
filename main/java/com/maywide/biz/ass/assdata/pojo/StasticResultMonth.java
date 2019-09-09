package com.maywide.biz.ass.assdata.pojo;

import java.io.Serializable;

public class StasticResultMonth implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5060907150479320327L;
	

	private String tmonth;
	
	private String city;
	
	private Long assid;
	
	private Long patchid;
	
	private String extpermark;
	
	private Double income;

	public String getTmonth() {
		return tmonth;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getAssid() {
		return assid;
	}

	public void setAssid(Long assid) {
		this.assid = assid;
	}

	public void setTmonth(String tmonth) {
		this.tmonth = tmonth;
	}

	public Long getPatchid() {
		return patchid;
	}

	public void setPatchid(Long patchid) {
		this.patchid = patchid;
	}

	public String getExtpermark() {
		return extpermark;
	}

	public void setExtpermark(String extpermark) {
		this.extpermark = extpermark;
	}

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}
	
}
