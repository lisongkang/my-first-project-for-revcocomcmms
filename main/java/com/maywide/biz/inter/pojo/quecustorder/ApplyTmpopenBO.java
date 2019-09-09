package com.maywide.biz.inter.pojo.quecustorder;

public class ApplyTmpopenBO implements java.io.Serializable {

	private String servid;//用户id
	private String logicdevno;//用户设备号
	private String logicstbno;//机顶盒号
	private String planid;//点通方案id
	private String planname;//点通方案名称
	private String pid;//授权产品id
	private String pname;//授权产品名称
	private String days;//授权天数
	
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
	public String getPlanid() {
		return planid;
	}
	public void setPlanid(String planid) {
		this.planid = planid;
	}
	public String getPlanname() {
		return planname;
	}
	public void setPlanname(String planname) {
		this.planname = planname;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}

}
