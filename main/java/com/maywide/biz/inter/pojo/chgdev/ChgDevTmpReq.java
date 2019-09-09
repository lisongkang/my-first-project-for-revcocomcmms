package com.maywide.biz.inter.pojo.chgdev;

import java.util.ArrayList;
import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class ChgDevTmpReq extends BaseApiRequest implements java.io.Serializable {
	private List<FittingInfoReq> fittingList = new ArrayList() ;
	private CustChangeDevBO  bo = new CustChangeDevBO();
	// 订单表所需参数
	private String areaid;// 业务区
	private String custid;//客户id
	private String name;// 客户姓名
	private String houseid;// 地址id
	private String patchid;// 片区id
	private String whladdr;// 地址
	private String descrip;// 业务说明

	private String buff;// 备用字段
	private String iscrtorder;// 是否生成订单
	private String gdnoid;// 广电号ID
	private String gdno;// 广电号
	
	private String verifyphone;//接收验证码的手机号
	
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
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
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
	public String getHouseid() {
		return houseid;
	}
	public void setHouseid(String houseid) {
		this.houseid = houseid;
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
	public List<FittingInfoReq> getFittingList() {
		return fittingList;
	}
	public void setFittingList(List<FittingInfoReq> fittingList) {
		this.fittingList = fittingList;
	}
	public CustChangeDevBO getBo() {
		return bo;
	}
	public void setBo(CustChangeDevBO bo) {
		this.bo = bo;
	}
	public String getVerifyphone() {
		return verifyphone;
	}
	public void setVerifyphone(String verifyphone) {
		this.verifyphone = verifyphone;
	}
	
}
