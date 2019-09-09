package com.maywide.biz.inter.pojo.bizpreprocess;

import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizPreprocessBossReq implements java.io.Serializable {
	
	private String custid;//客户编号
	private List<EjectParamsBossBO> ejectparams;//退订参数
	private List<OrderParamsBossBO> orderparams;//订购参数
	private String buff;//备用字段
	private String iscrtorder;//是否生成订单
	private String gdnoid;//广电号ID	
	private String gdno;//广电号	
	private String divide;
	private DeveloperBossBO developer;//发展人

	//组网业务池新增业务
	private String preacceptserialno;//预受理流水号

	public String getPreacceptserialno() {
		return preacceptserialno;
	}

	public void setPreacceptserialno(String preacceptserialno) {
		this.preacceptserialno = preacceptserialno;
	}

	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public List<OrderParamsBossBO> getOrderparams() {
		return orderparams;
	}
	public void setOrderparams(List<OrderParamsBossBO> orderparams) {
		this.orderparams = orderparams;
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
	public List<EjectParamsBossBO> getEjectparams() {
		return ejectparams;
	}
	public void setEjectparams(List<EjectParamsBossBO> ejectparams) {
		this.ejectparams = ejectparams;
	}
	public DeveloperBossBO getDeveloper() {
		return developer;
	}
	public void setDeveloper(DeveloperBossBO developer) {
		this.developer = developer;
	}
	public String getDivide() {
		return divide;
	}
	public void setDivide(String divide) {
		this.divide = divide;
	}
	

}
