package com.maywide.biz.inter.pojo.bizfeein;

import java.util.List;

import com.maywide.biz.inter.pojo.bizpreprocess.DeveloperBossBO;
import com.maywide.biz.inter.pojo.bizpreprocess.EjectParamsBossBO;
import com.maywide.biz.inter.pojo.bizpreprocess.OrderParamsBossBO;

public class BizFeeinAndOrderBossReq {

	private String custid;
	
	private String iscrtorder;
	
	private String gdnoid;
	
	private String gdno;
	
	private String keyno;
	
	private String chouseid;
	
	private String quicknum;
	
	private List<EjectParamsBossBO> ejectparams;//退订参数
	
	private List<OrderParamsBossBO> orderparams;//订购参数
	
	private DeveloperBossBO developer;//发展人

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
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

	public List<EjectParamsBossBO> getEjectparams() {
		return ejectparams;
	}

	public void setEjectparams(List<EjectParamsBossBO> ejectparams) {
		this.ejectparams = ejectparams;
	}

	public List<OrderParamsBossBO> getOrderparams() {
		return orderparams;
	}

	public void setOrderparams(List<OrderParamsBossBO> orderparams) {
		this.orderparams = orderparams;
	}

	public DeveloperBossBO getDeveloper() {
		return developer;
	}

	public void setDeveloper(DeveloperBossBO developer) {
		this.developer = developer;
	}

	public String getQuicknum() {
		return quicknum;
	}

	public void setQuicknum(String quicknum) {
		this.quicknum = quicknum;
	}

	
	
}
