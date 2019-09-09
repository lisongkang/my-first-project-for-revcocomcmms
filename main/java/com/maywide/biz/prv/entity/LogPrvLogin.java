package com.maywide.biz.prv.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "LOG_PRV_LOGIN")
@SuppressWarnings("serial")
public class LogPrvLogin implements java.io.Serializable {

	private Long id;
	private Long operid;
	private java.util.Date optime;
	private String ipaddr;
	private String macaddr;
	private String memo;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RECID", nullable = false, unique = true, insertable = true, updatable = true, precision = 12, scale = 0)
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

	public java.util.Date getOptime() {
		return optime;
	}

	public void setOptime(java.util.Date optime) {
		this.optime = optime;
	}

	public String getIpaddr() {
		return ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	public String getMacaddr() {
		return macaddr;
	}

	public void setMacaddr(String macaddr) {
		this.macaddr = macaddr;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
