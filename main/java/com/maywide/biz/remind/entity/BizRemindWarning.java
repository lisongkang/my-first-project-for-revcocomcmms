package com.maywide.biz.remind.entity;

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
@Table(name = "BIZ_REMIND_WARNING")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BizRemindWarning extends PersistableEntity<Long> implements Persistable<Long> {
	@MetaData(value = "预警任务标识")
	private Long id;
	
	@MetaData(value = "预警类型")
	@EntityAutoCode(order = 1, listShow = true)
	private String objtype;
	
	@MetaData(value = "预警对象标识")
	@EntityAutoCode(order = 2, listShow = true)
	private String objids;
	
	@MetaData(value = "预警任务描述")
	@EntityAutoCode(order = 3, listShow = true)
	private String description;
	
	@MetaData(value = "优先级")
	@EntityAutoCode(order = 4, listShow = true)
	private Long pri;

	@MetaData(value = "有效期限")
	@EntityAutoCode(order = 5, listShow = true)
	private Date edate;
	
	@MetaData(value = "录入时间")
	@EntityAutoCode(order = 6, listShow = true)
	private Date opdate;
	
	@MetaData(value = "录入工号")
	@EntityAutoCode(order = 7, listShow = true)
	private Long operid;
	
	@MetaData(value = "录入部门")
	@EntityAutoCode(order = 8, listShow = true)
	private Long deptid;
	
	@MetaData(value = "所属分公司")
	@EntityAutoCode(order = 9, listShow = true)
	private String city;
	
	private String deptname;
	private String opername;
	private String objtypename;
	private String objidsname;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "remid", unique = true, length = 16)
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
	
	@Column(name = "objtype", nullable = true, length = 1)
	public String getObjtype(){
		return objtype;
	}
	
	public void setObjtype(String objtype) {
		this.objtype = objtype;
	}

	@Column(name = "objids", nullable = true, length = 255)
	public String getObjids() {
		return objids;
	}

	public void setObjids(String objids) {
		this.objids = objids;
	}

	@Column(name = "description", nullable = true, length = 255)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "pri", nullable = true, length = 16)
	public Long getPri() {
		return pri;
	}

	public void setPri(Long pri) {
		this.pri = pri;
	}

	@Column(name = "edate", nullable = true)
	public Date getEdate() {
		return edate;
	}

	public void setEdate(Date edate) {
		this.edate = edate;
	}

	@Column(name = "opdate", nullable = true)
	public Date getOpdate() {
		return opdate;
	}

	public void setOpdate(Date opdate) {
		this.opdate = opdate;
	}

	@Column(name = "operid", nullable = true, length = 16)
	public Long getOperid() {
		return operid;
	}

	public void setOperid(Long operid) {
		this.operid = operid;
	}

	@Column(name = "deptid", nullable = true, length = 16)
	public Long getDeptid() {
		return deptid;
	}

	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}

	@Column(name = "city", nullable = true, length = 2)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Transient
	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	@Transient
	public String getOpername() {
		return opername;
	}

	public void setOpername(String opername) {
		this.opername = opername;
	}

	@Transient
	public String getObjtypename() {
		return objtypename;
	}

	public void setObjtypename(String objtypename) {
		this.objtypename = objtypename;
	}

	@Transient
	public String getObjidsname() {
		return objidsname;
	}

	public void setObjidsname(String objidsname) {
		this.objidsname = objidsname;
	}
	
}