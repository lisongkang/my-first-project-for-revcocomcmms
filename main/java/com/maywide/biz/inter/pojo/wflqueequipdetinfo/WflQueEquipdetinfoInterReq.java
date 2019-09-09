package com.maywide.biz.inter.pojo.wflqueequipdetinfo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class WflQueEquipdetinfoInterReq extends BaseApiRequest {
	private String custid ;
	private String custorderid;
	private String servorderid;
	private String serialno;
	private String channels;
	private String bizcode;
	private String areaid;
	private String deptid;
	private String permark;
	private String addr;
	private String patchids;
	private String gridcodes;
	private String soptime;
	private String eoptime;
	private String stepcode;
	private String pagesize;
	private String currentpage;
	private boolean filter = true;
	
	public boolean isFilter() {
		return filter;
	}
	public void setFilter(boolean filter) {
		this.filter = filter;
	}
	public String getPagesize() {
		return pagesize;
	}
	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}
	public String getCurrentpage() {
		return currentpage;
	}
	public void setCurrentpage(String currentpage) {
		this.currentpage = currentpage;
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getCustorderid() {
		return custorderid;
	}
	public void setCustorderid(String custorderid) {
		this.custorderid = custorderid;
	}
	public String getServorderid() {
		return servorderid;
	}
	public void setServorderid(String servorderid) {
		this.servorderid = servorderid;
	}
	public String getSerialno() {
		return serialno;
	}
	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}
	public String getChannels() {
		return channels;
	}
	public void setChannels(String channels) {
		this.channels = channels;
	}
	public String getBizcode() {
		return bizcode;
	}
	public void setBizcode(String bizcode) {
		this.bizcode = bizcode;
	}
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public String getPermark() {
		return permark;
	}
	public void setPermark(String permark) {
		this.permark = permark;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getPatchids() {
		return patchids;
	}
	public void setPatchids(String patchids) {
		this.patchids = patchids;
	}
	public String getGridcodes() {
		return gridcodes;
	}
	public void setGridcodes(String gridcodes) {
		this.gridcodes = gridcodes;
	}
	public String getSoptime() {
		return soptime;
	}
	public void setSoptime(String soptime) {
		this.soptime = soptime;
	}
	public String getEoptime() {
		return eoptime;
	}
	public void setEoptime(String eoptime) {
		this.eoptime = eoptime;
	}
	public String getStepcode() {
		return stepcode;
	}
	public void setStepcode(String stepcode) {
		this.stepcode = stepcode;
	}
}
