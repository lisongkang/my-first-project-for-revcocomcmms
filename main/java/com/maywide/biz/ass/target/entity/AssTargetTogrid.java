package com.maywide.biz.ass.target.entity;

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
@Table(name = "ASS_TARGET_TOGRID")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AssTargetTogrid extends PersistableEntity<Long> implements Persistable<Long> {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8943450486747769072L;
	
	@MetaData(value = "id")
    @EntityAutoCode
    private Long id;
	
	@MetaData(value = "地市")
    @EntityAutoCode
	private String city;
	
	@MetaData(value = "指标库id")
    @EntityAutoCode(order = 1, listShow = true )
	private Long assId;
	
	@MetaData(value = "网格id")
    @EntityAutoCode(order = 1, listShow = true)
	private Long gridId;
	
	
	@MetaData(value = "指标考核值")
    @EntityAutoCode(order = 1, listShow = true)
	private String assNum;
	
	
	@MetaData(value = "指标考核单位")
    @EntityAutoCode(order = 1, listShow = true)
	private String assnumUnit;
	
	@MetaData(value = "当前值")
    @EntityAutoCode(order = 1, listShow = true)
	private String currentValue;
	
	@MetaData(value = "当前值更新时间")
    @EntityAutoCode(order = 1, listShow = true)
	private Date optimeValue;
	
	@MetaData(value = "考核周期")
    @EntityAutoCode(order = 1, listShow = true)
	private Date cycleNum;
	
	
	@MetaData(value = "状态")
    @EntityAutoCode(order = 1, listShow = true)
	private Integer status;
    
    @MetaData(value = "操作人")
    @EntityAutoCode(order = 1, listShow = true)
	private Long operator;
    
    @MetaData(value = "创建时间")
    @EntityAutoCode(order = 1, listShow = true)
	private Date optime;
    
    
    @MetaData(value = "是否已删除")
    @EntityAutoCode(order = 1, listShow = true)
	private Integer isDel;
    
    
    @MetaData(value = "当前评分")
    @EntityAutoCode(order = 1, listShow = true)
	private String currGrade;
    
    @MetaData(value = "最终评分")
    @EntityAutoCode(order = 1, listShow = true)
	private String finalGrade;
    
    @MetaData(value = "字段更新时间")
    @EntityAutoCode(order = 1, listShow = false)
    private String kpidate;

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TOGRID_ID", unique = true)
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

	@Column(name = "CITY", nullable = false, length = 30)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "ASSID", nullable = false)
	public Long getAssId() {
		return assId;
	}

	public void setAssId(Long assId) {
		this.assId = assId;
	}

	@Column(name = "OPERATOR", nullable = true)
	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	@Column(name = "GRIDID", nullable = false)
	public Long getGridId() {
		return gridId;
	}

	public void setGridId(Long gridId) {
		this.gridId = gridId;
	}

	@Column(name = "ASSNUM", nullable = false)
	public String getAssNum() {
		return assNum;
	}

	public void setAssNum(String assNum) {
		this.assNum = assNum;
	}

	@Column(name = "ASSNUM_UNIT", nullable = true)
	public String getAssnumUnit() {
		return assnumUnit;
	}

	public void setAssnumUnit(String assnumUnit) {
		this.assnumUnit = assnumUnit;
	}

	@Column(name = "CURRENT_VALUE", nullable = true)
	public String getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}

	@Column(name = "OPTIME_VALUE", nullable = true)
	public Date getOptimeValue() {
		return optimeValue;
	}

	public void setOptimeValue(Date optimeValue) {
		this.optimeValue = optimeValue;
	}

	@Column(name = "CYCLENUM", nullable = false)
	public Date getCycleNum() {
		return cycleNum;
	}

	public void setCycleNum(Date cycleNum) {
		this.cycleNum = cycleNum;
	}

	@Column(name = "STATUS", nullable = false)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "OPTIME", nullable = true)
	public Date getOptime() {
		return optime;
	}

	public void setOptime(Date optime) {
		this.optime = optime;
	}

	
	@Column(name = "ISDEL", nullable = true)
	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	@Column(name = "CURR_GRADE", nullable = false)
	public String getCurrGrade() {
		return currGrade;
	}

	public void setCurrGrade(String currGrade) {
		this.currGrade = currGrade;
	}

	@Column(name = "FINAL_GRADE", nullable = false)
	public String getFinalGrade() {
		return finalGrade;
	}

	public void setFinalGrade(String finalGrade) {
		this.finalGrade = finalGrade;
	}

	@Column(name = "KPI_DATE", nullable = true)
	public String getKpidate() {
		return kpidate;
	}

	public void setKpidate(String kpidate) {
		this.kpidate = kpidate;
	}
	
	
	
}
