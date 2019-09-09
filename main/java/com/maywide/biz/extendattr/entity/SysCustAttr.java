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
@Table(name = "sys_cust_attr")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysCustAttr extends PersistableEntity<Long> implements Persistable<Long> {
    private Long id;
    private Long custid;
    private Long attrruleid;
    private String attrcode;
    private String attrvalue;
    private Long createoper;
    private Date createtime;
    private Long updateoper;
    private Date updatetime;
   
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
	
	public Long getCustid() {
		return custid;
	}
	public void setCustid(Long custid) {
		this.custid = custid;
	}
	public Long getAttrruleid() {
		return attrruleid;
	}
	public void setAttrruleid(Long attrruleid) {
		this.attrruleid = attrruleid;
	}
	public String getAttrcode() {
		return attrcode;
	}
	public void setAttrcode(String attrcode) {
		this.attrcode = attrcode;
	}
	public String getAttrvalue() {
		return attrvalue;
	}
	public void setAttrvalue(String attrvalue) {
		this.attrvalue = attrvalue;
	}
	public Long getCreateoper() {
		return createoper;
	}
	public void setCreateoper(Long createoper) {
		this.createoper = createoper;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Long getUpdateoper() {
		return updateoper;
	}
	public void setUpdateoper(Long updateoper) {
		this.updateoper = updateoper;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
   
}
