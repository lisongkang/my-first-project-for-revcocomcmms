package com.maywide.biz.prd.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "PRD_SALESPKG_SELECT_DET")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class SalespkgSelectDet implements Serializable {
	private Long id;
	private Long selectid;
	private Long pid;
	private Long createoper;
	private java.util.Date createtime;
	private Long updateoper;
	private java.util.Date updatetime;

	@Id	
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "RECID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSelectid() {
		return selectid;
	}

	public void setSelectid(Long selectid) {
		this.selectid = selectid;
	}

	@Column(name = "PID", nullable = true, unique = false, insertable = true, updatable = true)
	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	@Column(name = "CREATEOPER", nullable = true, unique = false, insertable = true, updatable = true)
	public Long getCreateoper() {
		return createoper;
	}

	public void setCreateoper(Long createoper) {
		this.createoper = createoper;
	}

	@Column(name = "CREATETIME", nullable = true, unique = false, insertable = true, updatable = true)
	public java.util.Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(java.util.Date createtime) {
		this.createtime = createtime;
	}

	@Column(name = "UPDATEOPER", nullable = true, unique = false, insertable = true, updatable = true)
	public Long getUpdateoper() {
		return updateoper;
	}

	public void setUpdateoper(Long updateoper) {
		this.updateoper = updateoper;
	}

	@Column(name = "UPDATETIME", nullable = true, unique = false, insertable = true, updatable = true)
	public java.util.Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(java.util.Date updatetime) {
		this.updatetime = updatetime;
	}
}
