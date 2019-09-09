package com.maywide.biz.inter.pojo.bizchgoperatorpwd;

public class BizChgOperatorPwdBossReq implements java.io.Serializable {
	private String operid;// 操作员id
	private String oldpwd;// 旧密码
	private String newpwd;// 新密码
	
	public String getOperid() {
		return operid;
	}
	public void setOperid(String operid) {
		this.operid = operid;
	}
	public String getOldpwd() {
		return oldpwd;
	}
	public void setOldpwd(String oldpwd) {
		this.oldpwd = oldpwd;
	}
	public String getNewpwd() {
		return newpwd;
	}
	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd;
	}

}
