package com.maywide.biz.inter.pojo.queCommCust;

import java.util.List;

public class CustlistBean {

	private String custid;
	
	private String custname;
	
	private String contactphone;
	
	private String owetotal;
	
	private String contactaddr;
	
	private List<DeviceListBean> devicelist;
	
	private String markno;

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public String getContactphone() {
		return contactphone;
	}

	public void setContactphone(String contactphone) {
		this.contactphone = contactphone;
	}

	public String getOwetotal() {
		return owetotal;
	}

	public void setOwetotal(String owetotal) {
		this.owetotal = owetotal;
	}

	public String getContactaddr() {
		return contactaddr;
	}

	public void setContactaddr(String contactaddr) {
		this.contactaddr = contactaddr;
	}

	public List<DeviceListBean> getDevicelist() {
		return devicelist;
	}

	public void setDevicelist(List<DeviceListBean> devicelist) {
		this.devicelist = devicelist;
	}

	public String getMarkno() {
		return markno;
	}

	public void setMarkno(String markno) {
		this.markno = markno;
	}
	
	
	
}
