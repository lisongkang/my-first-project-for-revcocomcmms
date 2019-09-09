package com.maywide.biz.inter.pojo.bizupdatecmpwd;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizUpdateCmPwdReq extends BaseApiRequest {

	private static final long serialVersionUID = 1L;

	private String servid;
	private String newPasswd;

	public String getServid() {
		return servid;
	}

	public void setServid(String servid) {
		this.servid = servid;
	}

	public String getNewPasswd() {
		return newPasswd;
	}

	public void setNewPasswd(String newPasswd) {
		this.newPasswd = newPasswd;
	}

}
