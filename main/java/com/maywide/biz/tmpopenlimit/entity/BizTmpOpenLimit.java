package com.maywide.biz.tmpopenlimit.entity;

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
@Table(name = "BIZ_TMPOPEN_LIMIT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BizTmpOpenLimit extends PersistableEntity<Long> implements
Persistable<Long> {
	
	@MetaData(value = "recid")
	@EntityAutoCode
	private Long id;
	
	@MetaData(value = "限制对象类型")
	@EntityAutoCode
	private String objType;
	
	@MetaData(value = "限制对象ID")
	@EntityAutoCode
	private Long objId;
	
	@MetaData(value = "限制方式")
	@EntityAutoCode
	private String timeType;
	
	@MetaData(value = "体验授权方案标识")
	@EntityAutoCode
	private Long planId;
	
	@MetaData(value = "体验授权名称")
	@EntityAutoCode
	private String name;
	
	@MetaData(value = "限制次数")
	@EntityAutoCode
	private Long limitNums;
	
	@MetaData(value = "创建人")
	@EntityAutoCode
	private Long createOper;
	
	@MetaData(value = "创建时间")
	@EntityAutoCode
	private Date createTime;
	
	@MetaData(value = "最近修改人")
	@EntityAutoCode
	private Long updateOper;
	
	@MetaData(value = "最近修改时间")
	@EntityAutoCode
	private Date updateTime;
	
	@MetaData(value = "地市")
	@EntityAutoCode
	private String city;
	
	
	@Override
	@Transient
	public String getDisplay() {
		return null;
	}

	@Id	
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "RECID", nullable = false, unique = true, insertable = true, updatable = true, precision = 16, scale = 0)
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}
	@Column(name = "OBJTYPE", nullable = false, unique = false, insertable = true, updatable = true, length = 1)
	public String getObjType() {
		return objType;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}
	@Column(name = "OBJID", nullable = false, unique = false, insertable = true, updatable = true, length = 16)
	public Long getObjId() {
		return objId;
	}

	public void setObjId(Long objId) {
		this.objId = objId;
	}
	@Column(name = "TIMETYPE", nullable = false, unique = false, insertable = true, updatable = true, length = 1)
	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	@Column(name = "PLANID", nullable = false, unique = false, insertable = true, updatable = true, length = 16)
	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}
	@Column(name = "NAME", nullable = false, unique = false, insertable = true, updatable = true, length = 40)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "LIMITNUMS", nullable = false, unique = false, insertable = true, updatable = true, length = 16)
	public Long getLimitNums() {
		return limitNums;
	}

	public void setLimitNums(Long limitNums) {
		this.limitNums = limitNums;
	}
	@Column(name = "CREATEOPER", nullable = true, unique = false, insertable = true, updatable = true, length = 16)
	public Long getCreateOper() {
		return createOper;
	}

	public void setCreateOper(Long createOper) {
		this.createOper = createOper;
	}
	@Column(name = "CREATETIME", nullable = true, unique = false, insertable = true, updatable = true)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "UPDATEOPER", nullable = true, unique = false, insertable = true, updatable = true, length = 16)
	public Long getUpdateOper() {
		return updateOper;
	}

	public void setUpdateOper(Long updateOper) {
		this.updateOper = updateOper;
	}
	@Column(name = "UPDATETIME", nullable = true, unique = false, insertable = true, updatable = true)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Column(name = "CITY", nullable = true, unique = false, insertable = true, updatable = true, length = 2)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setId(Long id) {
		this.id = id;
	}	

}
