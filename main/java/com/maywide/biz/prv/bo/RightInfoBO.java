package com.maywide.biz.prv.bo;

import java.io.Serializable;

public class RightInfoBO implements Serializable {
	private String areaname;
	private Long areaid;
	private String departname;
	private String departid;
	private String operatorname;
	private String operatorid;
	private Long patchid;
	private String patchname;
	private String city;
	private String cityname;
	private String subkind;
	private String subkindname;
	private String kind;
	private String kindname;
	
	
	
	
	public String getSubkind() {
		return subkind;
	}
	public void setSubkind(String subkind) {
		this.subkind = subkind;
	}
	public String getSubkindname() {
		return subkindname;
	}
	public void setSubkindname(String subkindname) {
		this.subkindname = subkindname;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getKindname() {
		return kindname;
	}
	public void setKindname(String kindname) {
		this.kindname = kindname;
	}
	public String getCityname() {
		return cityname;
	}
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public Long getAreaid() {
		return areaid;
	}
	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}

	public String getOperatorname() {
		return operatorname;
	}
	public void setOperatorname(String operatorname) {
		this.operatorname = operatorname;
	}
	public String getOperatorid() {
		return operatorid;
	}
	public void setOperatorid(String operatorid) {
		this.operatorid = operatorid;
	}
	public String getDepartname() {
		return departname;
	}
	public void setDepartname(String departname) {
		this.departname = departname;
	}
	public String getDepartid() {
		return departid;
	}
	public void setDepartid(String departid) {
		this.departid = departid;
	}
	public Long getPatchid() {
		return patchid;
	}
	public void setPatchid(Long patchid) {
		this.patchid = patchid;
	}
	public String getPatchname() {
		return patchname;
	}
	public void setPatchname(String patchname) {
		this.patchname = patchname;
	}
}
