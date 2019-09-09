package com.maywide.biz.inter.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="operator_login_log")
public class OperatorLoginLog {

	private Long logid;
	
	private String loginIpAddr;
	
	private Date loginDate;
	
	private String loginVertype;
	
	private String loginPkgName;
	
	private String loginDevstr;
	
	private Long operid;
	
	private Long deptid;
	
	private String city;
	
	
	
	

	public String getLoginIpAddr() {
		return loginIpAddr;
	}


	@Id
	@Column(name="logid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getLogid() {
		return logid;
	}



	public void setLogid(Long logid) {
		this.logid = logid;
	}



	public void setLoginIpAddr(String loginIpAddr) {
		this.loginIpAddr = loginIpAddr;
	}



	public Date getLoginDate() {
		return loginDate;
	}



	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}



	public String getLoginVertype() {
		return loginVertype;
	}



	public void setLoginVertype(String loginVertype) {
		this.loginVertype = loginVertype;
	}



	public String getLoginPkgName() {
		return loginPkgName;
	}



	public void setLoginPkgName(String loginPkgName) {
		this.loginPkgName = loginPkgName;
	}



	public String getLoginDevstr() {
		return loginDevstr;
	}



	public void setLoginDevstr(String loginDevstr) {
		this.loginDevstr = loginDevstr;
	}



	public Long getOperid() {
		return operid;
	}



	public void setOperid(Long operid) {
		this.operid = operid;
	}



	public Long getDeptid() {
		return deptid;
	}



	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}


}
