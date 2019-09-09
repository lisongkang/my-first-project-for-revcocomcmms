package com.maywide.biz.market.entity;

import java.io.Serializable;

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
@Table(name = "BIZ_GRID_OBJ")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GridObj extends PersistableEntity<Long> implements Persistable<Long> {
    private Long recid;
	private Long gridid;
	private String objtype;
	private Long objid;
	private String city;
	
	@Override
    @Transient
    public String getDisplay() {
        return null;
    }
	
	@Id	
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "recid")
	public Long getId() {
		return recid;
	}
	public void setId(Long recid) {
		this.recid = recid;
	}
	
	@Transient
    public Long getRecid() {
        return recid;
    }
	public void setRecid(Long recid) {
        this.recid = recid;
    }
	public Long getGridid() {
		return gridid;
	}
	public void setGridid(Long gridid) {
		this.gridid = gridid;
	}
	public String getObjtype() {
		return objtype;
	}
	public void setObjtype(String objtype) {
		this.objtype = objtype;
	}
	public Long getObjid() {
		return objid;
	}
	public void setObjid(Long objid) {
		this.objid = objid;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
}
