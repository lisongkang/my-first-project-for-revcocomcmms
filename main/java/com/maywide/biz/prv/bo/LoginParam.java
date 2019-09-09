package com.maywide.biz.prv.bo;

public class LoginParam {
	private String loginname;
	private String password;
	private Long deptid;
	private Boolean isPwdCrypt;//密码是否已加密
	
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getDeptid() {
		return deptid;
	}
	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}
	public Boolean getIsPwdCrypt() {
		return isPwdCrypt;
	}
	public void setIsPwdCrypted(Boolean isPwdCrypt) {
		this.isPwdCrypt = isPwdCrypt;
	}

	
}
