package com.maywide.biz.inter.pojo.querycatalog;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueryCatalogInterReq extends BaseApiRequest implements java.io.Serializable {
	
	private String areaid;//业务区id
	private String catalogname;//营销批次号
	private String catalogstatus;//处理状态
	private String bizcode;
	
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	public String getCatalogname() {
		return catalogname;
	}
	public void setCatalogname(String catalogname) {
		this.catalogname = catalogname;
	}
	public String getCatalogstatus() {
		return catalogstatus;
	}
	public void setCatalogstatus(String catalogstatus) {
		this.catalogstatus = catalogstatus;
	}
	public String getBizcode() {
		return bizcode;
	}
	public void setBizcode(String bizcode) {
		this.bizcode = bizcode;
	}
	
	
	
}
