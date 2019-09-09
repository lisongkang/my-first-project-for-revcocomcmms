package com.maywide.biz.ass.gridrpt.entity;

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
@Table(name = "STL_GRID_DETAIL")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StlGridDetail extends PersistableEntity<Long> implements Persistable<Long> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2311511854066816696L;

	@MetaData(value = "统计序号")
    @EntityAutoCode
    private Long id;
	
	@MetaData(value = "数据年月")
    @EntityAutoCode(order = 1, listShow = true)
	private String feemonth;
	
	@MetaData(value = "结算网格")
    @EntityAutoCode(order = 2, listShow = true)
	private Long gridid;
	
	@MetaData(value = "片区网格")
    @EntityAutoCode(order = 3, listShow = true)
	private Long pgridid;	
	
	@MetaData(value = "用户")
    @EntityAutoCode(order = 4, listShow = true)
	private Long servid;
	
	@MetaData(value = "片区")
    @EntityAutoCode(order = 5, listShow = true)
	private Long patchid;
	
	private String patchname;

	@MetaData(value = "主副机类型")
    @EntityAutoCode(order = 6, listShow = true)
	private String servtype;
	
	private String servtypename;
	
	@MetaData(value = "业务类型")
    @EntityAutoCode(order = 7, listShow = true)
	private String permark;
	
	private String permarkname;
	
	@MetaData(value = "总金额")
    @EntityAutoCode(order = 6, listShow = true)
	private Double amount;
	
	@MetaData(value = "总结算金额")
    @EntityAutoCode(order = 7, listShow = true)
	private Double setamount;
	
	@MetaData(value = "总网格结算")
    @EntityAutoCode(order = 8, listShow = true)
	private Double netamount;	
	
	@MetaData(value = "所属规则")
    @EntityAutoCode(order = 5, listShow = true)
	private Long ruleid;
	
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RECID", unique = true, length = 16)
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

	@Column(name = "FEEMONTH", nullable = true, length = 6)
	public String getFeemonth() {
		return feemonth;
	}
	
	public void setFeemonth(String feemonth) {
		this.feemonth = feemonth;
	}

	@Column(name = "GRIDID", nullable = true, length = 16)
	public Long getGridid() {
		return gridid;
	}

	public void setGridid(Long gridid) {
		this.gridid = gridid;
	}

	@Column(name = "PGRIDID", nullable = true, length = 16)
	public Long getPgridid() {
		return pgridid;
	}

	public void setPgridid(Long pgridid) {
		this.pgridid = pgridid;
	}

	public Long getServid() {
		return servid;
	}

	@Column(name = "SERVID", nullable = true, length = 16)
	public void setServid(Long servid) {
		this.servid = servid;
	}

	@Column(name = "PATCHID", nullable = true, length = 16)
	public Long getPatchid() {
		return patchid;
	}

	public void setPatchid(Long patchid) {
		this.patchid = patchid;
	}

	@Column(name = "SERVTYPE", nullable = true, length = 1)
	public String getServtype() {
		return servtype;
	}

	public void setServtype(String servtype) {
		this.servtype = servtype;
	}

	@Column(name = "PERMARK", nullable = true, length = 1)
	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}

	@Column(name = "AMOUNT", nullable = true, length = 16)
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "SETAMOUNT", nullable = true, length = 16)
	public Double getSetamount() {
		return setamount;
	}

	public void setSetamount(Double setamount) {
		this.setamount = setamount;
	}

	@Column(name = "NETAMOUNT", nullable = true, length = 16)
	public Double getNetamount() {
		return netamount;
	}

	public void setNetamount(Double netamount) {
		this.netamount = netamount;
	}

	@Column(name = "RULEID", nullable = true, length = 16)
	public Long getRuleid() {
		return ruleid;
	}

	public void setRuleid(Long ruleid) {
		this.ruleid = ruleid;
	}

	@Transient
	public String getPatchname() {
		return patchname;
	}

	public void setPatchname(String patchname) {
		this.patchname = patchname;
	}

	@Transient
	public String getPermarkname() {
		return permarkname;
	}

	public void setPermarkname(String permarkname) {
		this.permarkname = permarkname;
	}

	public String getServtypename() {
		return servtypename;
	}

	public void setServtypename(String servtypename) {
		this.servtypename = servtypename;
	}
}