package com.maywide.biz.inter.pojo.queuserprd;

public class MixProdBO {

	private String spid;// 子产品id
	private String spname;// 子产品名称
	private String permark;// 子产品业务类型
	private String devno;// 关联设备

	public String getSpid() {
		return spid;
	}

	public void setSpid(String spid) {
		this.spid = spid;
	}

	public String getSpname() {
		return spname;
	}

	public void setSpname(String spname) {
		this.spname = spname;
	}

	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}

	public String getDevno() {
		return devno;
	}

	public void setDevno(String devno) {
		this.devno = devno;
	}
}