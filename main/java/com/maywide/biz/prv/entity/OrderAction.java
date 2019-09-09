package com.maywide.biz.prv.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="prd_orderaction")
public class OrderAction {

	private Long id;
	
	private String psubclass;
	
	private String pid;
	
	private String areaid;
	
	private String iselected;
	
	private Long count;
	
	private String orderunit;
	
	private String timetype;
	
	private String timecond;
	
	private String ispostpone;
	
	private Long pri;

	@Id
	@Column(name = "recid", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "psubclass")
	public String getPsubclass() {
		return psubclass;
	}

	public void setPsubclass(String psubclass) {
		this.psubclass = psubclass;
	}

	@Column(name = "pid")
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Column(name = "areaid")
	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	@Column(name = "iselected")
	public String getIsselected() {
		return iselected;
	}

	public void setIsselected(String iselected) {
		this.iselected = iselected;
	}

	@Column(name = "count")
	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	@Column(name = "orderunit")
	public String getOrdercount() {
		return orderunit;
	}

	public void setOrdercount(String orderunit) {
		this.orderunit = orderunit;
	}

	@Column(name = "timetype")
	public String getTimetype() {
		return timetype;
	}

	public void setTimetype(String timetype) {
		this.timetype = timetype;
	}

	@Column(name = "timecond")
	public String getTimecond() {
		return timecond;
	}

	public void setTimecond(String timecond) {
		this.timecond = timecond;
	}

	@Column(name = "ispostpone")
	public String getIspostpone() {
		return ispostpone;
	}

	public void setIspostpone(String ispostpone) {
		this.ispostpone = ispostpone;
	}

	@Column(name = "pri")
	public Long getPri() {
		return pri;
	}

	public void setPri(Long pri) {
		this.pri = pri;
	}
	
	
}
