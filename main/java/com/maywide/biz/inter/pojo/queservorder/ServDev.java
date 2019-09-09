package com.maywide.biz.inter.pojo.queservorder;

import java.util.List;

public class ServDev {

	private String servcode; // 服务代码
	private String value; // 设备编号
	private String syscode; // 资源类型 1 智能卡 、 2 机顶盒 、 3 CM 等
	private String resourcename; // 资源名称
	private List<DevSubKind> subkindList; // 可录入设备型号列表

	public String getServcode() {
		return servcode;
	}

	public void setServcode(String servcode) {
		this.servcode = servcode;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSyscode() {
		return syscode;
	}

	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}

	public String getResourcename() {
		return resourcename;
	}

	public void setResourcename(String resourcename) {
		this.resourcename = resourcename;
	}

	public List<DevSubKind> getSubkindList() {
		return subkindList;
	}

	public void setSubkindList(List<DevSubKind> subkindList) {
		this.subkindList = subkindList;
	}

}
