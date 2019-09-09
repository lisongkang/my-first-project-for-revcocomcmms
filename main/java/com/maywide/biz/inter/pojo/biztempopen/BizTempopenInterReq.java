package com.maywide.biz.inter.pojo.biztempopen;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizTempopenInterReq extends BaseApiRequest implements
		java.io.Serializable {
	// boss接口所需参数
	private String keyno;// 设备号
	private String pcode;// 产品编码
	private String planid;// 点通方案id

	// 订单表所需参数
	private String areaid;// 业务区
	private String custid;// 客户id
	private String name;// 客户姓名
	private String houseid;// 地址id
	private String patchid;// 片区id
	private String whladdr;// 地址
	private String descrip;// 业务说明

	// 体验授权表所需参数
	private String servid;// 用户id
	// private String logicdevno;//用户设备号
	private String logicstbno;// 绑定设备号
	private String planname;// 点通方案名称
	private String pids;// 授权产品串
	private String days;// 授权天数

	public String getKeyno() {
		return keyno;
	}

	public void setKeyno(String keyno) {
		this.keyno = keyno;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public String getPlanid() {
		return planid;
	}

	public void setPlanid(String planid) {
		this.planid = planid;
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

	public String getServid() {
		return servid;
	}

	public void setServid(String servid) {
		this.servid = servid;
	}

	// public String getLogicdevno() {
	// return logicdevno;
	// }
	// public void setLogicdevno(String logicdevno) {
	// this.logicdevno = logicdevno;
	// }
	public String getLogicstbno() {
		return logicstbno;
	}

	public void setLogicstbno(String logicstbno) {
		this.logicstbno = logicstbno;
	}

	public String getPlanname() {
		return planname;
	}

	public void setPlanname(String planname) {
		this.planname = planname;
	}

	public String getPids() {
		return pids;
	}

	public void setPids(String pids) {
		this.pids = pids;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

    public String getHouseid() {
        return houseid;
    }

    public void setHouseid(String houseid) {
        this.houseid = houseid;
    }

}
