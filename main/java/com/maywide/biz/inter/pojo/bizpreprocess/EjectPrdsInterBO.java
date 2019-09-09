package com.maywide.biz.inter.pojo.bizpreprocess;

public class EjectPrdsInterBO implements java.io.Serializable {
	private String servid;//用户id
	private String pids;//退订产品id串,英文逗号分割
	private String cycle;//退订周期	不可同时指按周期退订和按截至时间退订
	private String ejectdate;//退订截至时间yyyy-mm-dd hh24:mi:ss,不可同时指按周期退订和按截至时间退订
	
	//不用转给boss的
	private String keyno;
	
	public String getServid() {
		return servid;
	}
	public void setServid(String servid) {
		this.servid = servid;
	}
	public String getPids() {
		return pids;
	}
	public void setPids(String pids) {
		this.pids = pids;
	}
	public String getCycle() {
		return cycle;
	}
	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	public String getEjectdate() {
		return ejectdate;
	}
	public void setEjectdate(String ejectdate) {
		this.ejectdate = ejectdate;
	}
	public String getKeyno() {
		return keyno;
	}
	public void setKeyno(String keyno) {
		this.keyno = keyno;
	}
	
}
