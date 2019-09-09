package com.maywide.biz.market.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import com.maywide.core.entity.PersistableEntity;

@Entity
@Table(name = "BIZ_TRACE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Trace extends PersistableEntity<Long> implements Persistable<Long> {
	private Long id;
	private Long operid;
	@Temporal(TemporalType.TIMESTAMP)
	private Date optime;
	private Long menuid;
	private String optype;
	private String opinfo;
	private String resultinfo;
	private Long orderid;
	private String city;
	private Long accesslogid;
	
	@Override
    @Transient
    public String getDisplay() {
        return null;
    }
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "traceid")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOperid() {
		return operid;
	}
	public void setOperid(Long operid) {
		this.operid = operid;
	}
	public Date getOptime() {
		return optime;
	}
	public void setOptime(Date optime) {
		this.optime = optime;
	}
	public Long getMenuid() {
		return menuid;
	}
	public void setMenuid(Long menuid) {
		this.menuid = menuid;
	}
	public String getOptype() {
		return optype;
	}
	public void setOptype(String optype) {
		this.optype = optype;
	}
	public String getOpinfo() {
		return opinfo;
	}
	public void setOpinfo(String opinfo) {
		this.opinfo = opinfo;
	}
	public String getResultinfo() {
		return resultinfo;
	}
	public void setResultinfo(String resultinfo) {
		this.resultinfo = resultinfo;
	}
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Long getAccesslogid() {
		return accesslogid;
	}
	public void setAccesslogid(Long accesslogid) {
		this.accesslogid = accesslogid;
	}
}
