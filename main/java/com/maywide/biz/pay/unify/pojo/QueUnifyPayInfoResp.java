package com.maywide.biz.pay.unify.pojo;

public class QueUnifyPayInfoResp {

	private String requestid;

	private String requestContent;

	private String url;

	public String getRequestid() {
		return requestid;
	}

	public void setRequestid(String requestid) {
		this.requestid = requestid;
	}

	public String getRequestContent() {
		return requestContent;
	}

	public void setRequestContent(String requestContent) {
		this.requestContent = requestContent;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public QueUnifyPayInfoResp() {
	}

	public QueUnifyPayInfoResp(String requestid, String requestContent, String url) {
		this.requestid = requestid;
		this.requestContent = requestContent;
		this.url = url;
	}
}
