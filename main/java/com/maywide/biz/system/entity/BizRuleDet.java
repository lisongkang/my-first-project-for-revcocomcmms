package com.maywide.biz.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BIZ_RULE_DET")
@SuppressWarnings("serial")
public class BizRuleDet implements java.io.Serializable {

	private Long id;
	private Long ruleid;
	private String areaid;
	private String permission;
	private String info;
	private Long operator;
	private java.util.Date optime;
	private String value;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DETAILID", nullable = false, unique = true, insertable = true, updatable = true)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "RULEID", nullable = true, unique = false, insertable = true, updatable = true)
	public Long getRuleid() {
		return ruleid;
	}

	public void setRuleid(Long ruleid) {
		this.ruleid = ruleid;
	}

	@Column(name = "AREAID", nullable = true, unique = false, insertable = true, updatable = true)
	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	@Column(name = "PERMISSION", nullable = true, unique = false, insertable = true, updatable = true)
	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	@Column(name = "INFO", nullable = true, unique = false, insertable = true, updatable = true)
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Column(name = "OPERATOR", nullable = true, unique = false, insertable = true, updatable = true)
	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	@Column(name = "OPTIME", nullable = true, unique = false, insertable = true, updatable = true)
	public java.util.Date getOptime() {
		return optime;
	}

	public void setOptime(java.util.Date optime) {
		this.optime = optime;
	}

	@Column(name = "VALUE", nullable = true, unique = false, insertable = true, updatable = true , length = 255)
	public String getValue(){
		return value;
	}
	public void setValue(String value){
		this.value = value;
	}
}
