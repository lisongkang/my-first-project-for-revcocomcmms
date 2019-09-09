package com.maywide.biz.inter.pojo.bizuserfocus;

import java.io.Serializable;

public class QueryUserFocusReq implements Serializable {
	private static final long serialVersionUID = 4130420604522870632L;

	private Long operid;
	
	public Long getOperid() {
		return operid;
	}
	public void setOperid(Long operid) {
		this.operid = operid;
	}
}
