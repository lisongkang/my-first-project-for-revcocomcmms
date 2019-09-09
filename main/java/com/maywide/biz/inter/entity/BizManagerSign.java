package com.maywide.biz.inter.entity;

import java.util.Date;

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
@Table(name = "BIZ_MANAGER_SIGN")
public class BizManagerSign extends PersistableEntity<Long> implements Persistable<Long> {

	private static final long serialVersionUID = -3250951506359771856L;

	private Long id;
	private String keyno;
	private String custname;
	private String addr;
	private Date optime;
	private Long opid;
	private Long opdeptid;

	@Override
	@Transient
	public String getDisplay() {
		return null;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recid", unique = true, length = 16)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Transient
	public Long getRecid() {
		return id;
	}

	public void setRecid(Long recid) {
		this.id = recid;
	}

	public String getKeyno() {
		return keyno;
	}

	public void setKeyno(String keyno) {
		this.keyno = keyno;
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Date getOptime() {
		return optime;
	}

	public void setOptime(Date optime) {
		this.optime = optime;
	}

	public Long getOpid() {
		return opid;
	}

	public void setOpid(Long opid) {
		this.opid = opid;
	}

	public Long getOpdeptid() {
		return opdeptid;
	}

	public void setOpdeptid(Long opdeptid) {
		this.opdeptid = opdeptid;
	}

}
