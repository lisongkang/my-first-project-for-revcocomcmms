package com.maywide.biz.inter.pojo.queryopermenu;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueryOperMenuInterReq extends BaseApiRequest implements java.io.Serializable {
	private static final long serialVersionUID = 669110113226679348L;
	private String operid;
	private String roleid;
	private Integer sysid;

	public String getOperid() {
		return operid;
	}

	public void setOperid(String operid) {
		this.operid = operid;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public Integer getSysid() {
		return sysid;
	}

	public void setSysid(Integer sysid) {
		this.sysid = sysid;
	}
}
