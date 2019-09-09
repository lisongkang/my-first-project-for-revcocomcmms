package com.maywide.biz.inter.pojo.quecustorder;

public class ApplyArrearBO implements java.io.Serializable {
	
	private String custid;//缴费客户id
	private String keyno;//用户设备号
	private String permark;//业务类型
	private String pid;//产品id
	private String pname;//产品名称
	private String stime;//开始时间
	private String etime;//截至时间
	private String fees;//账号类型名称
	
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
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
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public String getFees() {
		return fees;
	}
	public void setFees(String fees) {
		this.fees = fees;
	}

	
}
