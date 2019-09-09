package com.maywide.biz.inter.pojo.deleteorder;

import java.io.Serializable;

public class DeleteOrderBossResp implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3375091964767282464L;

	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
