package com.maywide.biz.prd.entity;

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
@Table(name="sta_sales_point_oper")
@Cache(usage= CacheConcurrencyStrategy.READ_WRITE)
public class StaSalesPointOper extends PersistableEntity<Long> implements Persistable<Long>  {
 
	private Long id;
	private Long operid;
	private Long total;
	private Date sday;
	private String memo;
	private String city;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SID")
	public Long getId() {
		return id;
	}
	
	@Override
	@Transient
	public String getDisplay() {
		return "";
	}

	@Column(name = "OPERID")
	public Long getOperid() {
		return operid;
	}

	public void setOperid(Long operid) {
		this.operid = operid;
	}

	@Column(name = "TOTAL")
	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	@Column(name = "SDAY")
	public Date getSday() {
		return sday;
	}

	public void setSday(Date sday) {
		this.sday = sday;
	}

	@Column(name = "MEMO")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CITY")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	

}
