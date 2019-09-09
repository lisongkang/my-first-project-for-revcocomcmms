package com.maywide.biz.inter.pojo.bizpreprocess;

import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizPreprocessInterReq extends BaseApiRequest implements
		java.io.Serializable {

	// boss接口需要的参数
	private String custid;// 客户编号
	private List<EjectParamsInterBO> ejectparams;// 退订参数
	private List<OrderParamsInterBO> orderparams;// 订购参数
	private String buff;// 备用字段
	private String iscrtorder;// 是否生成订单
	private String gdnoid;// 广电号ID
	private String gdno;// 广电号

	//组网业务池新增业务
	private String preacceptserialno;//预受理流水号

	// 订单表所需参数
	private String areaid;// 业务区
	// private String custid;//客户id
	private String name;// 客户姓名
	private String houseid;// 地址id
	private String patchid;// 片区id
	private String whladdr;// 地址
	private String descrip;// 业务说明
	private String verifyphone;//接收验证码的手机号
	private String divide;

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

	public List<OrderParamsInterBO> getOrderparams() {
		return orderparams;
	}

	public void setOrderparams(List<OrderParamsInterBO> orderparams) {
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

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPatchid() {
		return patchid;
	}

	public void setPatchid(String patchid) {
		this.patchid = patchid;
	}

	public String getWhladdr() {
		return whladdr;
	}

	public void setWhladdr(String whladdr) {
		this.whladdr = whladdr;
	}

	public String getDescrip() {
		return descrip;
	}

	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}

	public List<EjectParamsInterBO> getEjectparams() {
		return ejectparams;
	}

	public void setEjectparams(List<EjectParamsInterBO> ejectparams) {
		this.ejectparams = ejectparams;
	}

    public String getHouseid() {
        return houseid;
    }

    public void setHouseid(String houseid) {
        this.houseid = houseid;
    }

	public String getVerifyphone() {
		return verifyphone;
	}

	public void setVerifyphone(String verifyphone) {
		this.verifyphone = verifyphone;
	}

	public String getDivide() {
		return divide;
	}

	public void setDivide(String divide) {
		this.divide = divide;
	}
	
	
}
