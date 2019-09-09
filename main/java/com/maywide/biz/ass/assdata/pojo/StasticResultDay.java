package com.maywide.biz.ass.assdata.pojo;

import java.io.Serializable;

public class StasticResultDay implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5060907150479320327L;
	

	private String tjdate;
	
	private Long assid;
	
	private Long patchid;
	
	private Double newservAmount;

	public Long getAssid() {
		return assid;
	}

	public void setAssid(Long assid) {
		this.assid = assid;
	}

	public Long getPatchid() {
		return patchid;
	}

	public void setPatchid(Long patchid) {
		this.patchid = patchid;
	}

	public String getTjdate() {
		return tjdate;
	}

	public void setTjdate(String tjdate) {
		this.tjdate = tjdate;
	}

	public Double getNewservAmount() {
		return newservAmount;
	}

	public void setNewservAmount(Double newservAmount) {
		this.newservAmount = newservAmount;
	}

}
