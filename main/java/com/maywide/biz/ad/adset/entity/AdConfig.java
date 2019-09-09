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
 * 广告配置
 *<p>
 * Create at 2017-3-20
 *
 *@autor zhuangzhitang
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "ad_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdConfig extends PersistableEntity<Long> implements Persistable<Long> {
	
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
	
	/**非数据库影射属性**/
	private Long opertype;         //操作类型（gcode=‘SQL_OPER_TYPE’）
	private String optname; //录入操作员姓名
	private String auditname; //审核操作员姓名
	private String knowname; //商品库名
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ADID")
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
	
	@Override
	@Transient
	public String getDisplay() {
		return "";
	}
	
	@Transient
	public Long getOpertype() {
		return opertype;
	}
	public void setOpertype(Long opertype) {
		this.opertype = opertype;
	}
	
	@Transient
	public String getOptname() {
		return optname;
	}
	public void setOptname(String optname) {
		this.optname = optname;
	}
	
	@Transient
	public String getAuditname() {
		return auditname;
	}
	public void setAuditname(String auditname) {
		this.auditname = auditname;
	}
	
	@Transient
	public String getKnowname() {
		return knowname;
	}
	public void setKnowname(String knowname) {
		this.knowname = knowname;
	}
	
	
}
