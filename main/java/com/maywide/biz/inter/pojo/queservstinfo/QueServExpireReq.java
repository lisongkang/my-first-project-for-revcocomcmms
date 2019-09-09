package com.maywide.biz.inter.pojo.queservstinfo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;


public class QueServExpireReq extends BaseApiRequest  implements java.io.Serializable {

	private String patchids  				;
	private String quetype          ;
	private String custid           ;
	private String custname         ;
	private String logicdevno       ;
	private String objid            ;
	private String sdate            ;
	private String edate            ;
	private String servid		;
	private String   pagesize;
	private String   currentpage;

	public String getPatchids() {
		return patchids;
	}
	public void setPatchids(String patchids) {
		this.patchids = patchids;
	}
	public String getQuetype() {
		return quetype;
	}
	public void setQuetype(String quetype) {
		this.quetype = quetype;
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getCustname() {
		return custname;
	}
	public void setCustname(String custname) {
		this.custname = custname;
	}
	public String getLogicdevno() {
		return logicdevno;
	}
	public void setLogicdevno(String logicdevno) {
		this.logicdevno = logicdevno;
	}
	public String getObjid() {
		return objid;
	}
	public void setObjid(String objid) {
		this.objid = objid;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getEdate() {
		return edate;
	}
	public void setEdate(String edate) {
		this.edate = edate;
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
	public String getServid() {
		return servid;
	}
	public void setServid(String servid) {
		this.servid = servid;
	}
	
}
