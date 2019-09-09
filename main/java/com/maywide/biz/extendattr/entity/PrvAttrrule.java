package com.maywide.biz.extendattr.entity;

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
@Entity
@Table(name = "prv_attrrule")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PrvAttrrule extends PersistableEntity<Long> implements Persistable<Long> {
	private Long id ;
	private String classlib;
	private String classes; //属性列名 class是关键字，用classes代替
	private String subclass;
	private String attrcode;
	private String attrname;
	private String valuesrc;
	private String valueparam;
	private String ifnecessary;
	private String defaultvalue;
	private String scopeflag;
	private String minvalue;
	private String maxvalue;
	private Long createoper;
	private Date  createtime;
	private Long updateoper;
	private Date updatetime;
	private String groupby;
	private Long orderby;
	private String instanceflag;
	private String firstputflag;
	private String uniqueflag;
	
	private String city;//地市
	private String sysAttrAreaId;//区域表id
	
    @Id	
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RECID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Transient
    public String getDisplay() {
        return null;
    }
	 
	@Column(name="Classlib")
	public String getClasslib() {
		return classlib;
	}
	
	public void setClasslib(String classlib) {
		this.classlib = classlib;
	}
	
	@Column(name="CLASS")
	public String getClasses() {
		return classes;
	}
	
	public void setClasses(String classes) {
		this.classes = classes;
	}
	
	@Column(name="SUBCLASS")
	public String getSubclass() {
		return subclass;
	}
	
	public void setSubclass(String subclass) {
		this.subclass = subclass;
	}
	
	@Column(name="ATTRCODE")
	public String getAttrcode() {
		return attrcode;
	}
	
	public void setAttrcode(String attrcode) {
		this.attrcode = attrcode;
	}
	
	@Column(name="ATTRNAME")
	public String getAttrname() {
		return attrname;
	}
	
	public void setAttrname(String attrname) {
		this.attrname = attrname;
	}
	
	@Column(name="VALUESRC")
	public String getValuesrc() {
		return valuesrc;
	}
	
	public void setValuesrc(String valuesrc) {
		this.valuesrc = valuesrc;
	}
	
	@Column(name="VALUEPARAM")
	public String getValueparam() {
		return valueparam;
	}
	
	public void setValueparam(String valueparam) {
		this.valueparam = valueparam;
	}
	
	@Column(name="IFNECESSARY")
	public String getIfnecessary() {
		return ifnecessary;
	}
	
	public void setIfnecessary(String ifnecessary) {
		this.ifnecessary = ifnecessary;
	}
	
	@Column(name="DEFAULTVALUE")
	public String getDefaultvalue() {
		return defaultvalue;
	}
	
	public void setDefaultvalue(String defaultvalue) {
		this.defaultvalue = defaultvalue;
	}
	
	@Column(name="SCOPEFLAG")
	public String getScopeflag() {
		return scopeflag;
	}
	
	public void setScopeflag(String scopeflag) {
		this.scopeflag = scopeflag;
	}
	
	@Column(name="MINVALUE")
	public String getMinvalue() {
		return minvalue;
	}
	
	public void setMinvalue(String minvalue) {
		this.minvalue = minvalue;
	}
	
	@Column(name="`MAXVALUE`")
	public String getMaxvalue() {
		return maxvalue;
	}
	
	public void setMaxvalue(String maxvalue) {
		this.maxvalue = maxvalue;
	}
	
	@Column(name="CREATEOPER")
	public Long getCreateoper() {
		return createoper;
	}
	
	public void setCreateoper(Long createoper) {
		this.createoper = createoper;
	}
	
	@Column(name="CREATETIME")
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	@Column(name="UPDATEOPER")
	public Long getUpdateoper() {
		return updateoper;
	}
	
	public void setUpdateoper(Long updateoper) {
		this.updateoper = updateoper;
	}
	
	@Column(name="UPDATETIME")
	public Date getUpdatetime() {
		return updatetime;
	}
	
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	@Column(name="GROUPBY")
	public String getGroupby() {
		return groupby;
	}
	
	public void setGroupby(String groupby) {
		this.groupby = groupby;
	}
	
	@Column(name="ORDERBY")
	public Long getOrderby() {
		return orderby;
	}
	
	public void setOrderby(Long orderby) {
		this.orderby = orderby;
	}
	
	@Column(name="UNIQUEFLAG")
	public String getUniqueflag() {
		return uniqueflag;
	}
	
	public void setUniqueflag(String uniqueflag) {
		this.uniqueflag = uniqueflag;
	}

	@Column(name="INSTANCEFLAG")
	public String getInstanceflag() {
		return instanceflag;
	}

	public void setInstanceflag(String instanceflag) {
		this.instanceflag = instanceflag;
	}

	@Column(name="FIRSTPUTFLAG")
	public String getFirstputflag() {
		return firstputflag;
	}

	public void setFirstputflag(String firstputflag) {
		this.firstputflag = firstputflag;
	}

	@Transient
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Transient
	public String getSysAttrAreaId() {
		return sysAttrAreaId;
	}

	public void setSysAttrAreaId(String sysAttrAreaId) {
		this.sysAttrAreaId = sysAttrAreaId;
	}
	
	
	
}
