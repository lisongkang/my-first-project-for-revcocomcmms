package com.maywide.biz.cmp.pojo;

import com.maywide.biz.core.pojo.cmphttpinf.CmpHttpBaseRespBO;

public class CmpPushResp extends CmpHttpBaseRespBO {

	private static final long serialVersionUID = 1L;

	private String tempid;
	private String content;
	private String salescode;

	public String getTempid() {
		return tempid;
	}

	public void setTempid(String tempid) {
		this.tempid = tempid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSalescode() {
		return salescode;
	}

	public void setSalescode(String salescode) {
		this.salescode = salescode;
	}

}
