package com.maywide.biz.prv.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import com.maywide.core.entity.PersistableEntity;

@Entity
@Table(name = "PRV_OPER_MENUCTRL")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PrvOperMenuCtrl extends PersistableEntity<Long> implements Persistable<Long> {

	private Long id;
	
	private Long operid;
	
	private Long menuid;
	
	private String controlcode;
	
	private String controlvalue;
	
	private String value;
	
	private String timelength = "0";
	
	private String opername;
	
	private String menuname;
	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true, insertable = true, updatable = true, precision = 16, scale = 0)
	public Long getId() {
		return id;
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


	@Column(name = "CONTROLCODE", nullable = true, unique = false, insertable = true, updatable = true, precision = 16, scale = 0)
	public String getControlcode() {
		return controlcode;
	}


	public void setControlcode(String controlcode) {
		this.controlcode = controlcode;
	}

	@Column(name = "CONTROLVALUE", nullable = true, unique = false, insertable = true, updatable = true, precision = 16, scale = 0)
	public String getControlvalue() {
		return controlvalue;
	}


	public void setControlvalue(String controlvalue) {
		this.controlvalue = controlvalue;
	}

	@Column(name = "VALUE", nullable = true, unique = false, insertable = true, updatable = true, precision = 16, scale = 0)
	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "TIMELENGTH", nullable = true, unique = false, insertable = true, updatable = true, precision = 16, scale = 0)
	public String getTimelength() {
		return timelength;
	}


	public void setTimelength(String timelength) {
		this.timelength = timelength;
	}


	public void setId(Long id) {
		this.id = id;
	}


	@Override
	@Transient
	public String getDisplay() {
		return "";
	}
	
	/*
	public void setDisplay(String display){
		
	}*/
	
	@Transient
	public String getOpername() {
		return opername;
	}

	
	public void setOpername(String opername) {
		this.opername = opername;
	}

	@Transient
	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	
	

}
