package com.maywide.biz.channel.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@JsonIgnoreProperties(value = {"flag", "price", "prodarrears", "salespkgname"})
public class Pcode implements Serializable {
	private String pcode;
	private String pname;
	private String permark;
	private String stime;
	private String etime;
	private String prodstatus;
	private String ispostpone;
	private String prodfees;
	private String flag;
	private String price;
	private String prodarrears;
	private String salespkgname;
	private String pcodeStr;
	private String salescode;
	private String salesname;
	private String isbase;
	private String unit;
	private String fees;
	
	
	
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getIsbase() {
		return isbase;
	}
	public void setIsbase(String isbase) {
		this.isbase = isbase;
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
	public String getPermark() {
		return permark;
	}
	public void setPermark(String permark) {
		this.permark = permark;
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
	public String getProdstatus() {
		return prodstatus;
	}
	public void setProdstatus(String prodstatus) {
		this.prodstatus = prodstatus;
	}
	public String getIspostpone() {
		return ispostpone;
	}
	public void setIspostpone(String ispostpone) {
		this.ispostpone = ispostpone;
	}
	public String getProdfees() {
		return prodfees;
	}
	public void setProdfees(String prodfees) {
		this.prodfees = prodfees;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getProdarrears() {
		return prodarrears;
	}
	public void setProdarrears(String prodarrears) {
		this.prodarrears = prodarrears;
	}
	public String getSalespkgname() {
		return salespkgname;
	}
	public void setSalespkgname(String salespkgname) {
		this.salespkgname = salespkgname;
	}
	public String getPcodeStr() {
		return pcodeStr;
	}
	public void setPcodeStr(String pcodeStr) {
		this.pcodeStr = pcodeStr;
	}
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
	public String getFees() {
		return fees;
	}
	public void setFees(String fees) {
		this.fees = fees;
	}
	
	
}
