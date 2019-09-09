package com.maywide.biz.inter.pojo.bizResDev;

import com.maywide.biz.inter.pojo.IPrintCondition;

public class BizChlRestartDevResp implements IPrintCondition{

	private String orderId;
	
	private String printShow;
	
	private String bossserialno;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	public String getPrintShow() {
		return printShow;
	}

	@Override
	public void setPrintShow(String printShow) {
		this.printShow = printShow;
	}

	@Override
	public String getBossserialno() {
		return bossserialno;
	}

	@Override
	public void setBossserialno(String bossserialno) {
		this.bossserialno = bossserialno;
	}
	
	

}
