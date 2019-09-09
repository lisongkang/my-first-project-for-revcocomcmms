package com.maywide.biz.market.pojo;

import java.util.ArrayList;

import com.maywide.biz.system.entity.PrvSysparam;

public class InstallBasedataResp {
	private ArrayList<PrvSysparam> cardTypes = new ArrayList<PrvSysparam>();
	private ArrayList<PrvSysparam> areaIds = new ArrayList<PrvSysparam>();
	private ArrayList<PrvSysparam> banks = new ArrayList<PrvSysparam>();
	private ArrayList<PrvSysparam> acctKinds = new ArrayList<PrvSysparam>();
	private ArrayList<PrvSysparam> acctTypes = new ArrayList<PrvSysparam>();
	
	public ArrayList<PrvSysparam> getCardTypes() {
		return cardTypes;
	}
	public void setCardTypes(ArrayList<PrvSysparam> cardTypes) {
		this.cardTypes = cardTypes;
	}
	public ArrayList<PrvSysparam> getAreaIds() {
		return areaIds;
	}
	public void setAreaIds(ArrayList<PrvSysparam> areaIds) {
		this.areaIds = areaIds;
	}
	public ArrayList<PrvSysparam> getBanks() {
		return banks;
	}
	public void setBanks(ArrayList<PrvSysparam> banks) {
		this.banks = banks;
	}
	public ArrayList<PrvSysparam> getAcctKinds() {
		return acctKinds;
	}
	public void setAcctKinds(ArrayList<PrvSysparam> acctKinds) {
		this.acctKinds = acctKinds;
	}
	public ArrayList<PrvSysparam> getAcctTypes() {
		return acctTypes;
	}
	public void setAcctTypes(ArrayList<PrvSysparam> acctTypes) {
		this.acctTypes = acctTypes;
	}
}
