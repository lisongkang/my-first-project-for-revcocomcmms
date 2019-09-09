package com.maywide.biz.inter.pojo.bizuserfocus;

import java.io.Serializable;

public class AddUserFocusReq implements Serializable {
	private static final long serialVersionUID = 4130420604522870632L;

	private Long operid;
	private Long custid;
	
	public Long getOperid() {
		return operid;
	}
	public void setOperid(Long operid) {
		this.operid = operid;
	}
	public Long getCustid() {
		return custid;
	}
	public void setCustid(Long custid) {
		this.custid = custid;
	}
}
