package com.maywide.biz.inter.pojo.queMsgList;

public class MsgCountBean { 

	private int accountMsgCount;
	
	private int drawMsgCount;
	
	private NoticeMsg accountNotice;

	private NoticeMsg custDrawNotice;
	

	public NoticeMsg getAccountNotice() {
		return accountNotice;
	}

	public void setAccountNotice(NoticeMsg accountNotice) {
		this.accountNotice = accountNotice;
	}

	public NoticeMsg getCustDrawNotice() {
		return custDrawNotice;
	}

	public void setCustDrawNotice(NoticeMsg custDrawNotice) {
		this.custDrawNotice = custDrawNotice;
	}

	public int getAccountMsgCount() {
		return accountMsgCount;
	}

	public void setAccountMsgCount(int accountMsgCount) {
		this.accountMsgCount = accountMsgCount;
	}

	public int getDrawMsgCount() {
		return drawMsgCount;
	}

	public void setDrawMsgCount(int drawMsgCount) {
		this.drawMsgCount = drawMsgCount;
	}

	public MsgCountBean(int accountMsgCount, int drawMsgCount) {
		super();
		this.accountMsgCount = accountMsgCount;
		this.drawMsgCount = drawMsgCount;
	}

	public MsgCountBean() {
		super();
	}
	
	
	
}
