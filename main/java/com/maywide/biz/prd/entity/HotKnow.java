package com.maywide.biz.prd.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "PRD_HOT_KNOW")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HotKnow implements Serializable {
	private Long knowid;
	private Long monsales;
	private String city;
	
	@Id	
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getKnowid() {
		return knowid;
	}
	public void setKnowid(Long knowid) {
		this.knowid = knowid;
	}
	public Long getMonsales() {
		return monsales;
	}
	public void setMonsales(Long monsales) {
		this.monsales = monsales;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
