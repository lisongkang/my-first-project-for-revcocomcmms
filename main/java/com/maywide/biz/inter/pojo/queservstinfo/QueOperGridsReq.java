package com.maywide.biz.inter.pojo.queservstinfo;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueOperGridsReq extends BaseApiRequest implements java.io.Serializable {
	private  LoginInfo loginInfo = new LoginInfo();

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}
	
}
