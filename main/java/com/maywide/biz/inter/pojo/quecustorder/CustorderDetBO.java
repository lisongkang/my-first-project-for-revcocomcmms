package com.maywide.biz.inter.pojo.quecustorder;

import java.util.List;

public class CustorderDetBO implements java.io.Serializable {
	private List<ApplyInstallBO> applyInstalls;//申请报装信息
	private List<ApplyProductBO> applyProducts;//申请产品信息
	private List<ApplyBankBO> applyBanks;//申请银行信息
	private List<ApplyTmpopenBO> applyTmpopens;//体验授权信息
	private List<ApplyRefreshBO> applyRefreshs;//刷新授权信息
	private List<ApplyArrearBO> applyArrears;//缴纳欠费信息
	
	public List<ApplyInstallBO> getApplyInstalls() {
		return applyInstalls;
	}
	public void setApplyInstalls(List<ApplyInstallBO> applyInstalls) {
		this.applyInstalls = applyInstalls;
	}
	public List<ApplyProductBO> getApplyProducts() {
		return applyProducts;
	}
	public void setApplyProducts(List<ApplyProductBO> applyProducts) {
		this.applyProducts = applyProducts;
	}
	public List<ApplyBankBO> getApplyBanks() {
		return applyBanks;
	}
	public void setApplyBanks(List<ApplyBankBO> applyBanks) {
		this.applyBanks = applyBanks;
	}
	public List<ApplyTmpopenBO> getApplyTmpopens() {
		return applyTmpopens;
	}
	public void setApplyTmpopens(List<ApplyTmpopenBO> applyTmpopens) {
		this.applyTmpopens = applyTmpopens;
	}
	public List<ApplyRefreshBO> getApplyRefreshs() {
		return applyRefreshs;
	}
	public void setApplyRefreshs(List<ApplyRefreshBO> applyRefreshs) {
		this.applyRefreshs = applyRefreshs;
	}
	public List<ApplyArrearBO> getApplyArrears() {
		return applyArrears;
	}
	public void setApplyArrears(List<ApplyArrearBO> applyArrears) {
		this.applyArrears = applyArrears;
	}
	
	
}
