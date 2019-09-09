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
@Table(name = "BIZ_REM_RULECFG")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BizRemRulecfg extends PersistableEntity<Long> implements Persistable<Long> {
	@MetaData(value = "预警任务标识")
	private Long id;
	
	@MetaData(value = "预警任务标识")
	@EntityAutoCode(order = 1, listShow = true)
	private Long remid;
	
	@MetaData(value = "预警条件")
	@EntityAutoCode(order = 2, listShow = true)
	private String tritype;
	
	@MetaData(value = "触发条件")
	@EntityAutoCode(order = 3, listShow = true)
	private String trivalues;
	
	@MetaData(value = "提醒次数")
	@EntityAutoCode(order = 4, listShow = true)
	private Long nums;
	
	@MetaData(value = "预警有效天数")
	@EntityAutoCode(order = 5, listShow = true)
	private Long elen;
	
	@MetaData(value = "是否自动确认")
	@EntityAutoCode(order = 6, listShow = true)
	private String iscfm;
	
	@MetaData(value = "录入时间")
	@EntityAutoCode(order = 7, listShow = true)
	private Date appdate;
	
	@MetaData(value = "录入工号")
	@EntityAutoCode(order = 8, listShow = true)
	private Long operid;
	
	@MetaData(value = "录入部门")
	@EntityAutoCode(order = 9, listShow = true)
	private Long deptid;
	
	@MetaData(value = "所属分公司")
	@EntityAutoCode(order = 10, listShow = true)
	private String city;
	
	private String deptname;
	private String opername;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cfgid", unique = true, length = 16)
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
	
	@Column(name = "remid", nullable = true, length = 30)
	public Long getRemid(){
		return remid;
	}
	
	public void setRemid(Long remid) {
		this.remid = remid;
	}

	public String getTritype() {
		return tritype;
	}

	public void setTritype(String tritype) {
		this.tritype = tritype;
	}

	public String getTrivalues() {
		return trivalues;
	}

	public void setTrivalues(String trivalues) {
		this.trivalues = trivalues;
	}

	public Long getNums() {
		return nums;
	}

	public void setNums(Long nums) {
		this.nums = nums;
	}

	public Long getElen() {
		return elen;
	}

	public void setElen(Long elen) {
		this.elen = elen;
	}

	public String getIscfm() {
		return iscfm;
	}

	public void setIscfm(String iscfm) {
		this.iscfm = iscfm;
	}

	public Date getAppdate() {
		return appdate;
	}

	public void setAppdate(Date appdate) {
		this.appdate = appdate;
	}

	public Long getOperid() {
		return operid;
	}

	public void setOperid(Long operid) {
		this.operid = operid;
	}

	public Long getDeptid() {
		return deptid;
	}

	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}

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
	
}