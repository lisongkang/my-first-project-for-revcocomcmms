package com.maywide.biz.inter.pojo.roamctrl;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class RoamCtrlInterReq  extends BaseApiRequest {
	private String roamstatus;
	private String loginname;
	public String getRoamstatus() {
		return roamstatus;
	}
	public void setRoamstatus(String roamstatus) {
		this.roamstatus = roamstatus;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
}
