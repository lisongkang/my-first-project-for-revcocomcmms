package com.maywide.biz.inter.pojo.chgdev;

import java.util.List;



public class QueFittingInfoResp implements java.io.Serializable {
	
	private List<SysCustDevBO> custdevs;

	public List<SysCustDevBO> getCustdevs() {
		return custdevs;
	}

	public void setCustdevs(List<SysCustDevBO> custdevs) {
		this.custdevs = custdevs;
	}



}
