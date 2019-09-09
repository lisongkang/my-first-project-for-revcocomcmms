package com.maywide.test.pojo;

import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;
import com.maywide.biz.inter.pojo.queservstinfo.QueConditionsBO;

public class QueServstInfoInterReqTest  implements java.io.Serializable {

	private String pagesize;
	private String currentPage;
	private List<QueConditionsBO> queConditions;

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

	public List<QueConditionsBO> getQueConditions() {
		return queConditions;
	}

	public void setQueConditions(List<QueConditionsBO> queConditions) {
		this.queConditions = queConditions;
	}

}
