package com.maywide.biz.inter.pojo.queIntegrated;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueIntegratedReq extends BaseApiRequest {
	private String cardno;
	private String devno;

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getDevno() {
		return devno;
	}

	public void setDevno(String devno) {
		this.devno = devno;
	}

}
