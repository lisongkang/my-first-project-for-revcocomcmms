package com.maywide.biz.inter.pojo.chgportalpwd;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class ChgPortalPwdReq extends BaseApiRequest implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String userid;
	private String newpwd;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getNewpwd() {
		return newpwd;
	}

	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd;
	}

}
