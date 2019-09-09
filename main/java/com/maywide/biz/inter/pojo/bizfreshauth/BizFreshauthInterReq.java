package com.maywide.biz.inter.pojo.bizfreshauth;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizFreshauthInterReq  extends BaseApiRequest implements java.io.Serializable {
	//boss接口所需参数
	private String keyno;//设备号
	private String servid;//用户id
	private String permark;//业务类型
	
	//订单表所需参数
	private String areaid;//业务区
	private String custid;//客户id
	private String name;//客户姓名
	private String houseid;//地址id
	private String patchid;//片区id
	private String whladdr;//地址
	private String descrip;//业务说明
	
	//刷新授权表所需参数
	//private String servid;//用户id
	//private String logicdevno;//用户设备号
	private String logicstbno;//绑定设备号
	private String opkind;//处理类型
	private String pid;//授权产品
	
	
	public String getKeyno() {
		return keyno;
	}
	public void setKeyno(String keyno) {
		this.keyno = keyno;
	}
	public String getServid() {
		return servid;
	}
	public void setServid(String servid) {
		this.servid = servid;
	}
	public String getPermark() {
		return permark;
	}
	public void setPermark(String permark) {
		this.permark = permark;
	}
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
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
//	public String getLogicdevno() {
//		return logicdevno;
//	}
//	public void setLogicdevno(String logicdevno) {
//		this.logicdevno = logicdevno;
//	}
	public String getLogicstbno() {
		return logicstbno;
	}
	public void setLogicstbno(String logicstbno) {
		this.logicstbno = logicstbno;
	}
	public String getOpkind() {
		return opkind;
	}
	public void setOpkind(String opkind) {
		this.opkind = opkind;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
    public String getHouseid() {
        return houseid;
    }
    public void setHouseid(String houseid) {
        this.houseid = houseid;
    }

	
}
