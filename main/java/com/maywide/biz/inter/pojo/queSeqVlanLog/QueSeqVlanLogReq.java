package com.maywide.biz.inter.pojo.queSeqVlanLog;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueSeqVlanLogReq extends BaseApiRequest{
	private String city;
	
	private String mac;
	
	private String sn;
	
	private String starttime;
	
	private String endtime;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	
}
