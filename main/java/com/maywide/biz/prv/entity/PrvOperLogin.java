package com.maywide.biz.prv.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRV_OPER_LOGIN")
@SuppressWarnings("serial")
public class PrvOperLogin implements java.io.Serializable{

	private Long id;
	private java.util.Date refreshtime;
	private String memo;

	@Id
	@Column(name = "OPERID", nullable = false, unique = true, insertable = true, updatable = true , precision = 16, scale = 0)
	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}

	@Column(name = "REFRESHTIME", nullable = false, unique = false, insertable = true, updatable = true )
	public java.util.Date getRefreshtime(){
		return refreshtime;
	}
	public void setRefreshtime(java.util.Date refreshtime){
		this.refreshtime = refreshtime;
	}
	@Column(name = "MEMO", nullable = true, unique = false, insertable = true, updatable = true , length = 255)
	public String getMemo(){
		return memo;
	}
	public void setMemo(String memo){
		this.memo = memo;
	}
}
