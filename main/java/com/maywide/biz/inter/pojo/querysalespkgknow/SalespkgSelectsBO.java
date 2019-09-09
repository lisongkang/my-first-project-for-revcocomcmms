package com.maywide.biz.inter.pojo.querysalespkgknow;

import java.util.List;

public class SalespkgSelectsBO implements java.io.Serializable {
	private String selectid;//选择方案标识
	private String selectdesc;//优惠描述
	private String selectnum;//选择数量
	private String timetype;//优惠结果类型：0-指定周期，1-指定截止日期
	private String presentcycle;//优惠周期,对优惠结果类型为优惠周期有效
	private String unit;//优惠周期单位,对优惠结果类型为优惠周期有效
	private String fixedate;//赠送至截止时间,对优惠结果类型为赠送至截止时间有效
	private String ispostpone;//是否自动顺延
	private String optionflag;//可选标识：0-必选，1-可选默认已选，2-可选默认未选
	private List<SalespkgSelectDetsBO> prds;//方案内产品列表
	
	public String getSelectid() {
		return selectid;
	}
	public void setSelectid(String selectid) {
		this.selectid = selectid;
	}
	public String getSelectdesc() {
		return selectdesc;
	}
	public void setSelectdesc(String selectdesc) {
		this.selectdesc = selectdesc;
	}
	public String getSelectnum() {
		return selectnum;
	}
	public void setSelectnum(String selectnum) {
		this.selectnum = selectnum;
	}
	public String getTimetype() {
		return timetype;
	}
	public void setTimetype(String timetype) {
		this.timetype = timetype;
	}
	public String getPresentcycle() {
		return presentcycle;
	}
	public void setPresentcycle(String presentcycle) {
		this.presentcycle = presentcycle;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getFixedate() {
		return fixedate;
	}
	public void setFixedate(String fixedate) {
		this.fixedate = fixedate;
	}
	public String getOptionflag() {
		return optionflag;
	}
	public void setOptionflag(String optionflag) {
		this.optionflag = optionflag;
	}
	public List<SalespkgSelectDetsBO> getPrds() {
		return prds;
	}
	public void setPrds(List<SalespkgSelectDetsBO> prds) {
		this.prds = prds;
	}
	public String getIspostpone() {
		return ispostpone;
	}
	public void setIspostpone(String ispostpone) {
		this.ispostpone = ispostpone;
	}
	
}
