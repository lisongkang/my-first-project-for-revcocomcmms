package com.maywide.biz.prv.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="OPT_ACTION_RECORD")
public class OperAction {

	private Long id;
	
	private String opcode;
	
	private Long optid;
	
	private Long nums;
	
	private Date updateTime;
	
	private String city;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recid", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="opcode", nullable = false)
	public String getOpcode() {
		return opcode;
	}

	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}

	@Column(name="optid",nullable=false)
	public Long getOptid() {
		return optid;
	}

	public void setOptid(Long optid) {
		this.optid = optid;
	}

	@Column(name="nums",nullable=false)
	public Long getNums() {
		return nums;
	}

	public void setNums(Long nums) {
		this.nums = nums;
	}

	@Column(name="updatetime")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Column(name="city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	
	
}
