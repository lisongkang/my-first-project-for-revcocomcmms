package com.maywide.test.pojo;

import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;
import com.maywide.biz.inter.pojo.bizpreprocess.EjectParamsInterBO;
import com.maywide.biz.inter.pojo.bizpreprocess.OrderParamsInterBO;

public class BizPreprocessInterReqTest implements java.io.Serializable {

	// boss接口需要的参数
	private String custid;// 客户编号
	List<EjectParamsInterBO> ejectparams;// 订购参数
	List<OrderParamsInterBO> orderparams;// 订购参数
	private String buff;// 备用字段
	private String iscrtorder;// 是否生成订单
	private String gdnoid;// 广电号ID
	private String gdno;// 广电号

	// 订单表所需参数
	private String areaid;// 业务区
	// private String custid;//客户id
	private String name;// 客户姓名
	private String patchid;// 片区id
	private String whladdr;// 地址
	private String descrip;// 业务说明

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

}
