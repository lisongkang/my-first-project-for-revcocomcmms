package com.maywide.biz.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;

import com.maywide.core.entity.PersistableEntity;

@Entity
@Table(name = "PRV_SYSPARAM")
@SuppressWarnings("serial")
public class PrvSysparam extends PersistableEntity<Long> implements Persistable<Long> {

	private Long id;
	private String gcode;
	private String mcode;
	private String mname;
	private String data;
	private String fmt;
	private Long sort;
	private Long scope;
	private String pinyin;
	private Long _ne_id;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PARAMID", nullable = false, unique = true, insertable = true, updatable = true, precision = 12, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Transient
    public String getDisplay() {
        return mname;
    }

	@Column(name = "GCODE")
	public String getGcode() {
		return gcode;
	}

	public void setGcode(String gcode) {
		this.gcode = gcode;
	}

	@Column(name = "MCODE")
	public String getMcode() {
		return mcode;
	}

	public void setMcode(String mcode) {
		this.mcode = mcode;
	}

	@Column(name = "MNAME", nullable = true, unique = false, insertable = true, updatable = true, length = 40)
	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	@Column(name = "DATA", nullable = true, unique = false, insertable = true, updatable = true, length = 1024)
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Column(name = "FMT", nullable = true, unique = false, insertable = true, updatable = true, length = 512)
	public String getFmt() {
		return fmt;
	}

	public void setFmt(String fmt) {
		this.fmt = fmt;
	}

	@Column(name = "SORT", nullable = true, unique = false, insertable = true, updatable = true, precision = 12, scale = 0)
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	@Column(name = "SCOPE", nullable = true, unique = false, insertable = true, updatable = true, precision = 12, scale = 0)
	public Long getScope() {
		return scope;
	}

	public void setScope(Long scope) {
		this.scope = scope;
	}
	@Transient
	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	@Transient
	public Long get_ne_id() {
		return _ne_id;
	}

	public void set_ne_id(Long _ne_id) {
		this._ne_id = _ne_id;
	}
}
