package com.maywide.biz.prv.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRV_SHORTCUT")
@SuppressWarnings("serial")
public class PrvShortcut implements java.io.Serializable {

	private Long id;
	private Long operid;
	private Long roleid;
	private Long menuid;
	private Long sysid;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RECID", nullable = false, unique = true, insertable = true, updatable = true, precision = 16, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "OPERID", nullable = true, unique = false, insertable = true, updatable = true, precision = 16, scale = 0)
	public Long getOperid() {
		return operid;
	}

	public void setOperid(Long operid) {
		this.operid = operid;
	}

	@Column(name = "MENUID", nullable = true, unique = false, insertable = true, updatable = true, precision = 16, scale = 0)
	public Long getMenuid() {
		return menuid;
	}

	public void setMenuid(Long menuid) {
		this.menuid = menuid;
	}

	@Column(name = "SYSID", nullable = true, unique = false, insertable = true, updatable = true, precision = 16, scale = 0)
	public Long getSysid() {
		return sysid;
	}

	public void setSysid(Long sysid) {
		this.sysid = sysid;
	}

	@Column(name = "ROLEID", nullable = true, unique = true, insertable = true, updatable = true, precision = 12, scale = 0)
	public Long getRoleid() {
		return roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}
	
	
}
