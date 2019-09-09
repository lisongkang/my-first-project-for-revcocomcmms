package com.maywide.biz.inter.pojo.quetempopenplan;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueTempopenPlanInterReq extends BaseApiRequest implements
		java.io.Serializable {
	private String pagesize;
	private String currentPage;
	private String areaid;
	private String permark;
	
	
	public String getPermark() {
		return permark;
	}
	public void setPermark(String permark) {
		this.permark = permark;
	}
	public String getPagesize() {
		return pagesize;
	}
	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

}
