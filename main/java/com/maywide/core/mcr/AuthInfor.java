package com.maywide.core.mcr;


import java.util.Date;

import com.maywide.biz.core.pojo.LoginInfo;

public class AuthInfor {
	
	private int verify = 0;//验证类型 0:从微信客户端进来的，1:从第三方浏览器进来的 默认:0
	
	//如果是从微信客户端进来 这三个参数（code、state、publishid）必定有的
	private String code; 
	
	private String state;

	private String access_token;
	
	private String expires_in;
	
	private String refresh_token;
	
	private String openid;
	
	private String publishid;
	
	private String scope;
	
	private String errcode;
	
	private String errmsg;
	
	private Date crdate;
	
	private LoginInfo loginInfo;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

//	public CustinfoBO getCustinfo() {
//		return custinfo;
//	}
//
//	public void setCustinfo(CustinfoBO custinfo) {
//		this.custinfo = custinfo;
//	}

	public String getPublishid() {
		return publishid;
	}

	public void setPublishid(String publishid) {
		this.publishid = publishid;
	}

	public int getVerify() {
		return verify;
	}

	public void setVerify(int verify) {
		this.verify = verify;
	}

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	public Date getCrdate() {
		return crdate;
	}

	public void setCrdate(Date crdate) {
		this.crdate = crdate;
	}
	
	
}
