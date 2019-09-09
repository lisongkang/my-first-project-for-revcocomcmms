package com.maywide.biz.inter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;

import com.maywide.core.entity.PersistableEntity;

@Entity
@Table(name = "WFL_REASON")
public class WflReason extends PersistableEntity<Integer> implements Persistable<Integer> {

	private static final long serialVersionUID = 1;

	private Integer id;
	private String reasonname;

	@Override
	@Transient
	public String getDisplay() {
		return null;
	}

	@Id
	@Column(name = "reasoncode", unique = true, length = 16)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Transient
	public Integer getReasoncode() {
		return id;
	}

	public void setReasoncode(Integer reasoncode) {
		this.id = reasoncode;
	}

	public String getReasonname() {
		return reasonname;
	}

	public void setReasonname(String reasonname) {
		this.reasonname = reasonname;
	}

}
