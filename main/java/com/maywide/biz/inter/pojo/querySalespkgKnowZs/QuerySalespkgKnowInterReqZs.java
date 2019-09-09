package com.maywide.biz.inter.pojo.querySalespkgKnowZs;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QuerySalespkgKnowInterReqZs  extends BaseApiRequest implements java.io.Serializable {
	private String seriesId ;
	private String knowname;
	private String knowid;
	private Integer objtype;
	private Integer busType = -1;
	private boolean needdetail = true; 
	private String opcode;
	private String custid;
	private String keyno;
	private String permark;
	private String salespkgcode;
	
	public String getPermark() {
		return permark;
	}
	public void setPermark(String permark) {
		this.permark = permark;
	}
	public String getKeyno() {
		return keyno;
	}
	public void setKeyno(String keyno) {
		this.keyno = keyno;
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getOpcode() {
		return opcode;
	}
	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}
	public Integer getBusType() {
		return busType;
	}
	public void setBusType(Integer busType) {
		this.busType = busType;
	}
	
	public String getSeriesId() {
		return seriesId;
	}
	public void setSeriesId(String seriesId) {
		this.seriesId = seriesId;
	}
	public String getKnowname() {
		return knowname;
	}
	public void setKnowname(String knowname) {
		this.knowname = knowname;
	}
	public String getKnowid() {
		return knowid;
	}
	public void setKnowid(String knowid) {
		this.knowid = knowid;
	}
	public Integer getObjtype() {
		return objtype;
	}
	public void setObjtype(Integer objtype) {
		this.objtype = objtype;
	}
	public boolean isNeeddetail() {
		return needdetail;
	}
	public void setNeeddetail(boolean needdetail) {
		this.needdetail = needdetail;
	}
	public String getSalespkgcode() {
		return salespkgcode;
	}
	public void setSalespkgcode(String salespkgcode) {
		this.salespkgcode = salespkgcode;
	}
	
}
