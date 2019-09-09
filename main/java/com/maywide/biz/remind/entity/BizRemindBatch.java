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
@Table(name = "BIZ_REMIND_BATCH")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BizRemindBatch extends PersistableEntity<Long> implements Persistable<Long> {
	@MetaData(value = "批次标识")
	private Long id;
	
	@MetaData(value = "预警任务标识")
	@EntityAutoCode(order = 1, listShow = true)
	private Long remid;
	
	@MetaData(value = "预警条件")
	@EntityAutoCode(order = 2, listShow = true)
	private String objtype;

	@MetaData(value = "预警生成日期")
	@EntityAutoCode(order = 3, listShow = true)
	private Date buildate;
	
	@MetaData(value = "有效期限")
	@EntityAutoCode(order = 4, listShow = true)
	private Date edate;
	
	@MetaData(value = "是否自动确认")
	@EntityAutoCode(order = 5, listShow = true)
	private String iscfm;
	
	@MetaData(value = "确认部门")
	@EntityAutoCode(order = 6, listShow = true)
	private Long deptid;
	
	@MetaData(value = "确认操作员")
	@EntityAutoCode(order = 7, listShow = true)
	private Long operator;
	
	@MetaData(value = "确认时间")
	@EntityAutoCode(order = 8, listShow = true)
	private Date optime;
	
	private String description;
	private String objtypename;
	private String deptname;
	private String opername;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "batid", unique = true, length = 16)
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
	
	@Column(name = "remid", nullable = true, length = 16)
	public Long getRemid(){
		return remid;
	}
	
	public void setRemid(Long remid) {
		this.remid = remid;
	}

	@Column(name = "objtype", nullable = true, length = 1)
	public String getObjtype() {
		return objtype;
	}

	public void setObjtype(String objtype) {
		this.objtype = objtype;
	}

	@Column(name = "buildate", nullable = true)
	public Date getBuildate() {
		return buildate;
	}

	public void setBuildate(Date buildate) {
		this.buildate = buildate;
	}

	@Column(name = "edate", nullable = true)
	public Date getEdate() {
		return edate;
	}

	public void setEdate(Date edate) {
		this.edate = edate;
	}

	@Column(name = "iscfm", nullable = true, length = 1)
	public String getIscfm() {
		return iscfm;
	}

	public void setIscfm(String iscfm) {
		this.iscfm = iscfm;
	}

	@Column(name = "deptid", nullable = true, length = 16)
	public Long getDeptid() {
		return deptid;
	}

	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}

	@Column(name = "operator", nullable = true, length = 1)
	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	@Column(name = "optime", nullable = true)
	public Date getOptime() {
		return optime;
	}

	public void setOptime(Date optime) {
		this.optime = optime;
	}

	@Transient
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Transient
	public String getObjtypename() {
		return objtypename;
	}

	public void setObjtypename(String objtypename) {
		this.objtypename = objtypename;
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
