package com.maywide.biz.inter.pojo.querydepartment;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueryDepartmentInterReq extends BaseApiRequest implements java.io.Serializable {
	private String loginname;
	private Long areaid;

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public Long getAreaid() {
		return areaid;
	}

	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}

}
