package com.maywide.biz.inter.pojo.bizbusback;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizBusRollReq extends BaseApiRequest{

	private String orderid;
	
	private String flag;
	
	private String memo;
	
	private String opcode;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOpcode() {
		return opcode;
	}

	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}
	
	
}
