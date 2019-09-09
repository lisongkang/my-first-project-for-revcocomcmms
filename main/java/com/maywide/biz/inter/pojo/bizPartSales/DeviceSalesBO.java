package com.maywide.biz.inter.pojo.bizPartSales;

public class DeviceSalesBO {

	private String kind;
	
	private String subkind;
	
	private String nums;
	
	private String place;
	
	private String useprop; //来源  默认传"0"
	
	private String fitattr;//配件属性,默认传""
	
	private String addtype;//增加方式,默认传"3"
	
	private String devoptye;//销售类型,默认传"0",正常销售
	
	private String pid;//产品编号
	
	private String voucherPrice;//金额
	
	private String usestatus;//默认传"2"
	
	private String uprop;//默认传“0”
	
	private String devno;//设备编号

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getSubkind() {
		return subkind;
	}

	public void setSubkind(String subkind) {
		this.subkind = subkind;
	}

	public String getNums() {
		return nums;
	}

	public void setNums(String nums) {
		this.nums = nums;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getUseprop() {
		return useprop;
	}

	public void setUseprop(String useprop) {
		this.useprop = useprop;
	}

	public String getFitattr() {
		return fitattr;
	}

	public void setFitattr(String fitattr) {
		this.fitattr = fitattr;
	}

	public String getAddtype() {
		return addtype;
	}

	public void setAddtype(String addtype) {
		this.addtype = addtype;
	}

	public String getDevoptye() {
		return devoptye;
	}

	public void setDevoptye(String devoptye) {
		this.devoptye = devoptye;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getVoucherPrice() {
		return voucherPrice;
	}

	public void setVoucherPrice(String voucherPrice) {
		this.voucherPrice = voucherPrice;
	}

	public String getUsestatus() {
		return usestatus;
	}

	public void setUsestatus(String usestatus) {
		this.usestatus = usestatus;
	}

	public String getUprop() {
		return uprop;
	}

	public void setUprop(String uprop) {
		this.uprop = uprop;
	}

	public String getDevno() {
		return devno;
	}

	public void setDevno(String devno) {
		this.devno = devno;
	}
	
	
	
}
