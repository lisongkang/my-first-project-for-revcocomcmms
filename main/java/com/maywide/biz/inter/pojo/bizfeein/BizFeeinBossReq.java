package com.maywide.biz.inter.pojo.bizfeein;

import java.io.Serializable;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizFeeinBossReq implements Serializable {

	private String fees;
	private String keyno;
	private String permark;
	private String isorder;
	private String gdnoid;
	private String gdno;

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public String getKeyno() {
		return keyno;
	}

	public void setKeyno(String keyno) {
		this.keyno = keyno;
	}

	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}

	public String getIsorder() {
		return isorder;
	}

	public void setIsorder(String isorder) {
		this.isorder = isorder;
	}

	public String getGdnoid() {
		return gdnoid;
	}

	public void setGdnoid(String gdnoid) {
		this.gdnoid = gdnoid;
	}

	public String getGdno() {
		return gdno;
	}

	public void setGdno(String gdno) {
		this.gdno = gdno;
	}

}
