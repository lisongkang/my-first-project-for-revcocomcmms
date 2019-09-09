package com.maywide.biz.task.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "biz_task_resources")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BizTaskResources {
	
	private Long id;
	private Long taskid;
	private String gridid;
	private String operatorid;
	private String isvalid;
	private Date optime;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RES_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "TASK_ID")
	public Long getTaskid() {
		return taskid;
	}
	public void setTaskid(Long taskid) {
		this.taskid = taskid;
	}
	
	@Column(name = "GRIDID")
	public String getGridid() {
		return gridid;
	}
	public void setGridid(String gridid) {
		this.gridid = gridid;
	}
	
	@Column(name = "OPERATORID")
	public String getOperatorid() {
		return operatorid;
	}
	public void setOperatorid(String operatorid) {
		this.operatorid = operatorid;
	}
	
	@Column(name = "ISVALID")
	public String getIsvalid() {
		return isvalid;
	}
	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}
	
	@Column(name = "OPTIME")
	public Date getOptime() {
		return optime;
	}
	public void setOptime(Date optime) {
		this.optime = optime;
	}
	
	
}
