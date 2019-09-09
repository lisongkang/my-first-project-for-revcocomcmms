package com.maywide.biz.dl.pojo.asyncOrder;

import java.util.List;

import com.maywide.biz.inter.pojo.sycnChlInstall.SyncInstallBankParam;
import com.maywide.biz.inter.pojo.sycnChlInstall.SyncOnceFeeParam;

public class BizAsyncOrder2BossReq {

	private Long custid;
	
	private Long orderid;
	
	private String ordercode;
	
	private String orderstatus;
	
	private String synctime;
	
	private String opcode;
	
	private String optime;
	
	private Long oprdep;
	
	private Long operator;
	
	private Long areaid;
	
	private String describe;
	
	private String city;
	
	private String systemid;
	
	private CommonParam publicparam;
	
	private List<InstallParams> installparams;
	
	private List<PrdParam> prdparams;
	
	private List<SyncInstallBankParam> bankparams;
	
	private List<SyncOnceFeeParam> oncefeeparams;
	
	private List<UserParam> userparams;

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	public String getOrdercode() {
		return ordercode;
	}

	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}

	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getSynctime() {
		return synctime;
	}

	public void setSynctime(String synctime) {
		this.synctime = synctime;
	}

	public String getOpcode() {
		return opcode;
	}

	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}

	public String getOptime() {
		return optime;
	}

	public void setOptime(String optime) {
		this.optime = optime;
	}

	public Long getOprdep() {
		return oprdep;
	}

	public void setOprdep(Long oprdep) {
		this.oprdep = oprdep;
	}

	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	public Long getAreaid() {
		return areaid;
	}

	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSystemid() {
		return systemid;
	}

	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}


	public CommonParam getPublicparam() {
		return publicparam;
	}

	public void setPublicparam(CommonParam publicparam) {
		this.publicparam = publicparam;
	}

	public List<InstallParams> getInstallparams() {
		return installparams;
	}

	public void setInstallparams(List<InstallParams> installparams) {
		this.installparams = installparams;
	}

	public List<PrdParam> getPrdparams() {
		return prdparams;
	}

	public void setPrdparams(List<PrdParam> prdparams) {
		this.prdparams = prdparams;
	}

	public List<SyncInstallBankParam> getBankparams() {
		return bankparams;
	}

	public void setBankparams(List<SyncInstallBankParam> bankparams) {
		this.bankparams = bankparams;
	}

	public List<SyncOnceFeeParam> getOncefeeparams() {
		return oncefeeparams;
	}

	public void setOncefeeparams(List<SyncOnceFeeParam> oncefeeparams) {
		this.oncefeeparams = oncefeeparams;
	}

	public List<UserParam> getUserparams() {
		return userparams;
	}

	public void setUserparams(List<UserParam> userparams) {
		this.userparams = userparams;
	}
	
}
