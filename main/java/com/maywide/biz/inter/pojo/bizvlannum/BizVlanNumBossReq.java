package com.maywide.biz.inter.pojo.bizvlannum;

public class BizVlanNumBossReq {

	private String custId;// 客户ID
	private String servId;// 用户ID
	private String vlanNum;// VLAN号

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getServId() {
		return servId;
	}

	public void setServId(String servId) {
		this.servId = servId;
	}

	public String getVlanNum() {
		return vlanNum;
	}

	public void setVlanNum(String vlanNum) {
		this.vlanNum = vlanNum;
	}

	public BizVlanNumBossReq() {
	}

	public BizVlanNumBossReq(BizVlanNumReq req) {
		custId = req.getCustid();
		servId = req.getServid();
		vlanNum = req.getVlanNum();
	}

}
