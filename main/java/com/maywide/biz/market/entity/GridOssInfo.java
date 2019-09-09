package com.maywide.biz.market.entity;

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
@Table(name = "GRID_OSS_INFO")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class GridOssInfo extends PersistableEntity<Long> implements Persistable<Long> {
	private Long id;
	private String gridid;
	private String gridname;
	private String status;
	private Date created;
	private Date updatetime;
	
	@Override
    @Transient
    public String getDisplay() {
        return gridname;
    }
	
	@Id	
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "recid")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getGridid() {
		return gridid;
	}

	public void setGridid(String gridid) {
		this.gridid = gridid;
	}

	public String getGridname() {
		return gridname;
	}

	public void setGridname(String gridname) {
		this.gridname = gridname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
}
