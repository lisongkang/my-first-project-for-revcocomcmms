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
@Table(name = "biz_task_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BizTaskInfo {
	
	private Long taskid;
	private String tasktitle;
	private String taskdesc;
    private String  cusid;
    private Date stime;
    private Date ctime;
    private Date actime;
    private Integer pri;
    private String status;
    private String isexpired;
    private String cometype;
	private String type;
	private Date optime;
	private String opname;
	
	@Id
	@Column(name = "TASK_ID")
	public Long getTaskid() {
		return taskid;
	}
	public void setTaskid(Long taskid) {
		this.taskid = taskid;
	}
	
	@Column(name = "TASK_TITLE")
	public String getTasktitle() {
		return tasktitle;
	}
	public void setTasktitle(String tasktitle) {
		this.tasktitle = tasktitle;
	}
	
	@Column(name = "TASK_DESC")
	public String getTaskdesc() {
		return taskdesc;
	}
	public void setTaskdesc(String taskdesc) {
		this.taskdesc = taskdesc;
	}
	
	@Column(name = "CUSID")
	public String getCusid() {
		return cusid;
	}
	public void setCusid(String cusid) {
		this.cusid = cusid;
	}
	
	@Column(name = "STIME")
	public Date getStime() {
		return stime;
	}
	public void setStime(Date stime) {
		this.stime = stime;
	}
	
	@Column(name = "CTIME")
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	
	@Column(name = "ACTIME")
	public Date getActime() {
		return actime;
	}
	public void setActime(Date actime) {
		this.actime = actime;
	}
	
	@Column(name = "PRI")
	public Integer getPri() {
		return pri;
	}
	public void setPri(Integer pri) {
		this.pri = pri;
	}
	
	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "ISEXPIRED")
	public String getIsexpired() {
		return isexpired;
	}
	public void setIsexpired(String isexpired) {
		this.isexpired = isexpired;
	}
	
	@Column(name = "COMETYPE")
	public String getCometype() {
		return cometype;
	}
	public void setCometype(String cometype) {
		this.cometype = cometype;
	}
	
	@Column(name = "TYPE")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name = "OPTIME")
	public Date getOptime() {
		return optime;
	}
	public void setOptime(Date optime) {
		this.optime = optime;
	}
	
	@Column(name = "OPNAME")
	public String getOpname() {
		return opname;
	}
	public void setOpname(String opname) {
		this.opname = opname;
	}
	
	
}
