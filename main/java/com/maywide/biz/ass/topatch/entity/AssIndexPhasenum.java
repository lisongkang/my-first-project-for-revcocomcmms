package com.maywide.biz.ass.topatch.entity;

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

import com.maywide.core.annotation.MetaData;
import com.maywide.core.entity.PersistableEntity;
import com.maywide.core.entity.annotation.EntityAutoCode;


@Entity
@Table(name = "ASS_INDEX_PHASENUM")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AssIndexPhasenum extends PersistableEntity<Long> implements Persistable<Long> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1847597820496148711L;

	@MetaData(value = "记录序号")
    @EntityAutoCode
    private Long id;

	@MetaData(value = "任务类型")
    @EntityAutoCode(order = 1, listShow = true)
	private String ttype;
	
	@MetaData(value = "任务序号")
    @EntityAutoCode(order = 1, listShow = true)
	private Long taskid;
	
	@MetaData(value = "期序号")
    @EntityAutoCode(order = 2, listShow = true)
	private Long pno;
	
	private String pnoname;
	
    @MetaData(value = "当期目标数")
    @EntityAutoCode(order = 3, listShow = true)
	private Double assnum;
	
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recid", unique = true, length = 16)
	public Long getId() {
		return id;
	}

    public void setId(Long id) {
		this.id = id;
	}
    
	@Override
	@Transient
	public String getDisplay() {
		return "";
	}
	
	@Column(name = "taskid", nullable = true, length = 16)
	public Long getTaskid() {
		return taskid;
	}

	public void setTaskid(Long taskid) {
		this.taskid = taskid;
	}

	@Column(name = "pno", nullable = true, length = 16)
	public Long getPno() {
		return pno;
	}

	public void setPno(Long pno) {
		this.pno = pno;
	}

	@Column(name = "assnum", nullable = true, length = 18)
	public Double getAssnum() {
		return assnum;
	}

	public void setAssnum(Double assnum) {
		this.assnum = assnum;
	}

	@Column(name = "ttype", nullable = true, length = 1)
	public String getTtype() {
		return ttype;
	}

	public void setTtype(String ttype) {
		this.ttype = ttype;
	}

	@Transient
	public String getPnoname() {
		return pnoname;
	}

	public void setPnoname(String pnoname) {
		this.pnoname = pnoname;
	}
	
}