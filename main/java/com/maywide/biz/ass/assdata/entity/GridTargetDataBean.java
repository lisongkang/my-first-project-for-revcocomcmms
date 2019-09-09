package com.maywide.biz.ass.assdata.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.maywide.biz.inter.pojo.dataReport.DataTarget;


@Entity
@Table(name="tmp_grid_target_data")
public class GridTargetDataBean extends DataTarget implements Serializable {
	
	private static final long serialVersionUID = 2723712833143764486L;
	
	private Long id;
	
	private String targetTime;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "targetid", unique = true, length = 16)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTargetTime() {
		return targetTime;
	}

	public void setTargetTime(String targetTime) {
		this.targetTime = targetTime;
	}

	public GridTargetDataBean() {
		super();
	}
	
	public GridTargetDataBean(DataTarget target,String targetTime){
		this();
		setAddNums(target.getAddNums());
		setCity(target.getCity());
		setEtincNums(target.getEtincNums());
		setFlag(target.getFlag());
		setFlagName(target.getFlagName());
		setFlagSum(target.getFlagSum());
		setGridCode(target.getGridCode());
		setIsDetail(target.getIsDetail());
		setLossNums(target.getLossNums());
		setSort(target.getSort());
		setTargetTime(targetTime);
	}
}
