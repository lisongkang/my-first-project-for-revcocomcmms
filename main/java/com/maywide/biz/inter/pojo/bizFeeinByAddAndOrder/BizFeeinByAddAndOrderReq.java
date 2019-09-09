package com.maywide.biz.inter.pojo.bizFeeinByAddAndOrder;

import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizFeeinByAddAndOrderReq extends BaseApiRequest {

	private String fees;
	
	private String custid;
	
	private String 	name;
	
	private String whladdr;
	
	private String isorder;
	
	private String buff;
	
	private String iscrtorder;
	
	private String gdnoid;
	
	private String gdno;
	
	private String keyno;
	
	private String chouseid;
	
	private String permark;
	
	
	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}

	private List<BizFeeinByAddAndOrderChildBean> orderparams;

	
	
	public String getBuff() {
		return buff;
	}

	public void setBuff(String buff) {
		this.buff = buff;
	}

	public String getIscrtorder() {
		return iscrtorder;
	}

	public void setIscrtorder(String iscrtorder) {
		this.iscrtorder = iscrtorder;
	}

	public String getGdnoid() {
		return gdnoid;
	}

	public void setGdnoid(String gdnoid) {
		this.gdnoid = gdnoid;
	}

	public String getGdno() {
		return gdno;
	}

	public void setGdno(String gdno) {
		this.gdno = gdno;
	}

	public String getKeyno() {
		return keyno;
	}

	public void setKeyno(String keyno) {
		this.keyno = keyno;
	}

	public String getChouseid() {
		return chouseid;
	}

	public void setChouseid(String chouseid) {
		this.chouseid = chouseid;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWhladdr() {
		return whladdr;
	}

	public void setWhladdr(String whladdr) {
		this.whladdr = whladdr;
	}

	public String getIsorder() {
		return isorder;
	}

	public void setIsorder(String isorder) {
		this.isorder = isorder;
	}

	public List<BizFeeinByAddAndOrderChildBean> getOrderparams() {
		return orderparams;
	}

	public void setOrderparams(List<BizFeeinByAddAndOrderChildBean> orderparams) {
		this.orderparams = orderparams;
	}
	
	
}
