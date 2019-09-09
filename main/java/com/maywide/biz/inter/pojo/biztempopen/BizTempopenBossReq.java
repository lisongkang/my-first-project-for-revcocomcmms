package com.maywide.biz.inter.pojo.biztempopen;

public class BizTempopenBossReq implements java.io.Serializable {
	private String keyno;//设备号
	private String pcode;//产品编码
	private String planid;//点通方案id
	
	public String getKeyno() {
		return keyno;
	}
	public void setKeyno(String keyno) {
		this.keyno = keyno;
	}
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	public String getPlanid() {
		return planid;
	}
	public void setPlanid(String planid) {
		this.planid = planid;
	}

}
