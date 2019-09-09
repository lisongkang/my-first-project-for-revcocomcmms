package com.maywide.biz.inter.pojo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueryCustInfoReq  extends BaseApiRequest implements java.io.Serializable {
	private String pagesize;
	private String currentPage;
	private String identifierType;
	private String identifier;
	private String city;
	private String isQueGrp;
	private String areaids;
	public String getIdentifierType() {
		return identifierType;
	}
	public void setIdentifierType(String identifierType) {
		this.identifierType = identifierType;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
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
	public String getIsQueGrp() {
		return isQueGrp;
	}
	public void setIsQueGrp(String isQueGrp) {
		this.isQueGrp = isQueGrp;
	}
	public String getAreaids() {
		return areaids;
	}
	public void setAreaids(String areaids) {
		this.areaids = areaids;
	}
	
	
}
