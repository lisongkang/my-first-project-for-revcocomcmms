package com.maywide.biz.ass.gridrpt.entity;

import java.util.Date;

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
@Table(name = "STL_GRID_REPORT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StlGridReport extends PersistableEntity<Long> implements Persistable<Long> {

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
	
	private String gridname;
	
	private String gridmanager;
	
	@MetaData(value = "片区网格")
    @EntityAutoCode(order = 3, listShow = true)
	private Long pgridid;	
	
	@MetaData(value = "业务类型")
    @EntityAutoCode(order = 4, listShow = true)
	private String permark;
	
	@MetaData(value = "总数")
    @EntityAutoCode(order = 5, listShow = true)
	private Long nums;
	
	@MetaData(value = "总金额")
    @EntityAutoCode(order = 6, listShow = true)
	private Double amount;
	
	@MetaData(value = "总结算金额")
    @EntityAutoCode(order = 7, listShow = true)
	private Double setamount;
	
	@MetaData(value = "总网格结算")
    @EntityAutoCode(order = 8, listShow = true)
	private Double netamount;	
	
    @MetaData(value = "生成时间")
    @EntityAutoCode(order = 9, listShow = true)
	private Date optime;
	
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

	@Column(name = "PERMARK", nullable = true, length = 1)
	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}

	@Column(name = "NUMS", nullable = true, length = 16)
	public Long getNums() {
		return nums;
	}

	public void setNums(Long nums) {
		this.nums = nums;
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

	@Column(name = "OPTIME", nullable = true)
	public Date getOptime() {
		return optime;
	}

	public void setOptime(Date optime) {
		this.optime = optime;
	}

	@Transient
	public String getGridmanager() {
		return gridmanager;
	}

	public void setGridmanager(String gridmanager) {
		this.gridmanager = gridmanager;
	}
	
	@Transient
	public String getGridname() {
		return gridname;
	}

	public void setGridname(String gridname) {
		this.gridname = gridname;
	}
	
}