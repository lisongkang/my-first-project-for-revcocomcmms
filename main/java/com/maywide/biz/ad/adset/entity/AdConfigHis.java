package com.maywide.biz.ad.adset.entity;

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
 * 广告配置轨迹表
 *<p>
 * Create at 2017-3-20
 *
 *@autor zhuangzhitang
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "ad_config_his")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdConfigHis  extends PersistableEntity<Long> implements Persistable<Long>{
	
	private Long id;         
	private String adname;    //广告名称
	private String adsite;    //广告缩略图
	private Long adtype;      //广告类型
	private String adobj;     //广告对象
	private Long adstatus;	  //广告状态
	private Long optid;       //录入人员
	private Date opttime;     //录入时间
	private Long auditid;     //审核人员
	private Date audittime;   //审核时间
	private String city;      //
	private Long pri;         //优先级
	private  String memo;     //
	
	private Long opertype;         //操作类型（gcode=‘SQL_OPER_TYPE’）
	private Date opertime;         //操作时间
	private Long opertor;          //操作员
	private Long adid;             //影射‘广告配置表’id
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ADHISID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="ADNAME")
	public String getAdname() {
		return adname;
	}
	public void setAdname(String adname) {
		this.adname = adname;
	}
	
	@Column(name="ADSITE")
	public String getAdsite() {
		return adsite;
	}
	public void setAdsite(String adsite) {
		this.adsite = adsite;
	}
	
	@Column(name="ADTYPE")
	public Long getAdtype() {
		return adtype;
	}
	public void setAdtype(Long adtype) {
		this.adtype = adtype;
	}
	
	@Column(name="ADOBJ")
	public String getAdobj() {
		return adobj;
	}
	public void setAdobj(String adobj) {
		this.adobj = adobj;
	}
	
	@Column(name="ADSTATUS")
	public Long getAdstatus() {
		return adstatus;
	}
	public void setAdstatus(Long adstatus) {
		this.adstatus = adstatus;
	}
	
	@Column(name="OPTID")
	public Long getOptid() {
		return optid;
	}
	public void setOptid(Long optid) {
		this.optid = optid;
	}
	
	@Column(name="OPTTIME")
	public Date getOpttime() {
		return opttime;
	}
	public void setOpttime(Date opttime) {
		this.opttime = opttime;
	}
	
	@Column(name="AUDITID")
	public Long getAuditid() {
		return auditid;
	}
	public void setAuditid(Long auditid) {
		this.auditid = auditid;
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
	
	@Column(name="PRI")
	public Long getPri() {
		return pri;
	}
	public void setPri(Long pri) {
		this.pri = pri;
	}
	
	@Column(name="MEMO")
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Column(name="OPERTYPE")
	public Long getOpertype() {
		return opertype;
	}
	
	public void setOpertype(Long opertype) {
		this.opertype = opertype;
	}
	
	@Column(name="OPERTIME")
	public Date getOpertime() {
		return opertime;
	}
	public void setOpertime(Date opertime) {
		this.opertime = opertime;
	}
	
	@Column(name="OPERTOR")
	public Long getOpertor() {
		return opertor;
	}
	public void setOpertor(Long opertor) {
		this.opertor = opertor;
	}
	
	@Column(name="ADID")
	public Long getAdid() {
		return adid;
	}
	public void setAdid(Long adid) {
		this.adid = adid;
	}
	@Override
	@Transient
	public String getDisplay() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
