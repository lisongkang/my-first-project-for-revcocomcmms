package com.maywide.biz.inter.pojo.handlerSupplementGather;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class HandlerSupplementGatherReq {

	private Long bizOrderId;
	
	private String fees;
	
	private String payway;
	
	private String paycode;

	public Long getBizOrderId() {
		return bizOrderId;
	}

	public void setBizOrderId(Long bizOrderId) {
		this.bizOrderId = bizOrderId;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public String getPayway() {
		return payway;
	}

	public void setPayway(String payway) {
		this.payway = payway;
	}

	public String getPaycode() {
		return paycode;
	}

	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}
	

	
}
