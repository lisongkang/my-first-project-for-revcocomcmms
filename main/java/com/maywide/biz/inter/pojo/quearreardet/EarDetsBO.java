package com.maywide.biz.inter.pojo.quearreardet;

import java.util.Date;

public class EarDetsBO implements java.io.Serializable {

	private String servid;
	private String permark;
	private String pcode;
	private String pname;
	private String stime;
	private String etime;
	private String fees;
	private String logicdevno;
	private double wofees;
	private double unfees;
	
	private String salescode;
	
	private String salesname;
	
	

	public String getSalescode() {
		return salescode;
	}

	public void setSalescode(String salescode) {
		this.salescode = salescode;
	}

	public String getSalesname() {
		return salesname;
	}

	public void setSalesname(String salesname) {
		this.salesname = salesname;
	}

	public double getWofees() {
		return wofees;
	}

	public void setWofees(double wofees) {
		this.wofees = wofees;
	}

	public double getUnfees() {
		return unfees;
	}

	public void setUnfees(double unfees) {
		this.unfees = unfees;
	}

	public String getLogicdevno() {
		return logicdevno;
	}

	public void setLogicdevno(String logicdevno) {
		this.logicdevno = logicdevno;
	}

	public String getServid() {
		return servid;
	}

	public void setServid(String servid) {
		this.servid = servid;
	}

	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

}
