package com.maywide.biz.inter.pojo.sycnChlInstall;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.maywide.biz.inter.pojo.applyinstall.ExdevList;
import com.maywide.biz.inter.pojo.applyinstall.RecycleFitList;

public class ChlInstallParam {
	
	private Long houseid;
	
	private String addr;
	
	private String patchid;
	
	private String name;
	
	private String cardtype;
	
	private String cardno;
	
	private String linkman;
	
	private String linkphone;
	
	private String mobile;
	
	private String permark;
	
	private String omode;
	
	private String feekind;
	
	private String servtype = "0";
	
	private String pservid;
	
	private String bankid;
	
	private String oservid;
	
	private String ologicdevno;

	private String devback;
	
	private String stbback;
	
	private String predate;
	
	private String logicdevno;
	
	private String stbno;
	
	private String smno;
	
	private String percomb;
	
	private String smnouseprop;
	
	private String stbuseprop;
	
	private String backdept;
	
	private String memo;
	
	private String payway;
	
	private String cmno;   //大连开户使用,EOC设备编号
	
	private String cmuseprop; //大连开户使用,EOC设备来源
	
	private String qtypeno;		//光机编号
	
	private String qtypeprop;	//光机来源
	
	
	
	
	private String fitkind;
	
	private String fitpid;
	
	private String fitattr;
	
	private String fituseprop;
	
	private List<ExdevList> exdevList;
	
	private List<RecycleFitList> recycleFitList;

	public Long getHouseid() {
		return houseid;
	}

	public void setHouseid(Long houseid) {
		this.houseid = houseid;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPatchid() {
		return patchid;
	}

	public void setPatchid(String patchid) {
		this.patchid = patchid;
	}

	public String getName() {
		return name;
	}
	

	public void setName(String name) {
		this.name = name;
	}

	public String getCardtype() {
		return cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getLinkphone() {
		return linkphone;
	}

	public void setLinkphone(String linkphone) {
		this.linkphone = linkphone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}

	public String getOmode() {
		return omode;
	}

	public void setOmode(String omode) {
		this.omode = omode;
	}

	public String getFeekind() {
		return feekind;
	}

	public void setFeekind(String feekind) {
		this.feekind = feekind;
	}

	public String getServtype() {
		return servtype;
	}

	public void setServtype(String servtype) {
		this.servtype = servtype;
	}

	public String getPservid() {
		return pservid;
	}

	public void setPservid(String pservid) {
		this.pservid = pservid;
	}

	public String getBankid() {
		return bankid;
	}

	public void setBankid(String bankid) {
		this.bankid = bankid;
	}

	public String getOservid() {
		return oservid;
	}

	public void setOservid(String oservid) {
		this.oservid = oservid;
	}

	public String getOlogicdevno() {
		return ologicdevno;
	}

	public void setOlogicdevno(String ologicdevno) {
		this.ologicdevno = ologicdevno;
	}

	public String getDevback() {
		return devback;
	}

	public void setDevback(String devback) {
		this.devback = devback;
	}

	public String getStbback() {
		return stbback;
	}

	public void setStbback(String stbback) {
		this.stbback = stbback;
	}

	public String getPredate() {
		return predate;
	}

	public void setPredate(String predate) {
		this.predate = predate;
	}

	public String getLogicdevno() {
		return logicdevno;
	}

	public void setLogicdevno(String logicdevno) {
		this.logicdevno = logicdevno;
	}

	public String getStbno() {
		return stbno;
	}

	public void setStbno(String stbno) {
		this.stbno = stbno;
	}

	public String getSmno() {
		return smno;
	}

	public void setSmno(String smno) {
		this.smno = smno;
	}

	public String getPercomb() {
		return percomb;
	}

	public void setPercomb(String percomb) {
		this.percomb = percomb;
	}


	public String getSmnouseprop() {
		return smnouseprop;
	}

	public void setSmnouseprop(String smnouseprop) {
		this.smnouseprop = smnouseprop;
	}


	public String getStbuseprop() {
		return stbuseprop;
	}

	public void setStbuseprop(String stbuseprop) {
		this.stbuseprop = stbuseprop;
	}

	public String getBackdept() {
		return backdept;
	}

	public void setBackdept(String backdept) {
		this.backdept = backdept;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPayway() {
		return payway;
	}

	public void setPayway(String payway) {
		this.payway = payway;
	}

	public String getCmno() {
		return cmno;
	}

	public void setCmno(String cmno) {
		this.cmno = cmno;
	}

	public String getCmuseprop() {
		return cmuseprop;
	}

	public void setCmuseprop(String cmuseprop) {
		this.cmuseprop = cmuseprop;
	}

	public String getQtypeno() {
		return qtypeno;
	}

	public void setQtypeno(String qtypeno) {
		this.qtypeno = qtypeno;
	}

	public String getQtypeprop() {
		return qtypeprop;
	}

	public void setQtypeprop(String qtypeprop) {
		this.qtypeprop = qtypeprop;
	}

	public String getFitkind() {
		return fitkind;
	}

	public void setFitkind(String fitkind) {
		this.fitkind = fitkind;
	}

	public String getFitpid() {
		return fitpid;
	}

	public void setFitpid(String fitpid) {
		this.fitpid = fitpid;
	}

	public String getFitattr() {
		return fitattr;
	}

	public void setFitattr(String fitattr) {
		this.fitattr = fitattr;
	}

	public String getFituseprop() {
		return fituseprop;
	}

	public void setFituseprop(String fituseprop) {
		this.fituseprop = fituseprop;
	}

	public List<ExdevList> getExdevList() {
		return exdevList;
	}

	public void setExdevList(List<ExdevList> exdevList) {
		this.exdevList = exdevList;
	}

	public List<RecycleFitList> getRecycleFitList() {
		return recycleFitList;
	}

	public void setRecycleFitList(List<RecycleFitList> recycleFitList) {
		this.recycleFitList = recycleFitList;
	}
	
	
	
	
}
