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
@Table(name = "biz_task_track")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BizTaskTrack {
	private Long trackid;
	private Long taskid;
	private String operatorid;
	private String status;
	private String info;
	private Date optime;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRACK_ID")
	public Long getTrackid() {
		return trackid;
	}
	public void setTrackid(Long trackid) {
		this.trackid = trackid;
	}
	
	@Column(name = "TASK_ID")
	public Long getTaskid() {
		return taskid;
	}
	public void setTaskid(Long taskid) {
		this.taskid = taskid;
	}
	
	@Column(name = "OPERATORID")
	public String getOperatorid() {
		return operatorid;
	}
	public void setOperatorid(String operatorid) {
		this.operatorid = operatorid;
	}
	
	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "INFO")
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	@Column(name = "OPTIME")
	public Date getOptime() {
		return optime;
	}
	public void setOptime(Date optime) {
		this.optime = optime;
	}
	
	
}
