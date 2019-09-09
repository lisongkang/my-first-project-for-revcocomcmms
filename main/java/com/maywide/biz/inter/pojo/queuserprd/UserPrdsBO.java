package com.maywide.biz.inter.pojo.queuserprd;

import java.util.List;

public class UserPrdsBO implements java.io.Serializable {
	private String servid;// 用户id
	private String keyno;// 用户设备号
	private String pid;// 产品id
	private String pcode;// 产品编码
	private String pname;// 产品名称
	private String prodtype;// 产品类别 0-产品，1-组合产品
	private String permark;// 业务类型
	private String prodarrears;// 产品欠费
	private String prodfees;// 产品余额
	private String stime;// 订购开始时间

	private String pstatus;// 产品状态
	private String statusdate;// 状态时间
	private String payway;// 缴费方式
	private String ispostpone;// 是否自动顺延 Y-是,N-否,G-套餐顺延

	private String salespkgid;// 营销方案id
	private String pkginsid;// 营销方案实例ID

	// private String permark;//业务类型
	private String optime;// 订购时间
	private String stoplock;
	// 新增结果产品信息
	private String fbtime;// 可用时间
	private String stoptime;// 指定停用时间
	private String salesid;
    private String mindate;//最少使用期限
	private String etime;// 订购结束时间

	public String getMindate() {
		return mindate;
	}

	public void setMindate(String mindate) {
		this.mindate = mindate;
	}

	private List<MixProdBO> mixprods;

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

	private String stdfee;// 单价

	private String salescode;

	private String salesname;

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

	public String getProdtype() {
		return prodtype;
	}

	public void setProdtype(String prodtype) {
		this.prodtype = prodtype;
	}

	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}

	public String getProdarrears() {
		return prodarrears;
	}

	public void setProdarrears(String prodarrears) {
		this.prodarrears = prodarrears;
	}

	public String getProdfees() {
		return prodfees;
	}

	public void setProdfees(String prodfees) {
		this.prodfees = prodfees;
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

	public String getPstatus() {
		return pstatus;
	}

	public void setPstatus(String pstatus) {
		this.pstatus = pstatus;
	}

	public String getStatusdate() {
		return statusdate;
	}

	public void setStatusdate(String statusdate) {
		this.statusdate = statusdate;
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

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
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

	public String getStdfee() {
		return stdfee;
	}

	public void setStdfee(String stdfee) {
		this.stdfee = stdfee;
	}

	public String getStoplock() {
		return stoplock;
	}

	public void setStoplock(String stoplock) {
		this.stoplock = stoplock;
	}

	public String getFbtime() {
		return fbtime;
	}

	public void setFbtime(String fbtime) {
		this.fbtime = fbtime;
	}

	public String getStoptime() {
		return stoptime;
	}

	public void setStoptime(String stoptime) {
		this.stoptime = stoptime;
	}

	public String getSalesid() {
		return salesid;
	}

	public void setSalesid(String salesid) {
		this.salesid = salesid;
	}

	public List<MixProdBO> getMixprods() {
		return mixprods;
	}

	public void setMixprods(List<MixProdBO> mixprods) {
		this.mixprods = mixprods;
	}

	
}
