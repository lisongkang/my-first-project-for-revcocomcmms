package com.maywide.biz.inter.pojo.quebuslistinfo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueBuslistinfoInterReq extends BaseApiRequest {
	private String areastr;
	private String grpmanagerid;
	private String busname;
	private String stime;
	private String etime;
	private String rightstr;
	private String pagesize;// 分页大小
	private String currentPage;// 当前页数
	public String getAreastr() {
		return areastr;
	}
	public void setAreastr(String areastr) {
		this.areastr = areastr;
	}
	public String getGrpmanagerid() {
		return grpmanagerid;
	}
	public void setGrpmanagerid(String grpmanagerid) {
		this.grpmanagerid = grpmanagerid;
	}
	public String getBusname() {
		return busname;
	}
	public void setBusname(String busname) {
		this.busname = busname;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public String getRightstr() {
		return rightstr;
	}
	public void setRightstr(String rightstr) {
		this.rightstr = rightstr;
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
