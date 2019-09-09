package com.maywide.biz.inter.pojo.bizAnalogchg;

import java.util.List;

import com.maywide.biz.inter.pojo.install.OnceFeeParam;

public class SubsidaryMachine {
	
	private String servid;
	
	private String cardno;
	
	private String counts;
	
	private String knowids;
	
	private String percombs;
	
	private String units;
	
	private List<OnceFeeParam> feeParams;

	public String getServid() {
		return servid;
	}

	public void setServid(String servid) {
		this.servid = servid;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getCounts() {
		return counts;
	}

	public void setCounts(String counts) {
		this.counts = counts;
	}

	public String getKnowids() {
		return knowids;
	}

	public void setKnowids(String knowids) {
		this.knowids = knowids;
	}

	public String getPercombs() {
		return percombs;
	}

	public void setPercombs(String percombs) {
		this.percombs = percombs;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public List<OnceFeeParam> getFeeParams() {
		return feeParams;
	}

	public void setFeeParams(List<OnceFeeParam> feeParams) {
		this.feeParams = feeParams;
	}
	
	

}
