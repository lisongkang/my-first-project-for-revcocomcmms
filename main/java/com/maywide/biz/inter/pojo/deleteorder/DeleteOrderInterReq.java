package com.maywide.biz.inter.pojo.deleteorder;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class DeleteOrderInterReq extends BaseApiRequest {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5542669523483726043L;
	
	private String orderid;
	
	private String orderstatus;
	
	private String syncmode;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getSyncmode() {
		return syncmode;
	}

	public void setSyncmode(String syncmode) {
		this.syncmode = syncmode;
	}

}
