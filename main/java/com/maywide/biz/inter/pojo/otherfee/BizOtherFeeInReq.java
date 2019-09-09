package com.maywide.biz.inter.pojo.otherfee;

import java.io.Serializable;
import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

@SuppressWarnings("serial")
public class BizOtherFeeInReq extends BaseApiRequest implements Serializable {

	private String custid; // 客户编号
	private String servid; // 用户编号
	private String houseid; // 地址编号

	private String custname;
	private String addr;

	private List<OtherFeeParam> otherFeeList; // 项目对象

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

	public String getHouseid() {
		return houseid;
	}

	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public List<OtherFeeParam> getOtherFeeList() {
		return otherFeeList;
	}

	public void setOtherFeeList(List<OtherFeeParam> otherFeeList) {
		this.otherFeeList = otherFeeList;
	}

}
