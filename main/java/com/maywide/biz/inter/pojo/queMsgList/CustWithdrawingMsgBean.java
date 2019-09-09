package com.maywide.biz.inter.pojo.queMsgList;

import com.maywide.biz.inter.entity.CustWithdrawingMsg;

public class CustWithdrawingMsgBean extends CustWithdrawingMsg {

	private String ptimeMemo;

	public String getPtimeMemo() {
		return ptimeMemo;
	}

	public void setPtimeMemo(String ptimeMemo) {
		this.ptimeMemo = ptimeMemo;
	}

	public CustWithdrawingMsgBean() {
		super();
	}
	
	public CustWithdrawingMsgBean(CustWithdrawingMsg msg) {
		super();
		setFees(msg.getFees());
		setId(msg.getId());
		setMid(msg.getMid());
		setPtime(msg.getPtime());
	}
}
