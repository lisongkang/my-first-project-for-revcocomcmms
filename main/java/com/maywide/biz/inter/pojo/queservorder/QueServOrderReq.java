package com.maywide.biz.inter.pojo.queservorder;

import java.io.Serializable;

import com.googlecode.jsonplugin.annotations.JSON;
import com.maywide.biz.core.pojo.api.BaseApiRequest;

@SuppressWarnings("serial")
public class QueServOrderReq extends BaseApiRequest implements Serializable {

	private int pagesize = 10;

	private int currentpage = 1;
	private String fromdate;
	private String todate;
	private String custid;
	private String markno;
	private String name;
	private String deptid;
	private String operid;
	private String custorderid;
	private String servorderid;
	private String opcodeflag; // 0 故障单； 1 安装单（非故障单）
	private String isoutflag; // 0 超时工单； 1 预警工单；不传就查所有

	private String searchTxt;

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}

	public String getFromdate() {
		return fromdate;
	}

	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}

	public String getTodate() {
		return todate;
	}

	public void setTodate(String todate) {
		this.todate = todate;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getMarkno() {
		return markno;
	}

	public void setMarkno(String markno) {
		this.markno = markno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getOperid() {
		return operid;
	}

	public void setOperid(String operid) {
		this.operid = operid;
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

	public String getOpcodeflag() {
		return opcodeflag;
	}

	public void setOpcodeflag(String opcodeflag) {
		this.opcodeflag = opcodeflag;
	}

	public String getIsoutflag() {
		return isoutflag;
	}

	public void setIsoutflag(String isoutflag) {
		this.isoutflag = isoutflag;
	}

	@JSON(serialize = false)
	public String getSearchTxt() {
		return searchTxt;
	}

	public void setSearchTxt(String searchTxt) {
		this.searchTxt = searchTxt;
	}

}
