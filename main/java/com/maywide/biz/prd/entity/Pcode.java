package com.maywide.biz.prd.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maywide.core.entity.PersistableEntity;

@Entity
@Table(name = "PRD_PCODE")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Pcode extends PersistableEntity<Long> implements Persistable<Long> {
	private Long id;
	private String pcode;
	private String pname;
	private String pclass;
	private String psubclass;
	private String status;
	private String isbase;
	private String hotflag;
	private java.util.Date sdate;
	private java.util.Date edate;
	private java.util.Date stopdate;
	private Long createoper;
	private java.util.Date createtime;
	private Long updateoper;
	private java.util.Date updatetime;
	private String memo;
	private String externalid;
	private String permark;
	private String prodtype;
	private List<SalesRule> rules = new ArrayList<SalesRule>();

	@Id	
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "PID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Transient
    public String getDisplay() {
        return pname;
    }

	@Column(name = "PCODE", nullable = true, unique = false, insertable = true, updatable = true)
	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	@Column(name = "PNAME", nullable = true, unique = false, insertable = true, updatable = true)
	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	@Column(name = "PCLASS", nullable = true, unique = false, insertable = true, updatable = true)
	public String getPclass() {
		return pclass;
	}

	public void setPclass(String pclass) {
		this.pclass = pclass;
	}

	@Column(name = "PSUBCLASS", nullable = true, unique = false, insertable = true, updatable = true)
	public String getPsubclass() {
		return psubclass;
	}

	public void setPsubclass(String psubclass) {
		this.psubclass = psubclass;
	}

	@Column(name = "STATUS", nullable = true, unique = false, insertable = true, updatable = true)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "ISBASE", nullable = true, unique = false, insertable = true, updatable = true)
	public String getIsbase() {
		return isbase;
	}

	public void setIsbase(String isbase) {
		this.isbase = isbase;
	}

	@Column(name = "HOTFLAG", nullable = true, unique = false, insertable = true, updatable = true)
	public String getHotflag() {
		return hotflag;
	}

	public void setHotflag(String hotflag) {
		this.hotflag = hotflag;
	}

	@Column(name = "SDATE", nullable = true, unique = false, insertable = true, updatable = true)
	public java.util.Date getSdate() {
		return sdate;
	}

	public void setSdate(java.util.Date sdate) {
		this.sdate = sdate;
	}

	@Column(name = "EDATE", nullable = true, unique = false, insertable = true, updatable = true)
	public java.util.Date getEdate() {
		return edate;
	}

	public void setEdate(java.util.Date edate) {
		this.edate = edate;
	}

	@Column(name = "STOPDATE", nullable = true, unique = false, insertable = true, updatable = true)
	public java.util.Date getStopdate() {
		return stopdate;
	}

	public void setStopdate(java.util.Date stopdate) {
		this.stopdate = stopdate;
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

	@Column(name = "MEMO", nullable = true, unique = false, insertable = true, updatable = true)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "EXTERNALID", nullable = true, unique = false, insertable = true, updatable = true)
	public String getExternalid() {
		return externalid;
	}

	public void setExternalid(String externalid) {
		this.externalid = externalid;
	}

	@Column(name = "PERMARK", nullable = true, unique = false, insertable = true, updatable = true)
	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}
	
	@Column(name = "PRODTYPE", nullable = true, unique = false, insertable = true, updatable = true)
	public String getProdtype() {
		return prodtype;
	}

	public void setProdtype(String prodtype) {
		this.prodtype = prodtype;
	}
	
	@OneToMany(mappedBy = "pcode", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	public List<SalesRule> getRules() {
		return rules;
	}
	public void setRules(List<SalesRule> rules) {
		this.rules = rules;
	}
}
