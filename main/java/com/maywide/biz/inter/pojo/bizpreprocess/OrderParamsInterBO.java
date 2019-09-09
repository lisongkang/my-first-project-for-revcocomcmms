package com.maywide.biz.inter.pojo.bizpreprocess;

import java.util.List;

public class OrderParamsInterBO implements java.io.Serializable {
	private String servid;//用户id
	private String keyno;//用户设备号
	private String knowid;//营销id
	private String count;//订购周期
	private String unit;//订购周期单位	0：天；1：月；2：年
	private String ispostpone;//是否可顺延
	//private String chopcodes;//选择性软件产品优惠勾选的产品编码串	拼串：[{"selectid":"XXXXX","pidstr":"ZZZZZ,YYYY"}],可为空,当ORDERTYPE为1时有效
	private List<ChopcodesBO> chopcodes;
	private String selpcodes;//可选产品编码串	可为空,以英文逗号分隔，当ORDERTYPE为1时有效
	
	private String salescode;
	
	private String ordertype;

	private String stime;
	
	private String mindate;
	
	public String getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}
	public String getSalescode() {
		return salescode;
	}
	public void setSalescode(String salescode) {
		this.salescode = salescode;
	}
	public String getServid() {
		return servid;
	}
	public void setServid(String servid) {
		this.servid = servid;
	}
	public String getKeyno() {
		return keyno;
	}
	public void setKeyno(String keyno) {
		this.keyno = keyno;
	}
	public String getKnowid() {
		return knowid;
	}
	public void setKnowid(String knowid) {
		this.knowid = knowid;
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
	public String getIspostpone() {
		return ispostpone;
	}
	public void setIspostpone(String ispostpone) {
		this.ispostpone = ispostpone;
	}
	public List<ChopcodesBO> getChopcodes() {
		return chopcodes;
	}
	public void setChopcodes(List<ChopcodesBO> chopcodes) {
		this.chopcodes = chopcodes;
	}
	public String getSelpcodes() {
		return selpcodes;
	}
	public void setSelpcodes(String selpcodes) {
		this.selpcodes = selpcodes;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getMindate() {
		return mindate;
	}
	public void setMindate(String mindate) {
		this.mindate = mindate;
	}
	
	
	
}
