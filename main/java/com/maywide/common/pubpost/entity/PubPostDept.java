package com.maywide.common.pubpost.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;

import com.maywide.core.entity.PersistableEntity;

@Entity
@Table(name = "prv_pub_dept")
public class PubPostDept extends PersistableEntity<Long> implements Persistable<Long> {
	private Long id;
	private Long deptId;
	private String pubId;
	
	@Override
    @Transient
    public String getDisplay() {
        return null;
    }
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getPubId() {
		return pubId;
	}

	public void setPubId(String pubId) {
		this.pubId = pubId;
	}
}
