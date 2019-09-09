package com.maywide.biz.inter.pojo.login;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class LoginInterReq extends BaseApiRequest implements java.io.Serializable {
	private String loginname;
	private String password;
	private String cryptPwd;
	private String deptid; 
	private String atype;
	private String verApp;
	private String pkgName; 
	private String devStr;
	private boolean implicit; //是否隐式登录
	
	
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

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getCryptPwd() {
		return cryptPwd;
	}

	public void setCryptPwd(String cryptPwd) {
		this.cryptPwd = cryptPwd;
	}

	public boolean isImplicit() {
		return implicit;
	}

	public void setImplicit(boolean implicit) {
		this.implicit = implicit;
	}

	public String getAtype() {
		return atype;
	}

	public void setAtype(String atype) {
		this.atype = atype;
	}

	public String getVerApp() {
		return verApp;
	}

	public void setVerApp(String verApp) {
		this.verApp = verApp;
	}

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public String getDevStr() {
		return devStr;
	}

	public void setDevStr(String devStr) {
		this.devStr = devStr;
	}
	
	

}
