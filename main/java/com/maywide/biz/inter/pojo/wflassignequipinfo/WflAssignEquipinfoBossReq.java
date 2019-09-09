package com.maywide.biz.inter.pojo.wflassignequipinfo;

import java.io.Serializable;
import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class WflAssignEquipinfoBossReq implements Serializable {
	private String operseq;
	private String custid;
	private String custName;
	private String address;
	private List<WflAssignEquipinfoChildInterReq> devicelist;
	
	public String getOperseq() {
		return operseq;
	}
	public void setOperseq(String operseq) {
		this.operseq = operseq;
	}
	
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<WflAssignEquipinfoChildInterReq> getDevicelist() {
		return devicelist;
	}
	public void setDevicelist(List<WflAssignEquipinfoChildInterReq> devicelist) {
		this.devicelist = devicelist;
	}
}
