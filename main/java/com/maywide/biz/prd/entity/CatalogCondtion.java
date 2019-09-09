package com.maywide.biz.prd.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import com.maywide.core.annotation.MetaData;
import com.maywide.core.entity.PersistableEntity;
import com.maywide.core.entity.annotation.EntityAutoCode;

@Entity
@Table(name = "prd_catalog_condtion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CatalogCondtion extends PersistableEntity<Long> implements Persistable<Long> {
	@MetaData(value = "编号")
	private Long id;
	
	@MetaData(value = "目录编号")
	private Long catalogid;
	
	@MetaData(value = "条件类型")
	private String contiontype;
	
	@MetaData(value = "条件值")
	private String contionvalue;
	
	@Override
    @Transient
    public String getDisplay() {
        return null;
    }
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recid", unique = true, length = 16)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getCatalogid() {
		return catalogid;
	}

	public void setCatalogid(Long catalogid) {
		this.catalogid = catalogid;
	}

	@Column(nullable = false)
	public String getContiontype() {
		return contiontype;
	}

	public void setContiontype(String contiontype) {
		this.contiontype = contiontype;
	}

	@Column(nullable = false)
	public String getContionvalue() {
		return contionvalue;
	}

	public void setContionvalue(String contionvalue) {
		this.contionvalue = contionvalue;
	}
}
