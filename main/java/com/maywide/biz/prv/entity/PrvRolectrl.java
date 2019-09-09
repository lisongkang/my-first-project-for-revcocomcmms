package com.maywide.biz.prv.entity;

import javax.persistence.*;

@Entity
@Table(name = "PRV_ROLECTRL")
@SuppressWarnings("serial")
public class PrvRolectrl implements java.io.Serializable {

	private Long id;
	private Long privsid;
	private Long menuid;
	private String controlid;
	private String value;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true, insertable = true, updatable = true, precision = 12, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "PRIVSID", nullable = true, unique = false, insertable = true, updatable = true, length = 12)
	public Long getPrivsid() {
		return privsid;
	}

	public void setPrivsid(Long privsid) {
		this.privsid = privsid;
	}
	
	@Column(name = "MENUID", nullable = true, unique = false, insertable = true, updatable = true, length = 12)
	public Long getMenuid() {
		return menuid;
	}

	public void setMenuid(Long menuid) {
		this.menuid = menuid;
	}
	
	@Column(name = "CONTROLID", nullable = true, unique = false, insertable = true, updatable = true, length = 24)
	public String getControlid() {
		return controlid;
	}

	public void setControlid(String controlid) {
		this.controlid = controlid;
	}

	@Column(name = "VALUE", nullable = true, unique = false, insertable = true, updatable = true, length = 1)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
