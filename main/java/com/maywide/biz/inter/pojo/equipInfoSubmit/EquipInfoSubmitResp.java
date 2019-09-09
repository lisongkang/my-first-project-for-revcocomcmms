package com.maywide.biz.inter.pojo.equipInfoSubmit;

import java.io.Serializable;

public class EquipInfoSubmitResp implements Serializable{

	private String isPayed;
	
	private String custorderid;

	public String getIsPayed() {
		return isPayed;
	}

	public void setIsPayed(String isPayed) {
		this.isPayed = isPayed;
	}

	public String getCustorderid() {
		return custorderid;
	}

	public void setCustorderid(String custorderid) {
		this.custorderid = custorderid;
	}
	
	
	
}
