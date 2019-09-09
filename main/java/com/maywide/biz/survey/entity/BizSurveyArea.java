package com.maywide.biz.survey.entity;

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

import com.maywide.core.annotation.MetaData;
import com.maywide.core.entity.PersistableEntity;
import com.maywide.core.entity.annotation.EntityAutoCode;


@Entity
@Table(name="biz_survey_area")
@Cache(usage= CacheConcurrencyStrategy.READ_WRITE)
public class BizSurveyArea extends PersistableEntity<Long> implements Persistable<Long>{
  
	@MetaData(value = "编号")
	@EntityAutoCode
	private Long id;
	
	@MetaData(value = "问卷ID")
	@EntityAutoCode
	private Long sid;
	
	@MetaData(value = "地市")
	@EntityAutoCode
	private String city;
	
	@MetaData(value = "业务区")
	@EntityAutoCode
	private String areaid;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recid", unique = true, length = 16)
	public Long getId() {
	
		return id;
	}

	@Override
	@Transient
	public String getDisplay() {
		return "";
	}

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	

}
