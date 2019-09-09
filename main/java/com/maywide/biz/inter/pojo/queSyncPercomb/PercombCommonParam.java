package com.maywide.biz.inter.pojo.queSyncPercomb;

import java.util.List;

import com.maywide.biz.system.entity.PrvSysparam;

public class PercombCommonParam {

	private List<PrvSysparam> feekindList;
	
	private List<PrvSysparam> paywayList;
	
	private List<PrvSysparam> installtypeList;

	public List<PrvSysparam> getFeekindList() {
		return feekindList;
	}

	public void setFeekindList(List<PrvSysparam> feekindList) {
		this.feekindList = feekindList;
	}

	public List<PrvSysparam> getPaywayList() {
		return paywayList;
	}

	public void setPaywayList(List<PrvSysparam> paywayList) {
		this.paywayList = paywayList;
	}

	public List<PrvSysparam> getInstalltypeList() {
		return installtypeList;
	}

	public void setInstalltypeList(List<PrvSysparam> installtypeList) {
		this.installtypeList = installtypeList;
	}
	
	
	
	
}
