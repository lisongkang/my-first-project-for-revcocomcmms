package com.maywide.biz.inter.pojo.sycnChlInstall;

import com.maywide.biz.inter.pojo.bizpreprocess.EjectParamsBossBO;

import java.util.List;

public class SyncChlInstallReq {
	
	private Long custid;
	
	private String city;
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	private String iscrtorder;
	
	private String opcode;
	
	private String optime;
	
	private Long oprdep;
	
	private Long operator;
	
	private Long areaid;
	
	private String describe;
	
	private String systemid;
	
	private String memo;
	
	private List<ChlInstallParam> installparams;
	
	private List<SyncPrdParam> prdparams;
	
	private List<SyncInstallBankParam> bankparams;
	
	private SyncInstallAddr addrparam = new SyncInstallAddr();
	
	private List<SyncOnceFeeParam> oncefeeparams;
	
	private List<InstallUserParam> userparams;

	private String bizfeein;//是否缴清欠费 默认为Y

	private List<EjectParamsBossBO> ejectparams; //退订参数

	public String getBizfeein() {
		return bizfeein;
	}

	public void setBizfeein(String bizfeein) {
		this.bizfeein = bizfeein;
	}

	public List<EjectParamsBossBO> getEjectparams() {
		return ejectparams;
	}

	public void setEjectparams(List<EjectParamsBossBO> ejectparams) {
		this.ejectparams = ejectparams;
	}

	//	private List<ExdevList> exdevList;
//	
//	private List<RecycleFitList> recycleFitList;
//	
//	private String fitkind;
//	
//	private String fitpid;
//	
//	private String fitattr;
//	
//	private String fituseprop;
	
	

//	public List<ExdevList> getExdevList() {
//		return exdevList;
//	}
//
//	public void setExdevList(List<ExdevList> exdevList) {
//		this.exdevList = exdevList;
//	}
//
//	public List<RecycleFitList> getRecycleFitList() {
//		return recycleFitList;
//	}
//
//	public void setRecycleFitList(List<RecycleFitList> recycleFitList) {
//		this.recycleFitList = recycleFitList;
//	}
//
//	public String getFitkind() {
//		return fitkind;
//	}
//
//	public void setFitkind(String fitkind) {
//		this.fitkind = fitkind;
//	}
//
//	public String getFitpid() {
//		return fitpid;
//	}
//
//	public void setFitpid(String fitpid) {
//		this.fitpid = fitpid;
//	}
//
//	public String getFitattr() {
//		return fitattr;
//	}
//
//	public void setFitattr(String fitattr) {
//		this.fitattr = fitattr;
//	}
//
//	public String getFituseprop() {
//		return fituseprop;
//	}
//
//	public void setFituseprop(String fituseprop) {
//		this.fituseprop = fituseprop;
//	}

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	public String getIscrtorder() {
		return iscrtorder;
	}

	public void setIscrtorder(String iscrtorder) {
		this.iscrtorder = iscrtorder;
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

	public String getSystemid() {
		return systemid;
	}

	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public List<ChlInstallParam> getInstallparams() {
		return installparams;
	}

	public void setInstallparams(List<ChlInstallParam> installparams) {
		this.installparams = installparams;
	}

	public List<SyncPrdParam> getPrdparams() {
		return prdparams;
	}

	public void setPrdparams(List<SyncPrdParam> prdparams) {
		this.prdparams = prdparams;
	}

	public List<SyncInstallBankParam> getBankparams() {
		return bankparams;
	}

	public void setBankparams(List<SyncInstallBankParam> bankparams) {
		this.bankparams = bankparams;
	}



	public SyncInstallAddr getAddrparam() {
		return addrparam;
	}

	public void setAddrparam(SyncInstallAddr addrparam) {
		this.addrparam = addrparam;
	}

	public List<SyncOnceFeeParam> getOncefeeparams() {
		return oncefeeparams;
	}

	public void setOncefeeparams(List<SyncOnceFeeParam> oncefeeparams) {
		this.oncefeeparams = oncefeeparams;
	}

	public List<InstallUserParam> getUserparams() {
		return userparams;
	}

	public void setUserparams(List<InstallUserParam> userparams) {
		this.userparams = userparams;
	}
	
	

}
