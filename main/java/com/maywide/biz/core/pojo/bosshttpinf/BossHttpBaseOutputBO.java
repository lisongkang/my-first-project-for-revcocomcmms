package com.maywide.biz.core.pojo.bosshttpinf;

import java.io.Serializable;

public class BossHttpBaseOutputBO implements Serializable {
	// 用于解析boss http接口返回的ouput字段中需要的信息
	private Long orderid = null;
	private String serialno = null;

	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

}
