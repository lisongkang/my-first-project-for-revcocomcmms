package com.maywide.biz.wages.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="wages_kpi_header")
public class WagesKpiHeader {

	private Long id;
	
	private Double amount;
	
	private String notes;
	
	private Double cents;
	
	private String level;
	
	private String remark;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="case_id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}


	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Double getCents() {
		return cents;
	}

	public void setCents(Double cents) {
		this.cents = cents;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
