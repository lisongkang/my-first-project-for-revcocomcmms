package com.maywide.biz.ass.monstat.entity;

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
@Table(name = "ASS_INDEX_MONPROGRESS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AssIndexMonprogress extends PersistableEntity<Long> implements Persistable<Long> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2311511854066816696L;

	@MetaData(value = "统计序号")
    @EntityAutoCode
    private Long id;
	
	@MetaData(value = "统计月份YYYYMM")
    @EntityAutoCode(order = 1, listShow = true)
	private String tmonth;
	
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
	
	@MetaData(value = "累计完成数")
    @EntityAutoCode(order = 6, listShow = true)
	private Double comnum;
	
	@MetaData(value = "当月完成数")
    @EntityAutoCode(order = 7, listShow = true)
	private Double curnum;
	
	private List<String> secondGridids;
	
	private List<String> thirdPatchids;
	
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

	@Column(name = "TMONTH", nullable = true, length = 6)
	public String getTmonth() {
		return tmonth;
	}

	public void setTmonth(String tmonth) {
		this.tmonth = tmonth;
	}
	
	@Column(name = "ASSID", nullable = true, length = 6)
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

	@Column(name = "OBJID", nullable = true, length = 6)
	public Long getObjid() {
		return objid;
	}

	public void setObjid(Long objid) {
		this.objid = objid;
	}

	@Column(name = "TOTAL", nullable = true, length = 6)
	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@Column(name = "COMNUM", nullable = true, length = 6)
	public Double getComnum() {
		return comnum;
	}

	public void setComnum(Double comnum) {
		this.comnum = comnum;
	}

	@Column(name = "CURNUM", nullable = true, length = 6)
	public Double getCurnum() {
		return curnum;
	}

	public void setCurnum(Double curnum) {
		this.curnum = curnum;
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
	public List<String> getSecondGridids() {
		return secondGridids;
	}

	public void setSecondGridids(List<String> secondGridids) {
		this.secondGridids = secondGridids;
	}

	@Transient
	public List<String> getThirdPatchids() {
		return thirdPatchids;
	}

	public void setThirdPatchids(List<String> thirdPatchids) {
		this.thirdPatchids = thirdPatchids;
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
