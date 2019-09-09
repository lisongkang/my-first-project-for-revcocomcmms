package com.maywide.biz.inter.pojo.queProductByChannel;

public class ChannelProductRespBean {

	private String knowid;
	
	private String knowname;
	
	private String salesid;
	
	private boolean hadBuy;

	public String getKnowid() {
		return knowid;
	}

	public void setKnowid(String knowid) {
		this.knowid = knowid;
	}

	public String getKnowname() {
		return knowname;
	}

	public void setKnowname(String knowname) {
		this.knowname = knowname;
	}

	public String getSalesid() {
		return salesid;
	}

	public void setSalesid(String salesid) {
		this.salesid = salesid;
	}

	public boolean isHadBuy() {
		return hadBuy;
	}

	public void setHadBuy(boolean hadBuy) {
		this.hadBuy = hadBuy;
	}

	public ChannelProductRespBean() {
		super();
	}

	public ChannelProductRespBean(String knowname, boolean hadBuy) {
		super();
		this.knowname = knowname;
		this.hadBuy = hadBuy;
	}
	
	
	
	
	
}
