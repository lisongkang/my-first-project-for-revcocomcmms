package com.maywide.biz.prd.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "PRD_SALESPKG_SOFT")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class SalespkgSoft implements Serializable {
	private Long id;
	private Long salespkgid;
	private Long salesobjid;
	private String pclass;
	private String psubclass;
	private Long pid;
	private String timetype;
	private Long presentcycle;
	private String unit;
	private java.util.Date fixedate;
	private Long createoper;
	private java.util.Date createtime;
	private Long updateoper;
	private java.util.Date updatetime;
	private String ispostpone; // �����Ƿ�˳��
	private String optionflag;
	private String ismasterprd;
	
	@Id	
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "RECID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "SALESPKGID", nullable = false, unique = false, insertable = true, updatable = true)
	public Long getSalespkgid() {
		return salespkgid;
	}

	public void setSalespkgid(Long salespkgid) {
		this.salespkgid = salespkgid;
	}

	@Column(name = "SALESOBJID", nullable = false, unique = false, insertable = true, updatable = true)
	public Long getSalesobjid() {
		return salesobjid;
	}

	public void setSalesobjid(Long salesobjid) {
		this.salesobjid = salesobjid;
	}

	@Column(name = "PCLASS", nullable = false, unique = false, insertable = true, updatable = true)
	public String getPclass() {
		return pclass;
	}

	public void setPclass(String pclass) {
		this.pclass = pclass;
	}

	@Column(name = "PSUBCLASS", nullable = false, unique = false, insertable = true, updatable = true)
	public String getPsubclass() {
		return psubclass;
	}

	public void setPsubclass(String psubclass) {
		this.psubclass = psubclass;
	}

	@Column(name = "PID", nullable = false, unique = false, insertable = true, updatable = true)
	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	@Column(name = "TIMETYPE", nullable = false, unique = false, insertable = true, updatable = true)
	public String getTimetype() {
		return timetype;
	}

	public void setTimetype(String timetype) {
		this.timetype = timetype;
	}

	@Column(name = "PRESENTCYCLE", nullable = true, unique = false, insertable = true, updatable = true)
	public Long getPresentcycle() {
		return presentcycle;
	}

	public void setPresentcycle(Long presentcycle) {
		this.presentcycle = presentcycle;
	}

	@Column(name = "UNIT", nullable = true, unique = false, insertable = true, updatable = true)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "FIXEDATE", nullable = true, unique = false, insertable = true, updatable = true)
	public java.util.Date getFixedate() {
		return fixedate;
	}

	public void setFixedate(java.util.Date fixedate) {
		this.fixedate = fixedate;
	}


	@Column(name = "CREATEOPER", nullable = false, unique = false, insertable = true, updatable = true)
	public Long getCreateoper() {
		return createoper;
	}

	public void setCreateoper(Long createoper) {
		this.createoper = createoper;
	}

	@Column(name = "CREATETIME", nullable = false, unique = false, insertable = true, updatable = true)
	public java.util.Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(java.util.Date createtime) {
		this.createtime = createtime;
	}

	@Column(name = "UPDATEOPER", nullable = false, unique = false, insertable = true, updatable = true)
	public Long getUpdateoper() {
		return updateoper;
	}

	public void setUpdateoper(Long updateoper) {
		this.updateoper = updateoper;
	}

	@Column(name = "UPDATETIME", nullable = false, unique = false, insertable = true, updatable = true)
	public java.util.Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(java.util.Date updatetime) {
		this.updatetime = updatetime;
	}

	@Column(name = "ISPOSTPONE", nullable = true, unique = false, insertable = true, updatable = true)
	public String getIspostpone() {
		return ispostpone;
	}

	public void setIspostpone(String ispostpone) {
		this.ispostpone = ispostpone;
	}
	
	@Column(name = "OPTIONFLAG", nullable = false, unique = false, insertable = true, updatable = true)
	public String getOptionflag() {
		return optionflag;
	}

	public void setOptionflag(String optionflag) {
		this.optionflag = optionflag;
	}

	@Column(name = "ISMASTERPRD", nullable = true, unique = false, insertable = true, updatable = true)
	public String getIsmasterprd()
    {
	    return ismasterprd;
    }

	public void setIsmasterprd(String ismasterprd)
    {
	    this.ismasterprd = ismasterprd;
    }
}
