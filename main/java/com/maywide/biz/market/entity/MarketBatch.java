package com.maywide.biz.market.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import com.maywide.core.annotation.MetaData;
import com.maywide.core.entity.PersistableEntity;
import com.maywide.core.entity.annotation.EntityAutoCode;

@Entity
@Table(name = "BIZ_MARKET_BATCH")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MarketBatch extends PersistableEntity<Long> implements Persistable<Long> {

	@MetaData(value = "记录号")
    @EntityAutoCode
    private Long id;
	
	@MetaData(value = "营销批次")
    @EntityAutoCode(order = 1, listShow = true)
	private String batchno;
	
	@MetaData(value = "业务区范围")
    @EntityAutoCode(order = 2, listShow = true)
	private String areaids;
	
	@MetaData(value = "营销标识")
    @EntityAutoCode(order = 3, listShow = true)
	private Long knowid;
	
	@MetaData(value = "总户数")
    @EntityAutoCode(order = 4, listShow = true)
	private Long nums;
	
	@MetaData(value = "状态")
    @EntityAutoCode(order = 5, listShow = true)
	private String status;
	
	@MetaData(value = "时间")
    @EntityAutoCode(order = 6, listShow = true)
	private Date appdate;
	
	@MetaData(value = "操作员")
    @EntityAutoCode(order = 7, listShow = true)
	private Long operid;
	
	@MetaData(value = "地市")
    @EntityAutoCode(order = 8, listShow = true)
	private String city;
	
	@MetaData(value = "部门")
    @EntityAutoCode(order = 9, listShow = true)
	private Long deptid;
	
	@MetaData(value = "备注")
    @EntityAutoCode(order = 10, listShow = true)
	private String memo;
	
	private String deptname;
	private String areanames;
	private String knowname;
	private String sappdate;
	

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recid", unique = true, length = 16)
	public Long getId() {
		return id;
	}

    public void setId(Long id) {
		this.id = id;
	}
    
	@Override
	@Transient
	public String getDisplay() {
		return "";
	}

	@Column(name = "batchno", nullable = true, length = 30)
	public String getBatchno() {
		return batchno;
	}
	
	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	@Column(name = "areaids", nullable = true, length = 512)
	public String getAreaids() {
		return areaids;
	}

	public void setAreaids(String areaids) {
		this.areaids = areaids;
	}

	@Column(name = "knowid", nullable = true, length = 20)
	public Long getKnowid() {
		return knowid;
	}

	public void setKnowid(Long knowid) {
		this.knowid = knowid;
	}

	@Column(name = "nums", nullable = true, length = 8)
	public Long getNums() {
		return nums;
	}

	public void setNums(Long nums) {
		this.nums = nums;
	}

	@Column(name = "status", nullable = true, length = 1)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "appdate", nullable = true)
	public Date getAppdate() {
		return appdate;
	}

	public void setAppdate(Date appdate) {
		this.appdate = appdate;
	}

	@Column(name = "operid", nullable = true, length = 20)
	public Long getOperid() {
		return operid;
	}

	public void setOperid(Long operid) {
		this.operid = operid;
	}

	@Column(name = "city", nullable = true, length = 2)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "deptid", nullable = true, length = 2)
	public Long getDeptid() {
		return deptid;
	}

	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}

	@Column(name = "memo", nullable = true, length = 255)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Transient
	public String getAreanames() {
		return areanames;
	}
	public void setAreanames(String areanames) {
		this.areanames = areanames;
	}
	@Transient
	public String getKnowname() {
		return knowname;
	}
	public void setKnowname(String knowname) {
		this.knowname = knowname;
	}
	@Transient
	public String getSappdate() {
		return sappdate;
	}
	public void setSappdate(String sappdate) {
		this.sappdate = sappdate;
	}

	@Transient
	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	
}
