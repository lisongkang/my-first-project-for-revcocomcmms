package com.maywide.biz.inter.pojo.queCustAttr;

import java.util.List;

public class CustAttrBean {

	private Long attrId;
	
	private String attrCode;
	
	private String attrName;
	
	private String attrValue;
	
	private String valueSrc;
	
	private String ifNecessary;
	
	private String scopeFlag;
	
	private String min;
	
	private String max;
	
	private Long creater;
	
	private String gcode;
	
	private List<CustAttrChildBean> options;

	public Long getAttrId() {
		return attrId;
	}

	public void setAttrId(Long attrId) {
		this.attrId = attrId;
	}

	public String getAttrCode() {
		return attrCode;
	}

	public void setAttrCode(String attrCode) {
		this.attrCode = attrCode;
	}

	public String getValueSrc() {
		return valueSrc;
	}

	public void setValueSrc(String valueSrc) {
		this.valueSrc = valueSrc;
	}

	public String getIfNecessary() {
		return ifNecessary;
	}

	public void setIfNecessary(String ifNecessary) {
		this.ifNecessary = ifNecessary;
	}

	public String getScopeFlag() {
		return scopeFlag;
	}

	public void setScopeFlag(String scopeFlag) {
		this.scopeFlag = scopeFlag;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public Long getCreater() {
		return creater;
	}

	public void setCreater(Long creater) {
		this.creater = creater;
	}

	public String getGcode() {
		return gcode;
	}

	public void setGcode(String gcode) {
		this.gcode = gcode;
	}

	public List<CustAttrChildBean> getOptions() {
		return options;
	}

	public void setOptions(List<CustAttrChildBean> options) {
		this.options = options;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}
	
	
	
}
