package com.maywide.biz.inter.pojo.equipInfoSubmit;

import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class EquipInfoSubmitReq extends BaseApiRequest {

	private Long recid;
	
	private Long servorderid;
	
	private String rpay;
	
	private String payway;
	
	private String subpay;
	
	private String acctno;
	
	private String payreqid;
	
	private String salespkgname;
	
	public String getPayreqid() {
		return payreqid;
	}

	public void setPayreqid(String payreqid) {
		this.payreqid = payreqid;
	}

	private List<EquiInfoSubmitDevInfo> devicelist;

	public Long getRecid() {
		return recid;
	}

	public void setRecid(Long recid) {
		this.recid = recid;
	}

	public List<EquiInfoSubmitDevInfo> getDevicelist() {
		return devicelist;
	}

	public void setDevicelist(List<EquiInfoSubmitDevInfo> devicelist) {
		this.devicelist = devicelist;
	}

	public Long getServorderid() {
		return servorderid;
	}

	public void setServorderid(Long servorderid) {
		this.servorderid = servorderid;
	}

	public String getRpay() {
		return rpay;
	}

	public void setRpay(String rpay) {
		this.rpay = rpay;
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

	public String getSalespkgname() {
		return salespkgname;
	}

	public void setSalespkgname(String salespkgname) {
		this.salespkgname = salespkgname;
	}
	
	
	
}
