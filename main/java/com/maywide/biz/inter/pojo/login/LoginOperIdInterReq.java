package com.maywide.biz.inter.pojo.login;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class LoginOperIdInterReq extends BaseApiRequest{

	private String loginName;
	
	private String departmentId;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
	
	

}
