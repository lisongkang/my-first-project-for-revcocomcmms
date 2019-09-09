package com.maywide.biz.market.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import com.maywide.core.annotation.MetaData;
import com.maywide.core.entity.PersistableEntity;

@Entity
@Table(name = "BIZ_GRID")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Grid extends PersistableEntity<Long> implements Persistable<Long> {
	private Long gridid;
	@MetaData(value = "网格编码")
	private String gridcode;
	private String gridname;
	private String gridtype;
	private String city;
	
	@Override
    @Transient
    public String getDisplay() {
        return gridname;
    }
	
	@Id	
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "gridid")
	public Long getId() {
		return gridid;
	}
	
	public void setId(Long gridid) {
		this.gridid = gridid;
	}
	
	@Transient
    public Long getGridid() {
		return gridid;
	}
	public void setGridid(Long gridid) {
		this.gridid = gridid;
	}
	@Pattern(regexp = "^[A-Za-z0-9]+", message = "必须输入数字或字母")
	public String getGridcode() {
		return gridcode;
	}
	public void setGridcode(String gridcode) {
		this.gridcode = gridcode;
	}
	public String getGridname() {
		return gridname;
	}
	public void setGridname(String gridname) {
		this.gridname = gridname;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public String getGridtype() {
		return gridtype;
	}

	public void setGridtype(String gridtype) {
		this.gridtype = gridtype;
	}
	
}
