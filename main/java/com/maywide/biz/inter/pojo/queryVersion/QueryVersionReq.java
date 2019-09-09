package com.maywide.biz.inter.pojo.queryVersion;

public class QueryVersionReq {
	
	private String operate;
	
	private String pkgName;
	
	private String versionName; 
	
	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	
	
	

}
