package com.maywide.biz.inter.pojo.syncApplyInstall;

import com.maywide.biz.core.pojo.api.BaseApiRequest;
import com.maywide.biz.inter.pojo.applyinstall.KnowProducts;
import com.maywide.biz.inter.pojo.bizpreprocess.EjectParamsBossBO;
import com.maywide.biz.inter.pojo.sycnChlInstall.*;

import java.util.List;

public class SyncApplyInstallReq extends BaseApiRequest {

	private Long custid;
	
	private String custname;

	private String iscrtorder;

	private String opcode;

	private String optime;

	private String describe;
	
	private String memo;

	private String systemid;
	
	private String houseid;
	
	private String patchid;
	
	private String whladdr;
	
	private String knowids;
	
	private String counts;
	
	private String units;
	
	
	
	private String ispostpones;

	private String stimes;
	
	private String verifyphone;
	
//	private String fitkind;
//	
//	private String fitpid;
//	
//	private String fitattr;
//	
//	private String fituseprop;
	
	
	private String mindates;
	
//	private List<ExdevList> exdevList;
//	
//	private List<RecycleFitList> recycleFitList;
	
	private List<ChlInstallParam> installparams;

	private List<KnowProducts> selectProducts;

	private List<SyncInstallBankParam> bankparams;

	private List<SyncInstallAddr> addrs;

	private List<SyncOnceFeeParam> oncefeeparams;

	private List<InstallUserParam> userparams;
	
	
	private String bizfeein;//是否缴清欠费 默认为Y

	private List<EjectParamsBossBO> ejectparams; //退订参数

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

	public String getMindates() {
		return mindates;
	}

	public void setMindates(String mindates) {
		this.mindates = mindates;
	}



	public String getVerifyphone() {
		return verifyphone;
	}

	public void setVerifyphone(String verifyphone) {
		this.verifyphone = verifyphone;
	}

	public String getWhladdr() {
		return whladdr;
	}

	public void setWhladdr(String whladdr) {
		this.whladdr = whladdr;
	}

	public String getKnowids() {
		return knowids;
	}

	public void setKnowids(String knowids) {
		this.knowids = knowids;
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public String getHouseid() {
		return houseid;
	}

	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}

	public String getPatchid() {
		return patchid;
	}

	public void setPatchid(String patchid) {
		this.patchid = patchid;
	}

	

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

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getSystemid() {
		return systemid;
	}

	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}

	public List<ChlInstallParam> getInstallparams() {
		return installparams;
	}

	public void setInstallparams(List<ChlInstallParam> installparams) {
		this.installparams = installparams;
	}

	
	public List<SyncInstallBankParam> getBankparams() {
		return bankparams;
	}

	public String getCounts() {
		return counts;
	}

	public void setCounts(String counts) {
		this.counts = counts;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public void setBankparams(List<SyncInstallBankParam> bankparams) {
		this.bankparams = bankparams;
	}

	public List<SyncInstallAddr> getAddrs() {
		return addrs;
	}

	public void setAddrs(List<SyncInstallAddr> addrs) {
		this.addrs = addrs;
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

	public List<KnowProducts> getSelectProducts() {
		return selectProducts;
	}

	public void setSelectProducts(List<KnowProducts> selectProducts) {
		this.selectProducts = selectProducts;
	}

	public String getIspostpones() {
		return ispostpones;
	}

	public void setIspostpones(String ispostpones) {
		this.ispostpones = ispostpones;
	}

	public String getStimes() {
		return stimes;
	}

	public void setStimes(String stimes) {
		this.stimes = stimes;
	}

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
}
