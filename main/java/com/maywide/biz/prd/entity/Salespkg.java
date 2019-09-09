package com.maywide.biz.prd.entity;

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
@Table(name = "PRD_SALESPKG")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Salespkg extends PersistableEntity<Long> implements Persistable<Long> {
	private Long id;
	private String salespkgcode;
	private String salespkgname;
	private String sclass;
	private String ssubclass;
	private String bizcode;
	private Double sums;
	private String hotflag;
	private java.util.Date sdate;
	private java.util.Date edate;
	private Long appnums;
	private String status;
	private String feecode;
	private String ifeecode;
	private String rfeecode;
	private String scopeflag;
	private Long createoper;
	private java.util.Date createtime;
	private Long updateoper;
	private java.util.Date updatetime;
	private String memo;
	private String ordertype;
	private String areas;
	private String isshowpost; // �Ƿ���ʾ����˳����-��
	private String pkgtype;	//Ӫ������
	private String postflag;

	@Id	
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "SALESPKGID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Transient
    public String getDisplay() {
        return salespkgname;
    }

	@Column(name = "SALESPKGCODE", nullable = false, unique = false, insertable = true, updatable = true)
	public String getSalespkgcode() {
		return salespkgcode;
	}

	public void setSalespkgcode(String salespkgcode) {
		this.salespkgcode = salespkgcode;
	}

	@Column(name = "SALESPKGNAME", nullable = false, unique = false, insertable = true, updatable = true)
	public String getSalespkgname() {
		return salespkgname;
	}

	public void setSalespkgname(String salespkgname) {
		this.salespkgname = salespkgname;
	}

	@Column(name = "SCLASS", nullable = false, unique = false, insertable = true, updatable = true)
	public String getSclass() {
		return sclass;
	}

	public void setSclass(String sclass) {
		this.sclass = sclass;
	}

	@Column(name = "SSUBCLASS", nullable = false, unique = false, insertable = true, updatable = true)
	public String getSsubclass() {
		return ssubclass;
	}

	public void setSsubclass(String ssubclass) {
		this.ssubclass = ssubclass;
	}

	@Column(name = "BIZCODE", nullable = false, unique = false, insertable = true, updatable = true)
	public String getBizcode() {
		return bizcode;
	}

	public void setBizcode(String bizcode) {
		this.bizcode = bizcode;
	}

	@Column(name = "SUMS", nullable = true, unique = false, insertable = true, updatable = true)
	public Double getSums() {
		return sums;
	}

	public void setSums(Double sums) {
		this.sums = sums;
	}

	@Column(name = "HOTFLAG", nullable = false, unique = false, insertable = true, updatable = true)
	public String getHotflag() {
		return hotflag;
	}

	public void setHotflag(String hotflag) {
		this.hotflag = hotflag;
	}

	@Column(name = "SDATE", nullable = false, unique = false, insertable = true, updatable = true)
	public java.util.Date getSdate() {
		return sdate;
	}

	public void setSdate(java.util.Date sdate) {
		this.sdate = sdate;
	}

	@Column(name = "EDATE", nullable = false, unique = false, insertable = true, updatable = true)
	public java.util.Date getEdate() {
		return edate;
	}

	public void setEdate(java.util.Date edate) {
		this.edate = edate;
	}

	@Column(name = "APPNUMS", nullable = false, unique = false, insertable = true, updatable = true)
	public Long getAppnums() {
		return appnums;
	}

	public void setAppnums(Long appnums) {
		this.appnums = appnums;
	}

	@Column(name = "STATUS", nullable = false, unique = false, insertable = true, updatable = true)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "FEECODE", nullable = true, unique = false, insertable = true, updatable = true)
	public String getFeecode() {
		return feecode;
	}

	public void setFeecode(String feecode) {
		this.feecode = feecode;
	}

	@Column(name = "IFEECODE", nullable = true, unique = false, insertable = true, updatable = true)
	public String getIfeecode() {
		return ifeecode;
	}

	public void setIfeecode(String ifeecode) {
		this.ifeecode = ifeecode;
	}

	@Column(name = "RFEECODE", nullable = true, unique = false, insertable = true, updatable = true)
	public String getRfeecode() {
		return rfeecode;
	}

	public void setRfeecode(String rfeecode) {
		this.rfeecode = rfeecode;
	}

	@Column(name = "SCOPEFLAG", nullable = false, unique = false, insertable = true, updatable = true)
	public String getScopeflag() {
		return scopeflag;
	}

	public void setScopeflag(String scopeflag) {
		this.scopeflag = scopeflag;
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

	@Column(name = "MEMO", nullable = true, unique = false, insertable = true, updatable = true)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "ORDERTYPE", nullable = true, unique = false, insertable = true, updatable = true)
	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	@Column(name = "AREAS", nullable = true, unique = false, insertable = true, updatable = true)
	public String getAreas() {
		return areas;
	}

	public void setAreas(String areas) {
		this.areas = areas;
	}

	@Column(name = "ISSHOWPOST", nullable = true, unique = false, insertable = true, updatable = true)
	public String getIsshowpost() {
		return isshowpost;
	}
	
	@Column(name= "POSTFLAG", nullable = true, unique = false, insertable = true, updatable = true)
	public String getPostflag() {
		return postflag;
	}

	public void setPostflag(String postflag) {
		this.postflag = postflag;
	}

	public void setIsshowpost(String isshowpost) {
		this.isshowpost = isshowpost;
	}

	@Column(name = "PKGTYPE", nullable = true, unique = false, insertable = true, updatable = true)
	public String getPkgtype() {
		return pkgtype;
	}

	public void setPkgtype(String pkgtype) {
		this.pkgtype = pkgtype;
	}
}
