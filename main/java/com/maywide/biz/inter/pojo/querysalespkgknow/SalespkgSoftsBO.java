package com.maywide.biz.inter.pojo.querysalespkgknow;

public class SalespkgSoftsBO extends KnowPrdBO implements java.io.Serializable {
	
	private String id;//软件优惠id
	private String timetype;//优惠结果类型：0-指定周期，1-指定截止日期
	private String presentcycle;//优惠周期,对优惠结果类型为优惠周期有效
	private String unit;//优惠周期单位,对优惠结果类型为优惠周期有效
	private String fixedate;//赠送至截止时间,对优惠结果类型为赠送至截止时间有效
	private String ispostpone;//是否自动顺延	
	private String ismasterprd;//是否主产品	
	private String optionflag;//可选标识：0-必选，1-可选默认已选，2-可选默认未选
	private String issel;//是否已选择：Y-是，N-否
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOptionflag() {
		return optionflag;
	}
	public void setOptionflag(String optionflag) {
		this.optionflag = optionflag;
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
	public String getIspostpone() {
		return ispostpone;
	}
	public void setIspostpone(String ispostpone) {
		this.ispostpone = ispostpone;
	}
	public String getIsmasterprd() {
		return ismasterprd;
	}
	public void setIsmasterprd(String ismasterprd) {
		this.ismasterprd = ismasterprd;
	}
	public String getIssel() {
		return issel;
	}
	public void setIssel(String issel) {
		this.issel = issel;
	}
	
}
