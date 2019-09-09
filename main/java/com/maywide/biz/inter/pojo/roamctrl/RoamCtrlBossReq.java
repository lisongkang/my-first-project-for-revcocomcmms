package com.maywide.biz.inter.pojo.roamctrl;

import java.io.Serializable;

public class RoamCtrlBossReq  implements Serializable {
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
