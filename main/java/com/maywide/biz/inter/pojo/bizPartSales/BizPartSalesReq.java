package com.maywide.biz.inter.pojo.bizPartSales;

public class BizPartSalesReq extends BizPartSalesBossReq {
	
	private String address;
	
	private String custName;
	
	private String screenshots;
	
	private String verifyphone;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getScreenshots() {
		return screenshots;
	}

	public void setScreenshots(String screenshots) {
		this.screenshots = screenshots;
	}

	public String getVerifyphone() {
		return verifyphone;
	}

	public void setVerifyphone(String verifyphone) {
		this.verifyphone = verifyphone;
	}
	
}
