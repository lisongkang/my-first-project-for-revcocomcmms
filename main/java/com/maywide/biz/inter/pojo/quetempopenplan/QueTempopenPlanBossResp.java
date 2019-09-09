package com.maywide.biz.inter.pojo.quetempopenplan;

import java.util.List;

public class QueTempopenPlanBossResp  implements java.io.Serializable {
	private String pagesize;
	private String totalRecords;
	private String currentPage;
	
	private List<TempopenPlanInfoBO> plans;
	public String getPagesize() {
		return pagesize;
	}
	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}
	public String getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public List<TempopenPlanInfoBO> getPlans() {
		return plans;
	}
	public void setPlans(List<TempopenPlanInfoBO> plans) {
		this.plans = plans;
	}

}
