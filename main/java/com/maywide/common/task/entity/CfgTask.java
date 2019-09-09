package com.maywide.common.task.entity;

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
@Table(name = "cfg_task")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CfgTask extends PersistableEntity<Long> implements Persistable<Long> {
	private Long id;
	private String taskName;
	private String taskCode;
	private String implClass;
	private String taskMethod;
	private String taskExpr;
	private Integer sort;
	private String status;
	private String remark;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getImplClass() {
		return implClass;
	}
	public void setImplClass(String implClass) {
		this.implClass = implClass;
	}
	public String getTaskMethod() {
		return taskMethod;
	}
	public void setTaskMethod(String taskMethod) {
		this.taskMethod = taskMethod;
	}
	public String getTaskExpr() {
		return taskExpr;
	}
	public void setTaskExpr(String taskExpr) {
		this.taskExpr = taskExpr;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
    @Transient
    public String getDisplay() {
        return null;
    }
}
