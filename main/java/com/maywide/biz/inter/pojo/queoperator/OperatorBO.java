package com.maywide.biz.inter.pojo.queoperator;

public class OperatorBO {
	private Long id;
	private String loginname;
	private String name;
	private String status;
	private Double logtimes;
	//private java.util.Date stime;
	//private java.util.Date etime;
	
	private String roles;
	private String departments;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Double getLogtimes() {
		return logtimes;
	}
	public void setLogtimes(Double logtimes) {
		this.logtimes = logtimes;
	}
	/*public java.util.Date getStime() {
		return stime;
	}
	public void setStime(java.util.Date stime) {
		this.stime = stime;
	}
	public java.util.Date getEtime() {
		return etime;
	}
	public void setEtime(java.util.Date etime) {
		this.etime = etime;
	}*/
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public String getDepartments() {
		return departments;
	}
	public void setDepartments(String departments) {
		this.departments = departments;
	}
}
