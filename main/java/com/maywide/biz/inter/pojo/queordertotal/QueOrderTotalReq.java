package com.maywide.biz.inter.pojo.queordertotal;

import java.io.Serializable;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

@SuppressWarnings("serial")
public class QueOrderTotalReq extends BaseApiRequest implements Serializable {

	private String deptid;

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

}
