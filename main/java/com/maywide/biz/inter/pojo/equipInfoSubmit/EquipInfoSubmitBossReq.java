package com.maywide.biz.inter.pojo.equipInfoSubmit;

import java.io.Serializable;
import java.util.List;

public class EquipInfoSubmitBossReq implements Serializable{

	private Long custid;
	
	private String serialno;
	
	private Long servorderid;
	
	private Long custorderid;
	
	private String payway;
	
	private String subpay;
	
	private String acctno;
	
	private String rpay;
	
	private List<EquiInfoSubmitDevInfo> devicelist;

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	public String getRpay() {
		return rpay;
	}

	public void setRpay(String rpay) {
		this.rpay = rpay;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public Long getServorderid() {
		return servorderid;
	}

	public void setServorderid(Long servorderid) {
		this.servorderid = servorderid;
	}

	public Long getCustorderid() {
		return custorderid;
	}

	public void setCustorderid(Long custorderid) {
		this.custorderid = custorderid;
	}

	public String getPayway() {
		return payway;
	}

	public void setPayway(String payway) {
		this.payway = payway;
	}

	public String getSubpay() {
		return subpay;
	}

	public void setSubpay(String subpay) {
		this.subpay = subpay;
	}

	public String getAcctno() {
		return acctno;
	}

	public void setAcctno(String acctno) {
		this.acctno = acctno;
	}

	public List<EquiInfoSubmitDevInfo> getDevicelist() {
		return devicelist;
	}

	public void setDevicelist(List<EquiInfoSubmitDevInfo> devicelist) {
		this.devicelist = devicelist;
	}
	
	
	
}
