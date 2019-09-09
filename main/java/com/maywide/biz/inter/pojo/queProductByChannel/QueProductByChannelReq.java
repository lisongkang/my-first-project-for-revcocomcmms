package com.maywide.biz.inter.pojo.queProductByChannel;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueProductByChannelReq extends BaseApiRequest {

	private String channelname;
	
	private String custid;

	public String getChannelname() {
		return channelname;
	}

	public void setChannelname(String channelname) {
		this.channelname = channelname;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}
	
	
}
