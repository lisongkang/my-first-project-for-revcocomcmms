package com.maywide.biz.ass.assdata.entity;

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
@Table(name = "SYN_BOSS_TW2_INCOME_MONTH")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SynBossIncomeMonth extends PersistableEntity<Long> implements Persistable<Long> {

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
	
	@MetaData(value = "地市")
    @EntityAutoCode(order = 2, listShow = true)
	private String city;
	
	@MetaData(value = "业务区")
    @EntityAutoCode(order = 3, listShow = true)
	private Long areaid;
	
	@MetaData(value = "片区")
    @EntityAutoCode(order = 4, listShow = true)
	private Long patchid;
	
	@MetaData(value = "报表费项")
	@EntityAutoCode(order = 5, listShow = true)
	private String rfeecode;
	
	@MetaData(value = "收入")
    @EntityAutoCode(order = 6, listShow = true)
	private Double income;
	
	@MetaData(value = "纯收入")
    @EntityAutoCode(order = 7, listShow = true)
	private Double ifees;
	
	@MetaData(value = "税额")
    @EntityAutoCode(order = 8, listShow = true)
	private Double tfees;
	
	@MetaData(value = "业务收入类型， 参数：BIZ_EXTPERMARK")
    @EntityAutoCode(order = 9, listShow = true)
	private String extpermark;
	
	@MetaData(value = "网格ID")
    @EntityAutoCode(order = 10, listShow = true)
	private Long gridid;
	
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

	@Column(name = "CITY", nullable = true, length = 2)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "AREAID", nullable = true, length = 16)
	public Long getAreaid() {
		return areaid;
	}

	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}

	@Column(name = "PATCHID", nullable = true, length = 16)
	public Long getPatchid() {
		return patchid;
	}

	public void setPatchid(Long patchid) {
		this.patchid = patchid;
	}

	@Column(name = "RFEECODE", nullable = true, length = 4)
	public String getRfeecode() {
		return rfeecode;
	}

	public void setRfeecode(String rfeecode) {
		this.rfeecode = rfeecode;
	}

	@Column(name = "INCOME", nullable = true, length = 18)
	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	@Column(name = "IFEES", nullable = true, length = 18)
	public Double getIfees() {
		return ifees;
	}

	public void setIfees(Double ifees) {
		this.ifees = ifees;
	}

	@Column(name = "TFEES", nullable = true, length = 18)
	public Double getTfees() {
		return tfees;
	}

	public void setTfees(Double tfees) {
		this.tfees = tfees;
	}

	@Column(name = "EXTPERMARK", nullable = true, length = 32)
	public String getExtpermark() {
		return extpermark;
	}

	public void setExtpermark(String extpermark) {
		this.extpermark = extpermark;
	}

	@Column(name = "GRIDID", nullable = true, length = 16)
	public Long getGridid() {
		return gridid;
	}

	public void setGridid(Long gridid) {
		this.gridid = gridid;
	}
	
}