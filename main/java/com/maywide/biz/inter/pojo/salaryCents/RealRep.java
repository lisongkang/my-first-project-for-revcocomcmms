package com.maywide.biz.inter.pojo.salaryCents;

import com.maywide.biz.core.pojo.TokenReturnInfo;

public class RealRep extends TokenReturnInfo {
	private Integer totalnums;
	private Double totalcents;  //个人总实积分
	private Integer pagesize;
	private Integer currentPage;

	public Integer getTotalnums() {
		return totalnums;
	}

	public void setTotalnums(Integer totalnums) {
		this.totalnums = totalnums;
	}

	public Double getTotalcents() {
		return totalcents;
	}

	public void setTotalcents(Double totalcents) {
		this.totalcents = totalcents;
	}

	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
}
