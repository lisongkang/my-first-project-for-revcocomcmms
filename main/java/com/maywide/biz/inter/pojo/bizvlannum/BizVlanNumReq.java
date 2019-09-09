package com.maywide.biz.inter.pojo.bizvlannum;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizVlanNumReq extends BaseApiRequest {

	private static final long serialVersionUID = 1L;

	private String custid;// 客户ID
	private String servid;// 用户ID
	private String vlanNum;// VLAN号
	private String name;
	private String addr;

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getServid() {
		return servid;
	}

	public void setServid(String servid) {
		this.servid = servid;
	}

	public String getVlanNum() {
		return vlanNum;
	}

	public void setVlanNum(String vlanNum) {
		this.vlanNum = vlanNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

}
