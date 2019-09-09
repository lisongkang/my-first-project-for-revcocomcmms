package com.maywide.biz.ass.topatch.entity;

import java.util.Date;
import java.util.List;

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
@Table(name = "ASS_INDEX_TOPATCH")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AssIndexTopatch extends PersistableEntity<Long> implements Persistable<Long> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1847597820496148711L;

	@MetaData(value = "任务序号id")
    @EntityAutoCode
    private Long id;
	
	@MetaData(value = "考核指标id")
    @EntityAutoCode(order = 1, listShow = true)
	private Long assid;
	
	@MetaData(value = "片区id")
    @EntityAutoCode(order = 2, listShow = true)
	private Long patchid;
	
	@MetaData(value = "片区对应网格")
    @EntityAutoCode(order = 3, listShow = true)
	private Long pgridid;
	
	@MetaData(value = "下达任务的网格")
    @EntityAutoCode(order = 4, listShow = true)
	private Long tgridid;
	
	private String gridname;
	
	@MetaData(value = "下达任务的网格")
	private String patchName;
	
	private List<String> patchids;
	
	@MetaData(value = "考核起始日期")
    @EntityAutoCode(order = 5, listShow = true)
	private Date bdate;
	
	@MetaData(value = "接受任务数")
    @EntityAutoCode(order = 6, listShow = true)
	private Double revnum;
	
	@MetaData(value = "完成目标方式")
    @EntityAutoCode(order = 7, listShow = true)
	private String mode;
	
	private String modename;
	
	@MetaData(value = "每期目标数")
    @EntityAutoCode(order = 8, listShow = true)
	private Double assnum;
	
	// 分期目标数的流水号
	private String serialno;
	
	@MetaData(value = "操作时间")
    @EntityAutoCode(order = 9, listShow = true)
	private Date assdate;
	
	@MetaData(value = "操作部门")
    @EntityAutoCode(order = 10, listShow = true)
	private Long depart;
	
	@MetaData(value = "操作员")
    @EntityAutoCode(order = 11, listShow = true)
	private Long operator;
	
	private String asscontent;
	private String assStoreUnit;
	
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "taskid", unique = true, length = 16)
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

	@Column(name = "assid", nullable = true, length = 16)
	public Long getAssid() {
		return assid;
	}

	public void setAssid(Long assid) {
		this.assid = assid;
	}
	
	@Column(name = "patchid", nullable = true, length = 16)
	public Long getPatchid() {
		return patchid;
	}

	public void setPatchid(Long patchid) {
		this.patchid = patchid;
	}

	@Column(name = "pgridid", nullable = true, length = 16)
	public Long getPgridid() {
		return pgridid;
	}

	public void setPgridid(Long pgridid) {
		this.pgridid = pgridid;
	}

	@Column(name = "tgridid", nullable = true, length = 16)
	public Long getTgridid() {
		return tgridid;
	}

	public void setTgridid(Long tgridid) {
		this.tgridid = tgridid;
	}
	
	@Transient
	public String getPatchName() {
		return patchName;
	}

	public void setPatchName(String patchName) {
		this.patchName = patchName;
	}
	
	@Transient
	public List<String> getPatchids() {
		return patchids;
	}

	public void setPatchids(List<String> patchids) {
		this.patchids = patchids;
	}

	@Column(name = "bdate", nullable = true)
	public Date getBdate() {
		return bdate;
	}

	public void setBdate(Date bdate) {
		this.bdate = bdate;
	}

	@Column(name = "revnum", nullable = true)
	public Double getRevnum() {
		return revnum;
	}

	public void setRevnum(Double revnum) {
		this.revnum = revnum;
	}

	@Column(name = "mode", nullable = true, length = 1)
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	@Column(name = "assnum", nullable = true)
	public Double getAssnum() {
		return assnum;
	}

	public void setAssnum(Double assnum) {
		this.assnum = assnum;
	}

	@Transient
	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	@Column(name = "assdate", nullable = true)
	public Date getAssdate() {
		return assdate;
	}

	public void setAssdate(Date assdate) {
		this.assdate = assdate;
	}

	@Column(name = "depart", nullable = true, length = 16)
	public Long getDepart() {
		return depart;
	}

	public void setDepart(Long depart) {
		this.depart = depart;
	}

	@Column(name = "operator", nullable = true, length = 16)
	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	@Transient
	public String getModename() {
		return modename;
	}

	public void setModename(String modename) {
		this.modename = modename;
	}

	@Transient
	public String getGridname() {
		return gridname;
	}

	public void setGridname(String gridname) {
		this.gridname = gridname;
	}
	
	@Transient
    public String getAsscontent() {
        return asscontent;
    }

    public void setAsscontent(String asscontent) {
        this.asscontent = asscontent;
    }

    @Transient
    public String getAssStoreUnit() {
        return assStoreUnit;
    }

    public void setAssStoreUnit(String assStoreUnit) {
        this.assStoreUnit = assStoreUnit;
    }
}
