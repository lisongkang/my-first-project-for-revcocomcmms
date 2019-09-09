package com.maywide.biz.inter.pojo.addscreenshot;

public class AddScreenshotReq {

	private String orderid;
	private String screenshots;
	private boolean append;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getScreenshots() {
		return screenshots;
	}

	public void setScreenshots(String screenshots) {
		this.screenshots = screenshots;
	}

	public boolean isAppend() {
		return append;
	}

	public void setAppend(boolean append) {
		this.append = append;
	}

}
