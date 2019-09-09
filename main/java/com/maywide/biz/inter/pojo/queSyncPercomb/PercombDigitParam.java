package com.maywide.biz.inter.pojo.queSyncPercomb;

import java.util.List;

import com.maywide.biz.system.entity.PrvSysparam;

public class PercombDigitParam {
	
	private String servtype;

	private String iscable;

	private String platform;
	
	private List<PrvSysparam> servtypeList;
	
	private List<PrvSysparam> platformList;

	public String getServtype() {
		return servtype;
	}

	public void setServtype(String servtype) {
		this.servtype = servtype;
	}

	public String getIscable() {
		return iscable;
	}

	public void setIscable(String iscable) {
		this.iscable = iscable;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public List<PrvSysparam> getServtypeList() {
		return servtypeList;
	}

	public void setServtypeList(List<PrvSysparam> servtypeList) {
		this.servtypeList = servtypeList;
	}

	public List<PrvSysparam> getPlatformList() {
		return platformList;
	}

	public void setPlatformList(List<PrvSysparam> platformList) {
		this.platformList = platformList;
	}
	
	
}
