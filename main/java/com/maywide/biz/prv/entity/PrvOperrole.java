package com.maywide.biz.prv.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.domain.Persistable;

import com.maywide.core.cons.Datas;
import com.maywide.core.entity.PersistableEntity;

@Entity
@Table(name = "PRV_OPERROLE")
@SuppressWarnings("serial")
public class PrvOperrole extends PersistableEntity<Long> implements Persistable<Long> { 
	private Long id;
	private Long operid;
	private Long roleid;
	private Long priority;
	private java.util.Date stime;
	private java.util.Date etime;
	private PrvDepartment department = new PrvDepartment();
    //private Long deptid;
    private Long _ne_id;
    
    @Override
    @Transient
    public String getDisplay() {
        return id.toString();
    }
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OPERROLEID", nullable = false, unique = true, insertable = true, updatable = true, precision = 12, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "OPERID", nullable = true, unique = false, insertable = true, updatable = true, precision = 12, scale = 0)
	public Long getOperid() {
		return operid;
	}

	public void setOperid(Long operid) {
		this.operid = operid;
	}

	@Column(name = "ROLEID", nullable = true, unique = true, insertable = true, updatable = true, precision = 12, scale = 0)
	public Long getRoleid() {
		return roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

	@Column(name = "PRIORITY", nullable = true, unique = false, insertable = true, updatable = true, precision = 0, scale = -127)
	public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	@Column(name = "STIME", nullable = true, unique = false, insertable = true, updatable = true)
	public java.util.Date getStime() {
		return stime;
	}

	public void setStime(java.util.Date stime) {
		this.stime = stime;
	}

	@Column(name = "ETIME", nullable = true, unique = false, insertable = true, updatable = true)
	public java.util.Date getEtime() {
		return etime;
	}

	public void setEtime(java.util.Date etime) {
		this.etime = etime;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "deptid", nullable = false)
    @NotFound(action=NotFoundAction.IGNORE)
	public PrvDepartment getDepartment() {
		return department;
	}

	public void setDepartment(PrvDepartment department) {
		this.department = department;
	}

	@Transient
	public Long get_ne_id() {
		return _ne_id;
	}

	public void set_ne_id(Long neId) {
		_ne_id = neId;
	}
}
