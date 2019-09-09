package com.maywide.biz.inter.pojo.bizSendsms;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizSendMsgReq extends BaseApiRequest{

	private String phoneNum;
	
	private Long custid;
	
	private String msgContent;
	
	private Long tid;

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}
	
	
	
}
