package com.maywide.biz.prd.entity;

import java.util.Date;

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
@Table(name = "PRD_CATALOG_ITEM")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CatalogItem extends PersistableEntity<Long> implements Persistable<Long> {
	private Long id;
	private Long catalogid;
	private Long knowid;
	private Integer pri;
	private Long createoper;
	private Date createtime;
	private Long updateoper;
	private Date updatetime;
	private String city;
	
	@Override
    @Transient
    public String getDisplay() {
        return null;
    }
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "itemid", unique = true, length = 16)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Transient
	public Long getItemid() {
		return id;
	}
	public void setItemid(Long itemid) {
		this.id = itemid;
	}
	public Long getCatalogid() {
		return catalogid;
	}
	public void setCatalogid(Long catalogid) {
		this.catalogid = catalogid;
	}
	public Long getKnowid() {
		return knowid;
	}
	public void setKnowid(Long knowid) {
		this.knowid = knowid;
	}
	public Integer getPri() {
		return pri;
	}
	public void setPri(Integer pri) {
		this.pri = pri;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
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
	
}
