package com.maywide.biz.inter.pojo.queOrderNotice;

import java.util.List;

public class QueOrderNoticeResp {

	private String pCode;
	
	private String sums;
	
	private String pName;
	
	private List<String> prices;
	
	public List<String> getPrices() {
		return prices;
	}

	public void setPrices(List<String> prices) {
		this.prices = prices;
	}

	public String getpCode() {
		return pCode;
	}

	public void setpCode(String pCode) {
		this.pCode = pCode;
	}

	public String getSums() {
		return sums;
	}

	public void setSums(String sums) {
		this.sums = sums;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	
	
}
