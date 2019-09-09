package com.maywide.biz.prv.entity;

import javax.persistence.*;

@Entity
@Table(name = "PRV_ROLEPRIVS")
@SuppressWarnings("serial")
public class PrvRoleprivs implements java.io.Serializable {

	private Long id;
	private Long roleid;
	private Long menuid;
	private java.util.Date starttime;
	private java.util.Date stoptime;
	private String operid;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROLEPRIVSID", nullable = false, unique = true, insertable = true, updatable = true, precision = 12, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ROLEID", nullable = true, unique = false, insertable = true, updatable = true, precision = 12, scale = 0)
	public Long getRoleid() {
		return roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

	@Column(name = "MENUID", nullable = true, unique = false, insertable = true, updatable = true, precision = 12, scale = 0)
	public Long getMenuid() {
		return menuid;
	}

	public void setMenuid(Long menuid) {
		this.menuid = menuid;
	}

	@Column(name = "STARTTIME", nullable = true, unique = false, insertable = true, updatable = true)
	public java.util.Date getStarttime() {
		return starttime;
	}

	public void setStarttime(java.util.Date starttime) {
		this.starttime = starttime;
	}

	@Column(name = "STOPTIME", nullable = true, unique = false, insertable = true, updatable = true)
	public java.util.Date getStoptime() {
		return stoptime;
	}

	public void setStoptime(java.util.Date stoptime) {
		this.stoptime = stoptime;
	}

	@Column(name = "OPERID", nullable = true, unique = false, insertable = true, updatable = true)
	public String getOperid() {
		return operid;
	}

	public void setOperid(String operid) {
		this.operid = operid;
	}
	
}
