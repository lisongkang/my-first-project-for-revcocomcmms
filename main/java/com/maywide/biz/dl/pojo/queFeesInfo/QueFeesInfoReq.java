package com.maywide.biz.dl.pojo.queFeesInfo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueFeesInfoReq extends BaseApiRequest {

	private String sday;
	
	private String eday;
	
	private String paywaps;

	public String getSday() {
		return sday;
	}

	public void setSday(String sday) {
		this.sday = sday;
	}

	public String getEday() {
		return eday;
	}

	public void setEday(String eday) {
		this.eday = eday;
	}

	public String getPaywaps() {
		return paywaps;
	}

	public void setPaywaps(String paywaps) {
		this.paywaps = paywaps;
	}
	
	
	
}
