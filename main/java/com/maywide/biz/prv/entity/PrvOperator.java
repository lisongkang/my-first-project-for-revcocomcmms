package com.maywide.biz.prv.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import com.maywide.core.entity.PersistableEntity;

@Entity
@Table(name = "PRV_OPERATOR")
@SuppressWarnings("serial")
public class PrvOperator extends PersistableEntity<Long> implements Persistable<Long> {

	private Long id;
	private String loginname;
	private String name;
	private String passwd;
	private String status;
	private Double logtimes;
	private java.util.Date stime;
	private java.util.Date etime;
	private java.util.Date lasttime;

	private PrvOperinfo operinfo = new PrvOperinfo();
	
	private Set _in_id;

	@Id
	@GeneratedValue(generator = "ud")
	@GenericGenerator(name = "ud", strategy = "assigned")
	@Column(name = "OPERID", nullable = false, unique = true, insertable = true, updatable = true, precision = 12, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "LOGINNAME", nullable = false, unique = false, insertable = true, updatable = true, length = 64)
	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	@Column(name = "NAME", nullable = true, unique = false, insertable = true, updatable = true, length = 64)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PASSWD", nullable = true, unique = false, insertable = true, updatable = true, length = 128)
	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	@Column(name = "STATUS", nullable = true, unique = false, insertable = true, updatable = true, length = 1)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "LOGTIMES", nullable = true, unique = false, insertable = true, updatable = true, precision = 0, scale = -127)
	public Double getLogtimes() {
		return logtimes;
	}

	public void setLogtimes(Double logtimes) {
		this.logtimes = logtimes;
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

	@Column(name = "LASTTIME", nullable = true, unique = false, insertable = true, updatable = true)
	public java.util.Date getLasttime() {
		return lasttime;
	}

	public void setLasttime(java.util.Date lasttime) {
		this.lasttime = lasttime;
	}

	@OneToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
	public PrvOperinfo getOperinfo() {
		return operinfo;
	}

	public void setOperinfo(PrvOperinfo operinfo) {
		this.operinfo = operinfo;
	}
	
	@Override
    @Transient
    public String getDisplay() {
        return loginname;
    }

	@Transient
	public Set get_in_id() {
		return _in_id;
	}

	public void set_in_id(Set inId) {
		_in_id = inId;
	}
}
