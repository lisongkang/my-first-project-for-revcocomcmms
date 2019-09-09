package com.maywide.biz.inter.pojo.portal;

import com.maywide.biz.core.pojo.ReturnInfo;

/**
 * 
 *<p> 
 *portal返回数据信息
 *<p>
 * Create at 2016-11-2
 *
 *@autor zhuangzhitang
 */
public class PortalResp {
    private ReturnInfo returnInfo;
    private String responseBody;
   
	public PortalResp() {
		super();
	}

	public PortalResp(ReturnInfo returnInfo, String responseBody) {
		super();
		this.returnInfo = returnInfo;
		this.responseBody = responseBody;
	}
	
	public ReturnInfo getReturnInfo() {
		return returnInfo;
	}
	public void setReturnInfo(ReturnInfo returnInfo) {
		this.returnInfo = returnInfo;
	}
	public String getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

   
   
}
