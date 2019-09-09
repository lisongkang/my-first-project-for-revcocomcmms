package com.maywide.biz.prd.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.domain.Persistable;

import com.maywide.core.entity.PersistableEntity;

@Entity
@Table(name = "PRD_SALES_RULE")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class SalesRule extends PersistableEntity<Long> implements Persistable<Long> {
	private Long id;
	private Pcode pcode = new Pcode();
	private String areaid;
	private Integer minprice;
	private Integer maxprice;
	private String isbuy;
	private Long createoper;
	private Date createtime;
	private Long updateoper;
	private Date updatetime;
	private String rfeecode;
	private String ifeecode;
	
	@Transient
    public String getDisplay() {
        return null;
    }
	
	@Id	
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "RECID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "pid", nullable = false)
    @NotFound(action=NotFoundAction.IGNORE)
	public Pcode getPcode() {
		return pcode;
	}
	public void setPcode(Pcode pcode) {
		this.pcode = pcode;
	}
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	public Integer getMinprice() {
		return minprice;
	}
	public void setMinprice(Integer minprice) {
		this.minprice = minprice;
	}
	public Integer getMaxprice() {
		return maxprice;
	}
	public void setMaxprice(Integer maxprice) {
		this.maxprice = maxprice;
	}
	public String getIsbuy() {
		return isbuy;
	}
	public void setIsbuy(String isbuy) {
		this.isbuy = isbuy;
	}
	public Long getCreateoper() {
		return createoper;
	}
	public void setCreateoper(Long createoper) {
		this.createoper = createoper;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Long getUpdateoper() {
		return updateoper;
	}
	public void setUpdateoper(Long updateoper) {
		this.updateoper = updateoper;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public String getRfeecode() {
		return rfeecode;
	}
	public void setRfeecode(String rfeecode) {
		this.rfeecode = rfeecode;
	}
	public String getIfeecode() {
		return ifeecode;
	}
	public void setIfeecode(String ifeecode) {
		this.ifeecode = ifeecode;
	}
}
