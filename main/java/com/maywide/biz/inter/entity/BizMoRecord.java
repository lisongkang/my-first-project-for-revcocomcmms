package com.maywide.biz.inter.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BIZ_MO_RECORD")
public class BizMoRecord implements Serializable{

	private static final long serialVersionUID = 8620115458611136449L;
	
	private Long id;

	private Long aid;
	
	private Long menuid;
	
	private Long operid;
	
	private int sort;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RECID", nullable = false, unique = true)	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "AID", nullable = false,unique = true)
	public Long getAid() {
		return aid;
	}

	public void setAid(Long aid) {
		this.aid = aid;
	}

	@Column(name = "MENUID", nullable = false,unique = true)
	public Long getMenuid() {
		return menuid;
	}

	public void setMenuid(Long menuid) {
		this.menuid = menuid;
	}

	@Column(name = "OPERID", nullable = false)
	public Long getOperid() {
		return operid;
	}

	public void setOperid(Long operid) {
		this.operid = operid;
	}

	@Column(name = "SORT", nullable = false)
	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
