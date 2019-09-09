package com.maywide.biz.inter.pojo.savecustmarketing;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class SaveCustMarketingInterReq extends BaseApiRequest implements java.io.Serializable {
	private String custid;//客户ID
	private String knowid;//营销标识
	private String patchid;//片区id
	private String pri;//优先级
	private String name;//客户名称
	private String linkphone;//联系电话/手机
	private String houseid;//住宅地址ID
	private String whladdr;//住宅地址
	private String areaid;//业务区
	
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getKnowid() {
		return knowid;
	}
	public void setKnowid(String knowid) {
		this.knowid = knowid;
	}
	public String getPatchid() {
		return patchid;
	}
	public void setPatchid(String patchid) {
		this.patchid = patchid;
	}
	public String getPri() {
		return pri;
	}
	public void setPri(String pri) {
		this.pri = pri;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLinkphone() {
		return linkphone;
	}
	public void setLinkphone(String linkphone) {
		this.linkphone = linkphone;
	}
	public String getHouseid() {
		return houseid;
	}
	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}
	public String getWhladdr() {
		return whladdr;
	}
	public void setWhladdr(String whladdr) {
		this.whladdr = whladdr;
	}
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}


	
}
