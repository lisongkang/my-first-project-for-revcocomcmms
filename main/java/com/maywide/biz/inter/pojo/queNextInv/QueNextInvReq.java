package com.maywide.biz.inter.pojo.queNextInv;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueNextInvReq extends BaseApiRequest{

	private String operid;
	
	private String deptid;
	
	private String areaid;
	
	private String industrys;

	public String getOperid() {
		return operid;
	}

	public void setOperid(String operid) {
		this.operid = operid;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getIndustrys() {
		return industrys;
	}

	public void setIndustrys(String industrys) {
		this.industrys = industrys;
	}
	
	
	
}
