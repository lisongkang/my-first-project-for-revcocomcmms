package com.maywide.biz.dl.pojo.queDevSalesOper;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueDevSalesOperReq extends BaseApiRequest {

	private String stime;
	
	private String etime;
	
	private String payways;
	
	private String kinds;
	
	private String subkinds;
	
	private String devpids;

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}

	public String getPayways() {
		return payways;
	}

	public void setPayways(String payways) {
		this.payways = payways;
	}

	public String getKinds() {
		return kinds;
	}

	public void setKinds(String kinds) {
		this.kinds = kinds;
	}

	public String getSubkinds() {
		return subkinds;
	}

	public void setSubkinds(String subkinds) {
		this.subkinds = subkinds;
	}

	public String getDevpids() {
		return devpids;
	}

	public void setDevpids(String devpids) {
		this.devpids = devpids;
	}
	
	
	
}
