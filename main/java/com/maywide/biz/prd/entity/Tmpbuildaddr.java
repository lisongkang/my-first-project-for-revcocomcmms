package com.maywide.biz.prd.entity;

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
@Table(name="TMP_BUILDADDR")
public class Tmpbuildaddr implements java.io.Serializable {
	
	private Long id;
 
	private Long custid;
	
	private Long houseid;
	
	private String level7;
	
	private String level8;
	
	private String level9;
	
	private String level10;
	
	private String level11;
	
	private Long operid;

	@Id	
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "RECID")
	public Long getId() {
		return id;
	}

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	public Long getHouseid() {
		return houseid;
	}

	public void setHouseid(Long houseid) {
		this.houseid = houseid;
	}

	public String getLevel7() {
		return level7;
	}

	public void setLevel7(String level7) {
		this.level7 = level7;
	}

	public String getLevel8() {
		return level8;
	}

	public void setLevel8(String level8) {
		this.level8 = level8;
	}

	public String getLevel9() {
		return level9;
	}

	public void setLevel9(String level9) {
		this.level9 = level9;
	}

	public String getLevel10() {
		return level10;
	}

	public void setLevel10(String level10) {
		this.level10 = level10;
	}

	public String getLevel11() {
		return level11;
	}

	public void setLevel11(String level11) {
		this.level11 = level11;
	}

	public Long getOperid() {
		return operid;
	}

	public void setOperid(Long operid) {
		this.operid = operid;
	}

	public void setId(Long id) {
		this.id = id;
	}








	}
	
