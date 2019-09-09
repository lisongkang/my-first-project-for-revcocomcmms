package com.maywide.biz.inter.pojo.quegridmanager;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueGridmanagerInterReq  extends BaseApiRequest implements java.io.Serializable {
	private String city;//所属分公司
	private String objtype;//网格对象类型
	private String objid;//网格对象id
	private String gridid;//网格id
	private String gridname;//网格名称
	private String ismain;//区经理主标识
	private String areamgername;//社区经理名	
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getObjtype() {
		return objtype;
	}
	public void setObjtype(String objtype) {
		this.objtype = objtype;
	}
	public String getObjid() {
		return objid;
	}
	public void setObjid(String objid) {
		this.objid = objid;
	}
	public String getGridid() {
		return gridid;
	}
	public void setGridid(String gridid) {
		this.gridid = gridid;
	}
	public String getGridname() {
		return gridname;
	}
	public void setGridname(String gridname) {
		this.gridname = gridname;
	}
	public String getIsmain() {
		return ismain;
	}
	public void setIsmain(String ismain) {
		this.ismain = ismain;
	}
	public String getAreamgername() {
		return areamgername;
	}
	public void setAreamgername(String areamgername) {
		this.areamgername = areamgername;
	}


}
