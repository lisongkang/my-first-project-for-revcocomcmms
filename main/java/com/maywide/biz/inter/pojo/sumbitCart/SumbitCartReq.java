package com.maywide.biz.inter.pojo.sumbitCart;

import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class SumbitCartReq extends BaseApiRequest{

	private String verifyphone;//接收验证码的手机号
	
	private List<SumbitCartOrderParam> params;

	public String getVerifyphone() {
		return verifyphone;
	}

	public void setVerifyphone(String verifyphone) {
		this.verifyphone = verifyphone;
	}
	
	public List<SumbitCartOrderParam> getParams() {
		return params;
	}

	public void setParams(List<SumbitCartOrderParam> params) {
		this.params = params;
	}
	
	
}
