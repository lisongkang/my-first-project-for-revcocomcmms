package com.maywide.biz.prv.entity;

import javax.persistence.*;

@Entity
@Table(name = "PRV_OPER_RIGHTS")
@SuppressWarnings("serial")
public class PrvOperRights implements java.io.Serializable {

	private Long id;
	private String objtype;
	private Long objectid;
	private String type;
	private String value;
	private java.util.Date begintime;
	private java.util.Date endtime;
	private String memo;
	private String permark;
	private String areaid;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true, insertable = true, updatable = true, precision = 12, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "OBJTYPE", nullable = true, unique = false, insertable = true, updatable = true, length = 1)
	public String getObjtype() {
		return objtype;
	}

	public void setObjtype(String objtype) {
		this.objtype = objtype;
	}

	@Column(name = "OBJECTID", nullable = true, unique = false, insertable = true, updatable = true, precision = 12, scale = 0)
	public Long getObjectid() {
		return objectid;
	}

	public void setObjectid(Long objectid) {
		this.objectid = objectid;
	}

	@Column(name = "TYPE", nullable = true, unique = false, insertable = true, updatable = true, length = 1)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "VALUE", nullable = true, unique = false, insertable = true, updatable = true, precision = 12, scale = 0)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "BEGINTIME", nullable = true, unique = false, insertable = true, updatable = true)
	public java.util.Date getBegintime() {
		return begintime;
	}

	public void setBegintime(java.util.Date begintime) {
		this.begintime = begintime;
	}

	@Column(name = "ENDTIME", nullable = true, unique = false, insertable = true, updatable = true)
	public java.util.Date getEndtime() {
		return endtime;
	}

	public void setEndtime(java.util.Date endtime) {
		this.endtime = endtime;
	}

	@Column(name = "MEMO", nullable = true, unique = false, insertable = true, updatable = true, length = 255)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "PERMARK", nullable = true, unique = false, insertable = true, updatable = true, length = 80)
	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}

	@Column(name = "AREAID", nullable = true, unique = false, insertable = true, updatable = true, length = 80)
	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	
	
}
