package com.maywide.biz.inter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;

import com.maywide.core.entity.PersistableEntity;

@Entity
@Table(name="SYS_CATEGORY")
public class SysCategory extends PersistableEntity<Long> implements Persistable<Long>{

	private Long id;
	
	private String cCode;
	
	private String cName;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cid", unique = true, length = 16)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}

	@Override
	@Transient
	public String getDisplay() {
		return null;
	}

	public String getcCode() {
		return cCode;
	}

	public void setcCode(String cCode) {
		this.cCode = cCode;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

}
