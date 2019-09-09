package com.maywide.biz.inter.pojo.bizfeein;

import java.io.Serializable;
import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;
import com.maywide.biz.inter.pojo.bizpreprocess.EjectParamsInterBO;
import com.maywide.biz.inter.pojo.bizpreprocess.OrderParamsBossBO;
import com.maywide.biz.inter.pojo.bizpreprocess.OrderParamsInterBO;

public class BizFeeinInterReq extends BaseApiRequest implements Serializable {

	//boss接口所需参数
	private String fees;
	private String keyno;
	private String permark;
	private String isorder;
	private String gdnoid;
	private String gdno;
	private String chouseid;
	private String quicknum;
	private List<OrderParamsBossBO> orderparams;// 订购参数
	
	//订单表所需参数
	private String houseid;
	private String patchid;
	private String custid;
	private String name;
	private String whladdr;
	private String areaid;
	private String descrip;
	
	private String verifyphone;//接收验证码的手机号

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

	public String getKeyno() {
		return keyno;
	}

	public void setKeyno(String keyno) {
		this.keyno = keyno;
	}

	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}

	public String getIsorder() {
		return isorder;
	}

	public void setIsorder(String isorder) {
		this.isorder = isorder;
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

	public String getPatchid() {
		return patchid;
	}

	public void setPatchid(String patchid) {
		this.patchid = patchid;
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

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getDescrip() {
		return descrip;
	}

	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}

    public String getHouseid() {
        return houseid;
    }

    public void setHouseid(String houseid) {
        this.houseid = houseid;
    }


	public List<OrderParamsBossBO> getOrderparams() {
		return orderparams;
	}

	public void setOrderparams(List<OrderParamsBossBO> orderparams) {
		this.orderparams = orderparams;
	}

	public String getVerifyphone() {
		return verifyphone;
	}

	public void setVerifyphone(String verifyphone) {
		this.verifyphone = verifyphone;
	}

	public String getQuicknum() {
		return quicknum;
	}

	public void setQuicknum(String quicknum) {
		this.quicknum = quicknum;
	}
    
}
