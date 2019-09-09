package com.maywide.biz.inter.pojo.queservstinfo;

import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueServstInfoBossReq implements java.io.Serializable {

	private String pagesize;
	private String currentPage;
	private String city;
	private Long areaid;
	private String isNotQueServInfo;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

    public String getIsNotQueServInfo() {
        return isNotQueServInfo;
    }

    public void setIsNotQueServInfo(String isNotQueServInfo) {
        this.isNotQueServInfo = isNotQueServInfo;
    }

	public Long getAreaid() {
		return areaid;
	}

	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}
    

}
