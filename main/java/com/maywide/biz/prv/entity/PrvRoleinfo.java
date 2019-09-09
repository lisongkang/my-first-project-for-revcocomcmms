package com.maywide.biz.prv.entity;

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
@Table(name = "PRV_ROLEINFO")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PrvRoleinfo extends PersistableEntity<Long> implements Persistable<Long> {

	private Long id;
	private String name;
	private java.util.Date stime;
	private java.util.Date etime;
	private Long creator;
	private String memo;
	private String areas;
	private String rolelevel;
	private String atype;//添加网格用户跟集客用户的区别

	//不是数据库字段
	private String loginRolelevel;
	
	@Override
    @Transient
    public String getDisplay() {
        return name;
    }

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROLEID", nullable = false, unique = true, insertable = true, updatable = true, precision = 12, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NAME", nullable = false, unique = false, insertable = true, updatable = true, length = 32)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "STIME", nullable = false, unique = false, insertable = true, updatable = true)
	public java.util.Date getStime() {
		return stime;
	}

	public void setStime(java.util.Date stime) {
		this.stime = stime;
	}

	@Column(name = "ETIME", nullable = false, unique = false, insertable = true, updatable = true)
	public java.util.Date getEtime() {
		return etime;
	}

	public void setEtime(java.util.Date etime) {
		this.etime = etime;
	}

	@Column(name = "CREATOR", nullable = true, unique = false, insertable = true, updatable = true, precision = 12, scale = 0)
	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	@Column(name = "MEMO", nullable = true, unique = false, insertable = true, updatable = true, length = 255)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "AREAS", nullable = true, unique = false, insertable = true, updatable = true , length = 2048)
	public String getAreas(){
		return areas;
	}

	public void setAreas(String areas){
		this.areas = areas;
	}

	@Column(name = "ROLELEVEL", nullable = true, unique = false, insertable = true, updatable = true, length = 5)
	public String getRolelevel() {
		return rolelevel;
	}

	public void setRolelevel(String rolelevel) {
		this.rolelevel = rolelevel;
	}

	@Column(name = "ATYPE", nullable = true, unique = false, insertable = true, updatable = true, length = 5)
	public String getAtype() {
		return atype;
	}

	public void setAtype(String atype) {
		this.atype = atype;
	}

	@Transient
	public String getLoginRolelevel() {
		return loginRolelevel;
	}

	public void setLoginRolelevel(String loginRolelevel) {
		this.loginRolelevel = loginRolelevel;
	}
}
