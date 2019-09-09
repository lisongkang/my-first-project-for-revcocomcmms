package com.maywide.biz.remind.entity;

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
@Table(name = "BIZ_REMIND_RECORD")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BizRemindRecord extends PersistableEntity<Long> implements Persistable<Long> {
	@MetaData(value = "记录标识")
	private Long id;
	
	@MetaData(value = "批次标识")
	@EntityAutoCode(order = 1, listShow = true)
	private Long batid;
	
	@MetaData(value = "预警对象标识")
	@EntityAutoCode(order = 2, listShow = true)
	private String objid;
	
	@MetaData(value = "预警说明")
	@EntityAutoCode(order = 3, listShow = true)
	private String objcaption;
	
	@MetaData(value = "客户ID")
	@EntityAutoCode(order = 4, listShow = true)
	private Long custid;
	
	@MetaData(value = "记录状态")
	@EntityAutoCode(order = 5, listShow = true)
	private String status;
	
	private String whladdr;//住宅地址
	private String name; // 客户名称
	private Long areaid; // 业务区
	private String areaname; // 业务区名称
	private Long knowid;// 营销标识
	private String knowname; // 营销标识名称
	private Long areamger;// 社区经理
	private String areamgername;// 社区经理
	
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
	
	@Column(name = "batid", nullable = true, length = 16)
	public Long getBatid(){
		return batid;
	}
	
	public void setBatid(Long batid) {
		this.batid = batid;
	}

	@Column(name = "objid", nullable = true, length = 30)
	public String getObjid() {
		return objid;
	}

	public void setObjid(String objid) {
		this.objid = objid;
	}

	@Column(name = "objcaption", nullable = true, length = 255)
	public String getObjcaption() {
		return objcaption;
	}

	public void setObjcaption(String objcaption) {
		this.objcaption = objcaption;
	}

	@Column(name = "custid", nullable = true, length = 16)
	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	@Column(name = "status", nullable = true, length = 1)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Transient
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Transient
	public Long getAreaid() {
		return areaid;
	}

	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}

	@Transient
	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	@Transient
	public Long getKnowid() {
		return knowid;
	}

	public void setKnowid(Long knowid) {
		this.knowid = knowid;
	}

	@Transient
	public String getKnowname() {
		return knowname;
	}

	public void setKnowname(String knowname) {
		this.knowname = knowname;
	}

	@Transient
	public Long getAreamger() {
		return areamger;
	}

	public void setAreamger(Long areamger) {
		this.areamger = areamger;
	}

	@Transient
	public String getAreamgername() {
		return areamgername;
	}

	public void setAreamgername(String areamgername) {
		this.areamgername = areamgername;
	}

	@Transient
	public String getWhladdr() {
		return whladdr;
	}

	public void setWhladdr(String whladdr) {
		this.whladdr = whladdr;
	}
}
