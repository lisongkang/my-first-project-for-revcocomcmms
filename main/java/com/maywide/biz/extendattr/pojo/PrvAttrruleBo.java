package com.maywide.biz.extendattr.pojo;

import java.util.List;

/**
 * @author zhuangzhitang-pc
 *
 */
public class PrvAttrruleBo {
	private String id;
	private String city;
	private String attrname;
	private String attrcode;
	private String valuesrc;
	private String valueparam;
	private String ifnecessary;
	private String defaultvalue;
	private String mnames;//存储参数选项
	
	private List<String> cityList;
	
    
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAttrname() {
		return attrname;
	}

	public void setAttrname(String attrname) {
		this.attrname = attrname;
	}

	public String getAttrcode() {
		return attrcode;
	}

	public void setAttrcode(String attrcode) {
		this.attrcode = attrcode;
	}

	public String getValuesrc() {
		return valuesrc;
	}

	public void setValuesrc(String valuesrc) {
		this.valuesrc = valuesrc;
	}

	public String getValueparam() {
		return valueparam;
	}

	public void setValueparam(String valueparam) {
		this.valueparam = valueparam;
	}

	public String getIfnecessary() {
		return ifnecessary;
	}

	public void setIfnecessary(String ifnecessary) {
		this.ifnecessary = ifnecessary;
	}

	public String getDefaultvalue() {
		return defaultvalue;
	}

	public void setDefaultvalue(String defaultvalue) {
		this.defaultvalue = defaultvalue;
	}

	public List<String> getCityList() {
		return cityList;
	}

	public void setCityList(List<String> cityList) {
		this.cityList = cityList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMnames() {
		return mnames;
	}

	public void setMnames(String mnames) {
		this.mnames = mnames;
	}
	
	
}
