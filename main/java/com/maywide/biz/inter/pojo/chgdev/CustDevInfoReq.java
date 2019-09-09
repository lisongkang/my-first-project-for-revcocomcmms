package com.maywide.biz.inter.pojo.chgdev;

import java.util.LinkedHashSet;
import java.util.Set;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class CustDevInfoReq extends BaseApiRequest implements java.io.Serializable  {
	
	private Long custid;
	private Long areaid;
	private String kind;
	private String subkind;
	private String devno;
	private String status;
	private String bizcode = BizConstant.BizOpcode.BIZ_DEV_CHANGE;
	public Long getCustid() {
		return custid;
	}
	public void setCustid(Long custid) {
		this.custid = custid;
	}
	public Long getAreaid() {
		return areaid;
	}
	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getSubkind() {
		return subkind;
	}
	public void setSubkind(String subkind) {
		this.subkind = subkind;
	}
	public String getDevno() {
		return devno;
	}
	public void setDevno(String devno) {
		this.devno = devno;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBizcode() {
		return bizcode;
	}
	public void setBizcode(String bizcode) {
		this.bizcode = bizcode;
	}

	
	
	
}
