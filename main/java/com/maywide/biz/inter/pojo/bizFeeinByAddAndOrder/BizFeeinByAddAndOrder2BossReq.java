package com.maywide.biz.inter.pojo.bizFeeinByAddAndOrder;

import java.util.List;

public class BizFeeinByAddAndOrder2BossReq {

	private String custid;
	
	private String buff;
	
	private String iscrtorder;
	
	private String gdnoid;
	
	private String gdno;
	
	private String keyno;
	
	private String chouseid;
	
	private List<BizFeeinByAddAndOrderChildBean> orderparams;

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

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

	public List<BizFeeinByAddAndOrderChildBean> getOrderparams() {
		return orderparams;
	}

	public void setOrderparams(List<BizFeeinByAddAndOrderChildBean> orderparams) {
		this.orderparams = orderparams;
	}
	
	
	
}
