package com.maywide.biz.inter.pojo.querycatalog;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueCustMarktInfoReq extends BaseApiRequest  implements java.io.Serializable {
	private String  patchids			;
	private String  netattr       ;
	private String  servtype      ;
	private String  devno         ;
	private String  logicno       ;
	private String  iscust       ;
	private String  pagesize      ;
	private String  currentPage   ;
	public String getPatchids() {
		return patchids;
	}
	public void setPatchids(String patchids) {
		this.patchids = patchids;
	}
	public String getNetattr() {
		return netattr;
	}
	public void setNetattr(String netattr) {
		this.netattr = netattr;
	}
	public String getServtype() {
		return servtype;
	}
	public void setServtype(String servtype) {
		this.servtype = servtype;
	}
	public String getDevno() {
		return devno;
	}
	public void setDevno(String devno) {
		this.devno = devno;
	}
	public String getLogicno() {
		return logicno;
	}
	public void setLogicno(String logicno) {
		this.logicno = logicno;
	}
	public String getIscust() {
		return iscust;
	}
	public void setIscust(String iscust) {
		this.iscust = iscust;
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
