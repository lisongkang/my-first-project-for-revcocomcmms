package com.maywide.biz.inter.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;

import com.maywide.core.entity.PersistableEntity;

@Entity
@Table(name = "biz_opinion")
public class BizOpinion extends PersistableEntity<Long> implements Persistable<Long> {

	private static final long serialVersionUID = 1L;

	private Long id; // 序列号
	private Long proid;
	private String status;
	private String opinion;
	private String approvetime;
	private Long approveid;
	@Override
	@Transient
	public String getDisplay() {
		return null;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "opinionid", unique = true, length = 16)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProid() {
		return proid;
	}

	public void setProid(Long proid) {
		this.proid = proid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getApprovetime() {
		return approvetime;
	}

	public void setApprovetime(String approvetime) {
		this.approvetime = approvetime;
	}

	public Long getApproveid() {
		return approveid;
	}

	public void setApproveid(Long approveid) {
		this.approveid = approveid;
	}
}
