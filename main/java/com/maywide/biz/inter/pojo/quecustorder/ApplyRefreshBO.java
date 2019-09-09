package com.maywide.biz.inter.pojo.quecustorder;

public class ApplyRefreshBO implements java.io.Serializable {

	private String servid;//用户id
	private String logicdevno;//用户设备号
	private String logicstbno;//机顶盒号
	private String opkind;//处理类型
	private String opkindname;//处理类型名称
	private String pid;//产品
	private String pname;//产品名称
	
	public String getServid() {
		return servid;
	}
	public void setServid(String servid) {
		this.servid = servid;
	}
	public String getLogicdevno() {
		return logicdevno;
	}
	public void setLogicdevno(String logicdevno) {
		this.logicdevno = logicdevno;
	}
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
	public String getOpkindname() {
		return opkindname;
	}
	public void setOpkindname(String opkindname) {
		this.opkindname = opkindname;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	
}
