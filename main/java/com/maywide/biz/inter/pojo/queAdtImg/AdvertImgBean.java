package com.maywide.biz.inter.pojo.queAdtImg;

import java.util.Map;

public class AdvertImgBean {

private String url;
	
	private String path;
	
	private String toClz;
	
	private Map<String, String> params;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getToClz() {
		return toClz;
	}

	public void setToClz(String toClz) {
		this.toClz = toClz;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
}
