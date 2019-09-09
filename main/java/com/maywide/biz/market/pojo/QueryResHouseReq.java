package com.maywide.biz.market.pojo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueryResHouseReq  extends BaseApiRequest implements java.io.Serializable {
	private String pagesize;
	private String currentPage;
	private String addr;
	private String areaid;
	private String patch;
	private String city;
	private String rightControl;
	
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	public String getPatch() {
		return patch;
	}
	public void setPatch(String patch) {
		this.patch = patch;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRightControl() {
		return rightControl;
	}
	public void setRightControl(String rightControl) {
		this.rightControl = rightControl;
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
}
