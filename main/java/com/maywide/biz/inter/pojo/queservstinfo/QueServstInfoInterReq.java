package com.maywide.biz.inter.pojo.queservstinfo;

import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueServstInfoInterReq extends BaseApiRequest implements java.io.Serializable {

	private String pagesize;
	private String currentPage;
	private String isNotQueServInfo;
	private String notGrid;
	private List<QueConditionsBO> queConditions;
	
	public QueServstInfoInterReq(String pagesize, String currentPage,
			List<QueConditionsBO> queConditions) {
		super();
		this.pagesize = pagesize;
		this.currentPage = currentPage;
		this.queConditions = queConditions;
	}

	public QueServstInfoInterReq() {
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

	public List<QueConditionsBO> getQueConditions() {
		return queConditions;
	}

	public void setQueConditions(List<QueConditionsBO> queConditions) {
		this.queConditions = queConditions;
	}

    public String getIsNotQueServInfo() {
        return isNotQueServInfo;
    }

    public void setIsNotQueServInfo(String isNotQueServInfo) {
        this.isNotQueServInfo = isNotQueServInfo;
    }

	public String getNotGrid() {
		return notGrid;
	}

	public void setNotGrid(String notGrid) {
		this.notGrid = notGrid;
	}
    

}
