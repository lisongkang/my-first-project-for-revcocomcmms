package com.maywide.biz.inter.pojo.queCityChgFees;

import java.util.List;

public class QueCityChgFeesResp {
	
	private List<String> feeList;
	
	private String feesName;
	
	private String resource;

	public List<String> getFeeList() {
		return feeList;
	}

	public void setFeeList(List<String> feeList) {
		this.feeList = feeList;
	}

	public String getFeesName() {
		return feesName;
	}

	public void setFeesName(String feesName) {
		this.feesName = feesName;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
	
	
}
