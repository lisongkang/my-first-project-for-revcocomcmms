package com.maywide.biz.inter.pojo.bizstopsales;

public class StopSalesParam {

	private String servid; // 用户ID

	private String salesid; // 商品ID

	private String newstoptime; // 商品停用时间

	public String getServid() {
		return servid;
	}

	public void setServid(String servid) {
		this.servid = servid;
	}

	public String getSalesid() {
		return salesid;
	}

	public void setSalesid(String salesid) {
		this.salesid = salesid;
	}

	public String getNewstoptime() {
		return newstoptime;
	}

	public void setNewstoptime(String newstoptime) {
		this.newstoptime = newstoptime;
	}

}
