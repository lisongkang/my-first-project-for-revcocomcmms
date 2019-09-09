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
@Table(name="SYS_CATA_RELATION")
public class SysCataRelation extends PersistableEntity<Long> implements Persistable<Long>{

	private Long cid;
	
	private Long id;
	
	private String aName;
	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "aid", unique = true, length = 16)
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

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public String getaName() {
		return aName;
	}

	public void setaName(String aName) {
		this.aName = aName;
	}
	

}
