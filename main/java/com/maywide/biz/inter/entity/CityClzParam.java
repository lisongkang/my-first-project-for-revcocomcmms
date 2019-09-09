package com.maywide.biz.inter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;

import com.maywide.core.entity.PersistableEntity;

@Entity
@Table(name="CITY_CLASS_PARAM")
public class CityClzParam  extends PersistableEntity<Long> implements Persistable<Long>{

	public void setId(Long id) {
		this.id = id;
	}

	private Long id;
	
	private String city;
	
	private Long areaid;
	
	private String kind;
	
	private String subkind;
	
	private String subname;
	
	@Id
	@Column(name = "recid", unique = true, length = 16)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Override
	public Long getId() {
		return id;
	}

	@Override
	@Transient
	public String getDisplay() {
		return null;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getAreaid() {
		return areaid;
	}

	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getSubkind() {
		return subkind;
	}

	public void setSubkind(String subkind) {
		this.subkind = subkind;
	}

	public String getSubname() {
		return subname;
	}

	public void setSubname(String subname) {
		this.subname = subname;
	}
	

}
