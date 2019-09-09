package com.maywide.biz.market.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "prv_user_feedback")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PrvUserFeedback implements Serializable {
	private Long recid;
	private Long operid;
	private Long deptid;
	private Long orderid;
	private Date optime;
	private String name;
	private String phone;
	private String email;
	private String content;
	private String status;
	
	@Id	
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getRecid() {
		return recid;
	}
	public void setRecid(Long recid) {
		this.recid = recid;
	}
    public Long getOperid() {
        return operid;
    }
    public void setOperid(Long operid) {
        this.operid = operid;
    }
    public Long getDeptid() {
        return deptid;
    }
    public void setDeptid(Long deptid) {
        this.deptid = deptid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Long getOrderid() {
        return orderid;
    }
    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }
    public Date getOptime() {
        return optime;
    }
    public void setOptime(Date optime) {
        this.optime = optime;
    }
	
}
