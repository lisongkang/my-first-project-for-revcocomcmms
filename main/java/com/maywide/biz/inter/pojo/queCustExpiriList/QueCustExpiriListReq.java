package com.maywide.biz.inter.pojo.queCustExpiriList;

public class QueCustExpiriListReq {

	private int currentpager;
	
	private int pagercount;
	
	private String gridcodes;
	
	private String tagid;

	public String getGridcodes() {
		return gridcodes;
	}

	public void setGridcodes(String gridcodes) {
		this.gridcodes = gridcodes;
	}

	public int getCurrentpager() {
		return currentpager;
	}

	public void setCurrentpager(int currentpager) {
		this.currentpager = currentpager;
	}

	public int getPagercount() {
		return pagercount;
	}

	public void setPagercount(int pagercount) {
		this.pagercount = pagercount;
	}

	public String getTagid() {
		return tagid;
	}

	public void setTagid(String tagid) {
		this.tagid = tagid;
	}

	
}
