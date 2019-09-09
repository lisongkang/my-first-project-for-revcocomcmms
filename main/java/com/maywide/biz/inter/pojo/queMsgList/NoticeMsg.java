package com.maywide.biz.inter.pojo.queMsgList;

import java.util.Date;

public class NoticeMsg {

	private String mtype;
	
	private String custname;
	
	private Date updateTime;
	
	private String msgContent;
	
	private int msgcount;

	public String getMtype() {
		return mtype;
	}

	public void setMtype(String mtype) {
		this.mtype = mtype;
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public int getMsgcount() {
		return msgcount;
	}

	public void setMsgcount(int msgcount) {
		this.msgcount = msgcount;
	}
	
	
	
}
