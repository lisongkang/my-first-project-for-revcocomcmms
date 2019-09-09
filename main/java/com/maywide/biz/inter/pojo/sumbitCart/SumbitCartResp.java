package com.maywide.biz.inter.pojo.sumbitCart;

import java.util.List;

import com.maywide.biz.inter.pojo.bizpreprocess.BizPreprocessInterResp;

public class SumbitCartResp {
	
	private List<BizPreprocessInterResp> orderids;

	public List<BizPreprocessInterResp> getOrderids() {
		return orderids;
	}

	public void setOrderids(List<BizPreprocessInterResp> orderids) {
		this.orderids = orderids;
	}
	
	private String notice;

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}
	
	

}
