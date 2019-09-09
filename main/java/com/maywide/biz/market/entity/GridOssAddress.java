package com.maywide.biz.market.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "GRID_OSS_ADDRESS")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class GridOssAddress implements Serializable {
	private String id;
	private Long houseid;
	private String gridid;
	private String gridname;
	private String status;
	
	@Id	
	@GeneratedValue(generator = "ca")
	@GenericGenerator(name = "ca", strategy = "assigned")
    @Column(name = "addrid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getHouseid() {
		return houseid;
	}
	public void setHouseid(Long houseid) {
		this.houseid = houseid;
	}
	public String getGridid() {
		return gridid;
	}
	public void setGridid(String gridid) {
		this.gridid = gridid;
	}
	public String getGridname() {
		return gridname;
	}
	public void setGridname(String gridname) {
		this.gridname = gridname;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
