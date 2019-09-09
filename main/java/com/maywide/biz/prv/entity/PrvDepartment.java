package com.maywide.biz.prv.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;

import com.maywide.core.cons.Datas;
import com.maywide.core.entity.PersistableEntity;

@Entity
@Table(name = "PRV_DEPARTMENT")
@SuppressWarnings("serial")
public class PrvDepartment extends PersistableEntity<Long> implements Persistable<Long> {

	private Long id;
	private String kind;
	private String name;
	private Long preid;
	private Long areaid;
	private String deplevel;
	private Long sortby;
	private String depno; // 部门编号
	private String city;
	
	@Override
    @Transient
    public String getDisplay() {
        return name;
    }

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DEPTID", nullable = false, unique = true, insertable = true, updatable = true, precision = 16, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "KIND", nullable = true, unique = false, insertable = true, updatable = true, length = 1)
	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	@Column(name = "NAME", nullable = true, unique = false, insertable = true, updatable = true, length = 64)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PREID", nullable = true, unique = false, insertable = true, updatable = true, precision = 16, scale = 0)
	public Long getPreid() {
		return preid;
	}

	public void setPreid(Long preid) {
		this.preid = preid;
	}

	@Column(name = "AREAID", nullable = true, unique = false, insertable = true, updatable = true, precision = 16, scale = 0)
	public Long getAreaid() {
		return areaid;
	}

	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}

	@Column(name = "DEPLEVEL", nullable = true, unique = false, insertable = true, updatable = true, length = 1)
	public String getDeplevel() {
		return deplevel;
	}

	public void setDeplevel(String deplevel) {
		this.deplevel = deplevel;
	}

	@Column(name = "SORTBY", nullable = true, unique = false, insertable = true, updatable = true, precision = 16, scale = 0)
	public Long getSortby() {
		return sortby;
	}

	public void setSortby(Long sortby) {
		this.sortby = sortby;
	}
	
	
	public String getDepno() {
		return depno;
	}

	public void setDepno(String depno) {
		this.depno = depno;
	}
	
	
	@Column(name = "CITY", nullable = true, unique = false, insertable = true, updatable = true, precision = 16, scale = 0)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Transient
	public String getPreDeptname() {
		if (this.preid == null || this.preid == -1 || Datas.getDepartmentMap()==null) return null;
		if (Datas.getDepartmentMap().get(this.preid) == null) {
			Datas.initDepartment();
			if (Datas.getDepartmentMap().get(this.preid) == null) {
				return null;
			}
		}
		return Datas.getDepartmentMap().get(this.preid).getName();
	}

}
