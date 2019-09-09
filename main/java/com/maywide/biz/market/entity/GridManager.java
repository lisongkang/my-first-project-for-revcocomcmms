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
@Table(name = "BIZ_GRIDMANAGER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GridManager extends PersistableEntity<Long> implements Persistable<Long> {
	private Long recid;
	private Long gridid;
	private Long areamger;
	private String ismain;
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
	public Long getAreamger() {
		return areamger;
	}
	public void setAreamger(Long areamger) {
		this.areamger = areamger;
	}
	public String getIsmain() {
		return ismain;
	}
	public void setIsmain(String ismain) {
		this.ismain = ismain;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
