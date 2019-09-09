package com.maywide.biz.inter.pojo.queryVersion;

public class QueryVersionResp {
	
	private int versionForce;
	
	private String lastestVersion;
	
	private String downUrl;
	
	private String descript;
	
	private String advertImg;
	
	private boolean hasVersion;
	
	private String title;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isHasVersion() {
		return hasVersion;
	}

	public void setHasVersion(boolean hasVersion) {
		this.hasVersion = hasVersion;
	}

	public int getVersionForce() {
		return versionForce;
	}

	public void setVersionForce(int versionForce) {
		this.versionForce = versionForce;
	}


	public String getLastestVersion() {
		return lastestVersion;
	}

	public void setLastestVersion(String lastestVersion) {
		this.lastestVersion = lastestVersion;
	}

	public String getDownUrl() {
		return downUrl;
	}

	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getAdvertImg() {
		return advertImg;
	}

	public void setAdvertImg(String advertImg) {
		this.advertImg = advertImg;
	}
	
	

}
