package com.maywide.common.datatransfer.entity;

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
@Table(name = "cfg_mapping_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MappingConfig extends PersistableEntity<Long> implements Persistable<Long> {
	private Long id;
	private String className;
	private String conditionType;
	private String fieldName;
	private String fieldValue;
	private Integer status;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getConditionType() {
		return conditionType;
	}
	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
    @Transient
    public String getDisplay() {
        return null;
    }
}
