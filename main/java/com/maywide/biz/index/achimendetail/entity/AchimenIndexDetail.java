package com.maywide.biz.index.achimendetail.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import com.maywide.core.annotation.MetaData;
import com.maywide.core.entity.PersistableEntity;


@Entity
@Table(name ="ACHIMEN_INDEX_DETAIL")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AchimenIndexDetail extends PersistableEntity<Long> implements Persistable<Long>{

	@MetaData(value = "id")
	private Long id;
	
	@MetaData(value = "指标编码")
	private String kpicode;
	
	@MetaData(value = "指标值")
	private String kpivalue;
	
	@MetaData(value = "自定义指标项")
	private String selffield;
	
	@MetaData(value = "自定义指标值")
	private String selfvalue;
	
	@MetaData(value = "网格编码")
	private String gridcode;
	
//	private String type;
	
	@MetaData(value = "日期类型")
	private String datetype;
	
	@MetaData(value = "日期值")
	private String datevalue;
	
	@MetaData(value = "增长值")
	private String increase;
	
	@MetaData(value = "流失值")
	private String loss;
	
	@MetaData(value = "净增值")
	private String growth;
	
	@MetaData(value = "环比")
	private String ringratio;
	

	@Column(name = "kpicode", nullable = false)
	public String getKpicode() {
		return kpicode;
	}

	public void setKpicode(String kpicode) {
		this.kpicode = kpicode;
	}

	@Column(name = "kpivalue", nullable = false)
	public String getKpivalue() {
		return kpivalue;
	}

	public void setKpivalue(String kpivalue) {
		this.kpivalue = kpivalue;
	}

	@Column(name = "selffield")
	public String getSelffield() {
		return selffield;
	}

	public void setSelffield(String selffield) {
		this.selffield = selffield;
	}
	
	@Column(name = "selfvalue")
	public String getSelfvalue() {
		return selfvalue;
	}

	public void setSelfvalue(String selfvalue) {
		this.selfvalue = selfvalue;
	}

	@Column(name = "gridcode", nullable = false)
	public String getGridcode() {
		return gridcode;
	}

	public void setGridcode(String gridcode) {
		this.gridcode = gridcode;
	}

	@Column(name = "datetype", nullable = false)
	public String getDatetype() {
		return datetype;
	}

	public void setDatetype(String datetype) {
		this.datetype = datetype;
	}

	@Column(name = "datevalue", nullable = false)
	public String getDatevalue() {
		return datevalue;
	}

	public void setDatevalue(String datevalue) {
		this.datevalue = datevalue;
	}

	@Column(name = "increase")
	public String getIncrease() {
		return increase;
	}

	public void setIncrease(String increase) {
		this.increase = increase;
	}

	@Column(name = "loss")
	public String getLoss() {
		return loss;
	}

	public void setLoss(String loss) {
		this.loss = loss;
	}

	@Column(name = "growth")
	public String getGrowth() {
		return growth;
	}

	public void setGrowth(String growth) {
		this.growth = growth;
	}

	@Column(name = "ringratio")
	public String getRingratio() {
		return ringratio;
	}

	public void setRingratio(String ringratio) {
		this.ringratio = ringratio;
	}


	public void setId(Long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getDisplay() {
		return "";
	}
	
	public void setDisplay(String display){
		
	}

}
