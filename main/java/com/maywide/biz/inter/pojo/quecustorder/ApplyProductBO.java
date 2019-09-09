package com.maywide.biz.inter.pojo.quecustorder;

public class ApplyProductBO implements java.io.Serializable {
	
	private String recid;//申请软信息记录主键
	private String ostatus;//动作:0-订购,3-退订
	private String ostatusname;//动动名称
	private String servid;//用户id
	private String logicdevno;//用户设备号
	private String knowid;//知识库营销id
	private String knowname;//知识库营销名称
	private String salespkgid;//营销方案id，退订的时候记录
	private String salespkgname;//营销方案名称
	private String pid;//产品id，退订的时候记录
	private String pname;//产品名称
	private String count;//周期
	private String unit;//订购单位
	private String unitname;//订购单位名称
	
	public String getOstatus() {
		return ostatus;
	}
	public void setOstatus(String ostatus) {
		this.ostatus = ostatus;
	}
	public String getServid() {
		return servid;
	}
	public void setServid(String servid) {
		this.servid = servid;
	}
	public String getLogicdevno() {
		return logicdevno;
	}
	public void setLogicdevno(String logicdevno) {
		this.logicdevno = logicdevno;
	}
	public String getKnowid() {
		return knowid;
	}
	public void setKnowid(String knowid) {
		this.knowid = knowid;
	}
	public String getSalespkgid() {
		return salespkgid;
	}
	public void setSalespkgid(String salespkgid) {
		this.salespkgid = salespkgid;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getOstatusname() {
		return ostatusname;
	}
	public void setOstatusname(String ostatusname) {
		this.ostatusname = ostatusname;
	}
	public String getKnowname() {
		return knowname;
	}
	public void setKnowname(String knowname) {
		this.knowname = knowname;
	}
	public String getSalespkgname() {
		return salespkgname;
	}
	public void setSalespkgname(String salespkgname) {
		this.salespkgname = salespkgname;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getUnitname() {
		return unitname;
	}
	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}
	public String getRecid() {
		return recid;
	}
	public void setRecid(String recid) {
		this.recid = recid;
	}

	
}
