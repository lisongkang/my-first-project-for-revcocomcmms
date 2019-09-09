package com.maywide.biz.manage.smstemp.entity;

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

import com.maywide.core.entity.PersistableEntity;
/**
 * 
 *<p> 
 * 短信模板配置 实体类
 *<p>
 * Create at 2016-12-30
 *
 *@autor zhuangzhitang
 */
@Entity
@Table(name = "sys_smstemp_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysSmstempConfig  extends PersistableEntity<Long> implements Persistable<Long>{
    private Long id ;
    private String tname ;//模版标题
    private String tcontent ;//模版内容
    private Long opid ;//操作员
    private Long opdept ;//操作部门
    private Date optime ;//操作时间
    private Long tstatus ;//状态
    private Long auditid ;//审核人员
    private Long auditdept;//审核部门
    private Date audittime;//审核时间
    private String city ;//地市
    private String memo ;//
    
    //前台显示
    private String opidname;
    private String opdeptname;
    private String auditidname;
    private String auditdeptname;
    @Id	
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="TID")
	public Long getId() {
		return id;
	}
	
	@Override
	@Transient
	public String getDisplay() {
		return "";
	}

	@Column(name="TNAME")
	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	@Column(name="TCONTENT")
	public String getTcontent() {
		return tcontent;
	}

	public void setTcontent(String tcontent) {
		this.tcontent = tcontent;
	}

	@Column(name="OPID")
	public Long getOpid() {
		return opid;
	}

	public void setOpid(Long opid) {
		this.opid = opid;
	}

	@Column(name="OPDEPT")
	public Long getOpdept() {
		return opdept;
	}

	public void setOpdept(Long opdept) {
		this.opdept = opdept;
	}

	@Column(name="OPTIME")
	public Date getOptime() {
		return optime;
	}

	public void setOptime(Date optime) {
		this.optime = optime;
	}

	@Column(name="TSTATUS")
	public Long getTstatus() {
		return tstatus;
	}

	public void setTstatus(Long tstatus) {
		this.tstatus = tstatus;
	}

	@Column(name="AUDITID")
	public Long getAuditid() {
		return auditid;
	}

	public void setAuditid(Long auditid) {
		this.auditid = auditid;
	}

	@Column(name="AUDITDEPT")
	public Long getAuditdept() {
		return auditdept;
	}

	public void setAuditdept(Long auditdept) {
		this.auditdept = auditdept;
	}

	@Column(name="AUDITTIME")
	public Date getAudittime() {
		return audittime;
	}

	public void setAudittime(Date audittime) {
		this.audittime = audittime;
	}

	@Column(name="CITY")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name="MEMO")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Transient
	public String getOpidname() {
		return opidname;
	}

	public void setOpidname(String opidname) {
		this.opidname = opidname;
	}

	@Transient
	public String getOpdeptname() {
		return opdeptname;
	}

	public void setOpdeptname(String opdeptname) {
		this.opdeptname = opdeptname;
	}

	@Transient
	public String getAuditidname() {
		return auditidname;
	}

	public void setAuditidname(String auditidname) {
		this.auditidname = auditidname;
	}

	@Transient
	public String getAuditdeptname() {
		return auditdeptname;
	}

	public void setAuditdeptname(String auditdeptname) {
		this.auditdeptname = auditdeptname;
	}
   
	
	
}
