package com.maywide.biz.inter.pojo.queDevstore;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueDevstoreReq extends BaseApiRequest {
	
	private String tmpPlace;
	
	private String status;
	
	private String appstatus;
	
	private String kind;
	
	private String subkind;
	
	private String devno;
	
	private String devattr;
	
	private int currentPage = 1;
	
	private int pageSize = 10;

	public String getTmpPlace() {
		return tmpPlace;
	}

	public void setTmpPlace(String tmpPlace) {
		this.tmpPlace = tmpPlace;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAppstatus() {
		return appstatus;
	}

	public void setAppstatus(String appstatus) {
		this.appstatus = appstatus;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getSubkind() {
		return subkind;
	}

	public void setSubkind(String subkind) {
		this.subkind = subkind;
	}

	public String getDevno() {
		return devno;
	}

	public void setDevno(String devno) {
		this.devno = devno;
	}

	public String getDevattr() {
		return devattr;
	}

	public void setDevattr(String devattr) {
		this.devattr = devattr;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	

}
