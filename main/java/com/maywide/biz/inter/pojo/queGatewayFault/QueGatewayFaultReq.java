package com.maywide.biz.inter.pojo.queGatewayFault;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueGatewayFaultReq extends BaseApiRequest {
	private String servid;

	private String custid;

	private String devno;

	
	private String city;
	
	private String mac;
	
	private String sn;
	
	private String starttime;
	
	private String endtime;

	public String getServid() {
		return servid;
	}

	public void setServid(String servid) {
		this.servid = servid;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getDevno() {
		return devno;
	}

	public void setDevno(String devno) {
		this.devno = devno;
	}

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
