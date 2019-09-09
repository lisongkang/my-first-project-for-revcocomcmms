package com.maywide.biz.inter.pojo.deleteorder;

import java.io.Serializable;

public class DeleteOrderBossReq implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8289504766620573429L;

	private String orderid;
	
	private String queflag = "GRID";
	

	public String getQueflag() {
		return queflag;
	}

	public void setQueflag(String queflag) {
		this.queflag = queflag;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
}
