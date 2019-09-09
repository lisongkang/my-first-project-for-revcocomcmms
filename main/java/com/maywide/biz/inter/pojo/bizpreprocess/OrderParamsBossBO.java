package com.maywide.biz.inter.pojo.bizpreprocess;

import java.util.List;

public class OrderParamsBossBO implements java.io.Serializable {
	private String keyno;//计费对象
	private String permark;//业务类型
	private String ordertype;//订购类型	0订购产品、1订购套餐
	private String salescode;//商品编码  订购产品传pcode,订购套餐传 套餐代码
	private String count;//订购周期
	private String unit;//订购周期单位	0：天；1：月；2：年
	private String ispostpone;//是否可顺延
	//private String chopcodes;//选择性软件产品优惠勾选的产品编码串	拼串：[{"selectid":"XXXXX","pidstr":"ZZZZZ,YYYY"}],可为空,当ORDERTYPE为1时有效
	private List<ChopcodesBossBO> chopcodes;
	private String selpcodes;//可选产品编码串	可为空,以英文逗号分隔，当ORDERTYPE为1时有效
	private String rpay;
	private String stime; //预约时间
	private String mindate;
	
	
	
	public String getMindate() {
		return mindate;
	}
	public void setMindate(String mindate) {
		this.mindate = mindate;
	}
	public String getKeyno() {
		return keyno;
	}
	public void setKeyno(String keyno) {
		this.keyno = keyno;
	}
	public String getPermark() {
		return permark;
	}
	public void setPermark(String permark) {
		this.permark = permark;
	}
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
	public List<ChopcodesBossBO> getChopcodes() {
		return chopcodes;
	}
	public void setChopcodes(List<ChopcodesBossBO> chopcodes) {
		this.chopcodes = chopcodes;
	}
	public String getSelpcodes() {
		return selpcodes;
	}
	public void setSelpcodes(String selpcodes) {
		this.selpcodes = selpcodes;
	}
	public String getRpay() {
		return rpay;
	}
	public void setRpay(String rpay) {
		this.rpay = rpay;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		if (stime != null && stime.length() == 10) {
			this.stime = stime + " 00:00:00";
		} else {
			this.stime = stime;
		}
	}

	
}
