package com.maywide.biz.ass.daystat.entity;

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
@Table(name = "ASS_INDEX_DAYPROGRESS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AssIndexDayprogress extends PersistableEntity<Long> implements Persistable<Long> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2311511854066816696L;

	@MetaData(value = "统计序号")
    @EntityAutoCode
    private Long id;
	
	@MetaData(value = "统计日期YYYYMMDD")
    @EntityAutoCode(order = 1, listShow = true)
	private String tdate;
	
	@MetaData(value = "考核ID")
    @EntityAutoCode(order = 2, listShow = true)
	private Long assid;
	
	private String asscontent;
	
	@MetaData(value = "统计对象")
    @EntityAutoCode(order = 3, listShow = true)
	private String objtype;
	
	@MetaData(value = "对象ID")
    @EntityAutoCode(order = 4, listShow = true)
	private Long objid;
	
	private String objname;
	
	private String secondGridName; // 网格名称
	
	private String firstGridName; // 营维中心名称
	
	@MetaData(value = "任务总数")
    @EntityAutoCode(order = 5, listShow = true)
	private Double total;
	
	@MetaData(value = "当日完成数")
    @EntityAutoCode(order = 6, listShow = true)
	private Double curnum;
	
	@MetaData(value = "完成率")
    @EntityAutoCode(order = 7, listShow = true)
	private Double rate;
	
	private String ratename;
	
	private Long pgridid;
	
	private String pgrididname;
	
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "STAID", unique = true, length = 16)
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

	@Column(name = "TDATE", nullable = true, length = 6)
	public String getTdate() {
		return tdate;
	}

	public void setTdate(String tdate) {
		this.tdate = tdate;
	}
	
	@Column(name = "ASSID", nullable = true, length = 16)
	public Long getAssid() {
		return assid;
	}

	public void setAssid(Long assid) {
		this.assid = assid;
	}

	@Column(name = "OBJTYPE", nullable = true, length = 6)
	public String getObjtype() {
		return objtype;
	}

	public void setObjtype(String objtype) {
		this.objtype = objtype;
	}

	@Column(name = "OBJID", nullable = true, length = 16)
	public Long getObjid() {
		return objid;
	}

	public void setObjid(Long objid) {
		this.objid = objid;
	}

	@Column(name = "TOTAL", nullable = true, length = 16)
	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@Column(name = "CURNUM", nullable = true, length = 16)
	public Double getCurnum() {
		return curnum;
	}

	public void setCurnum(Double curnum) {
		this.curnum = curnum;
	}

	@Column(name = "RATE", nullable = true, length = 6)
	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@Transient
	public String getAsscontent() {
		return asscontent;
	}

	public void setAsscontent(String asscontent) {
		this.asscontent = asscontent;
	}

	@Transient
	public String getObjname() {
		return objname;
	}

	public void setObjname(String objname) {
		this.objname = objname;
	}

	@Transient
	public String getSecondGridName() {
		return secondGridName;
	}

	public void setSecondGridName(String secondGridName) {
		this.secondGridName = secondGridName;
	}

	@Transient
	public String getFirstGridName() {
		return firstGridName;
	}

	public void setFirstGridName(String firstGridName) {
		this.firstGridName = firstGridName;
	}

	@Transient
	public String getRatename() {
		if (!total.equals(0d)) {
			return ((int)((curnum*100) / total)) + "%";
		}
		return "";
	}

	public void setRatename(String ratename) {
		this.ratename = ratename;
	}
	
	@Transient
	public Long getPgridid() {
		return pgridid;
	}

	public void setPgridid(Long pgridid) {
		this.pgridid = pgridid;
	}

	@Transient
	public String getPgrididname() {
		return pgrididname;
	}

	public void setPgrididname(String pgrididname) {
		this.pgrididname = pgrididname;
	}

}
