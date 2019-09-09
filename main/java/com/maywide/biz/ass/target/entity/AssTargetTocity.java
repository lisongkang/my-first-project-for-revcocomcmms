package com.maywide.biz.ass.target.entity;

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
@Table(name = "ASS_TARGET_TOCITY")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AssTargetTocity extends PersistableEntity<Long> implements Persistable<Long> {
	

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8943450486747769072L;
	
	@MetaData(value = "考核指标id")
    @EntityAutoCode
    private Long id;
	
	@MetaData(value = "地市")
    @EntityAutoCode
	private String city;
	
	@MetaData(value = "原指标City")
    @EntityAutoCode
	private String assCity;
	
	@MetaData(value = "指标库id")
    @EntityAutoCode(order = 1, listShow = true)
	private Long assId;
    
    @MetaData(value = "操作人")
    @EntityAutoCode(order = 1, listShow = true)
	private Long operator;
    
    @MetaData(value = "创建时间")
    @EntityAutoCode(order = 1, listShow = true)
	private Date assdate;
    
    

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TOCITY_ID", unique = true)
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

	@Column(name = "CITY", nullable = true, length = 30)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "ASSID", nullable = false)
	public Long getAssId() {
		return assId;
	}

	public void setAssId(Long assId) {
		this.assId = assId;
	}

	@Column(name = "OPERATOR", nullable = true)
	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	@Column(name = "ASSDATE", nullable = true)
	public Date getAssdate() {
		return assdate;
	}

	public void setAssdate(Date assdate) {
		this.assdate = assdate;
	}

	@Column(name = "ASSCITY", nullable = true)
	public String getAssCity() {
		return assCity;
	}

	public void setAssCity(String assCity) {
		this.assCity = assCity;
	}

	
}
