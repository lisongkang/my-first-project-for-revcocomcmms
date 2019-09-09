package com.maywide.biz.inter.pojo.chgdev;

import java.util.Date;

/**
 * 价格对象，保存价格所必备的业务信息
 * 
 * @author maywide
 * 
 */
public class PrdPriceBO implements java.io.Serializable {
	/** 用户ID */
	private Long servid;
	/** 产品ID */
	private Long pid;
	/** 商品ID */
	private Long salesid;
	/** 商品名称 */
	private String salesname;
	/** 订购周期（数量） */
	private Long count;
	/** 计费周期 */
	private Long cycle;
	/** 周期单位 */
	private String unit;
	/** 起始订购时间 */
	private Date stime;
	/** 终止订购时间 */
	private Date etime;
	/** 标准单价 */
	private Double stdfee;
	/** 实收单价 */
	private Double rstdfee;
	/** 应收 */
	private Double spay;
	/** 费项名称 */
	private String feename;
	/** 内部费项代码 */
	private String feecode;
	/** 发票费项代码 */
	private String ifeecode;
	/** 报表费项代码 */
	private String rfeecode;
	/** 实收金额 */
	private Double rpay;
	
	private Double fee;
	/** 营销方案ID */
	private Long salespkgid;
	/** 收费模式 */
	private String chargemode;
	/** 残月计费规则 */
	private String firstfee;
	/** 合同协议号 */
	private Long contractid;
	/** 分段段落id */
	private Long segid;

	private String sdate;

	private String edate;

	private Date expdate;

	private String prodflag;

	private String city;

	private Long tprecid;

	private String frontmode;

	private Long salespkginsid;

	private String ismasterprd;

	private Double standardfee;
	private Long mainservid;
	private Long mainsalesid;
	private Long mainrecid;
	private Long plength;

	public Long getServid() {
		return servid;
	}

	public void setServid(Long servid) {
		this.servid = servid;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getSalesid() {
		return salesid;
	}

	public void setSalesid(Long salesid) {
		this.salesid = salesid;
	}

	public String getSalesname() {
		return salesname;
	}

	public void setSalesname(String salesname) {
		this.salesname = salesname;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getCycle() {
		return cycle;
	}

	public void setCycle(Long cycle) {
		this.cycle = cycle;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Date getStime() {
		return stime;
	}

	public void setStime(Date stime) {
		this.stime = stime;
	}

	public Date getEtime() {
		return etime;
	}

	public void setEtime(Date etime) {
		this.etime = etime;
	}

	public Double getStdfee() {
		return stdfee;
	}

	public void setStdfee(Double stdfee) {
		this.stdfee = stdfee;
	}

	public Double getRstdfee() {
		return rstdfee;
	}

	public void setRstdfee(Double rstdfee) {
		this.rstdfee = rstdfee;
	}

	public Double getSpay() {
		return spay;
	}

	public void setSpay(Double spay) {
		this.spay = spay;
	}

	public String getFeename() {
		return feename;
	}

	public void setFeename(String feename) {
		this.feename = feename;
	}

	public String getFeecode() {
		return feecode;
	}

	public void setFeecode(String feecode) {
		this.feecode = feecode;
	}

	public String getIfeecode() {
		return ifeecode;
	}

	public void setIfeecode(String ifeecode) {
		this.ifeecode = ifeecode;
	}

	public String getRfeecode() {
		return rfeecode;
	}

	public void setRfeecode(String rfeecode) {
		this.rfeecode = rfeecode;
	}

	public Double getRpay() {
		return rpay;
	}

	public void setRpay(Double rpay) {
		this.rpay = rpay;
	}

	public Long getSalespkgid() {
		return salespkgid;
	}

	public void setSalespkgid(Long salespkgid) {
		this.salespkgid = salespkgid;
	}

	public String getChargemode() {
		return chargemode;
	}

	public void setChargemode(String chargemode) {
		this.chargemode = chargemode;
	}

	public String getFirstfee() {
		return firstfee;
	}

	public void setFirstfee(String firstfee) {
		this.firstfee = firstfee;
	}

	public Long getContractid() {
		return contractid;
	}

	public void setContractid(Long contractid) {
		this.contractid = contractid;
	}

	public Long getSegid() {
		return segid;
	}

	public void setSegid(Long segid) {
		this.segid = segid;
	}
	
	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public Date getExpdate() {
		return expdate;
	}

	public void setExpdate(Date expdate) {
		this.expdate = expdate;
	}

	public String getProdflag() {
		return prodflag;
	}

	public void setProdflag(String prodflag) {
		this.prodflag = prodflag;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getTprecid() {
		return tprecid;
	}

	public void setTprecid(Long tprecid) {
		this.tprecid = tprecid;
	}

	public String getFrontmode() {
		return frontmode;
	}

	public void setFrontmode(String frontmode) {
		this.frontmode = frontmode;
	}

	public Long getSalespkginsid() {
		return salespkginsid;
	}

	public void setSalespkginsid(Long salespkginsid) {
		this.salespkginsid = salespkginsid;
	}

	public String getIsmasterprd() {
		return ismasterprd;
	}

	public void setIsmasterprd(String ismasterprd) {
		this.ismasterprd = ismasterprd;
	}

	public Double getStandardfee() {
		return standardfee;
	}

	public void setStandardfee(Double standardfee) {
		this.standardfee = standardfee;
	}

	public Long getMainservid() {
		return mainservid;
	}

	public void setMainservid(Long mainservid) {
		this.mainservid = mainservid;
	}

	public Long getMainsalesid() {
		return mainsalesid;
	}

	public void setMainsalesid(Long mainsalesid) {
		this.mainsalesid = mainsalesid;
	}

	public Long getMainrecid() {
		return mainrecid;
	}

	public void setMainrecid(Long mainrecid) {
		this.mainrecid = mainrecid;
	}

	public Long getPlength() {
		return plength;
	}

	public void setPlength(Long plength) {
		this.plength = plength;
	}
}
