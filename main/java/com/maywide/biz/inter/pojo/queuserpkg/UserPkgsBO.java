package com.maywide.biz.inter.pojo.queuserpkg;

import java.util.List;

public class UserPkgsBO implements java.io.Serializable {
	private String servid;// 用户id
	private String keyno;// 用户设备号
	private String pcode;// 产品编码	
	private String pname;// 产品名称	
	private String salespkgid;// 营销方案id	
	private String pkginsid;// 营销方案实例ID	
	private String optime;// 订购时间
	private String etime;// 到期时间
	private String payway;// 缴费方式
	private String ispostpone;// 是否自动顺延	Y-是,N-否
	private String stdfee;// 单价
	private String pstatus;
	
	
	public String getPstatus() {
		return pstatus;
	}
	public void setPstatus(String pstatus) {
		this.pstatus = pstatus;
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
	public String getSalespkgid() {
		return salespkgid;
	}
	public void setSalespkgid(String salespkgid) {
		this.salespkgid = salespkgid;
	}
	public String getPkginsid() {
		return pkginsid;
	}
	public void setPkginsid(String pkginsid) {
		this.pkginsid = pkginsid;
	}
	public String getOptime() {
		return optime;
	}
	public void setOptime(String optime) {
		this.optime = optime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public String getPayway() {
		return payway;
	}
	public void setPayway(String payway) {
		this.payway = payway;
	}
	public String getIspostpone() {
		return ispostpone;
	}
	public void setIspostpone(String ispostpone) {
		this.ispostpone = ispostpone;
	}
	public String getStdfee() {
		return stdfee;
	}
	public void setStdfee(String stdfee) {
		this.stdfee = stdfee;
	}

	
}
