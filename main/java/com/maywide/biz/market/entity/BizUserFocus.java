package com.maywide.biz.market.entity;

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
@Table(name = "biz_user_focus")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BizUserFocus extends PersistableEntity<Long> implements Persistable<Long> {
	private Long id;
	private Long operid;
	private Long custid;
	
	@Override
    @Transient
    public String getDisplay() {
        return null;
    }

	@Id	
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}
}
