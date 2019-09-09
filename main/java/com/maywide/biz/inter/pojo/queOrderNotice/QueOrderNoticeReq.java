package com.maywide.biz.inter.pojo.queOrderNotice;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueOrderNoticeReq extends BaseApiRequest{

	private String opcode;
	
	private String customerId;
	
	private String houseid;
	
	public String getOpcode() {
		return opcode;
	}

	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getHouseid() {
		return houseid;
	}

	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}

}
