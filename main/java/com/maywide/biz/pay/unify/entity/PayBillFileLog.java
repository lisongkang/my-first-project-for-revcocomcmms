package com.maywide.biz.pay.unify.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "pay_bill_file_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PayBillFileLog implements Serializable {
	private static final long serialVersionUID = 1L;

	private String filedate;
	private Integer status;
	private Date opertime;
	private String memo;

	@Id
	@Column(name = "filedate")
	public String getFiledate() {
		return filedate;
	}

	public void setFiledate(String filedate) {
		this.filedate = filedate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getOpertime() {
		return opertime;
	}

	public void setOpertime(Date opertime) {
		this.opertime = opertime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
