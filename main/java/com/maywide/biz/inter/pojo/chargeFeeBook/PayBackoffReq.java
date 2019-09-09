package com.maywide.biz.inter.pojo.chargeFeeBook;

public class PayBackoffReq {

	private String requestid;
	
	private String orderNo;
	
	private String memo;
	
	private String noticeAction;

	public String getRequestid() {
		return requestid;
	}

	public void setRequestid(String requestid) {
		this.requestid = requestid;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getNoticeAction() {
		return noticeAction;
	}

	public void setNoticeAction(String noticeAction) {
		this.noticeAction = noticeAction;
	}
	
	
	
}
