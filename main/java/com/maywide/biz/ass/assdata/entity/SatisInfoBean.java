package com.maywide.biz.ass.assdata.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.maywide.biz.inter.pojo.queSatis.SatisInfo;


@Entity
@Table(name="tmp_grid_satis_data")
public class SatisInfoBean extends SatisInfo implements Serializable {

	private Long satid;
	
	private String time;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "satid", unique = true, length = 16)
	public Long getSatid() {
		return satid;
	}

	public void setSatid(Long satid) {
		this.satid = satid;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public SatisInfoBean() {
		super();
	}
	
	public SatisInfoBean(SatisInfo info,String time){
		this();
		setCity(info.getCity());
		setFlag(info.getFlag());
		setFlagName(info.getFlagName());
		setGridCode(info.getGridCode());
		setGridName(info.getGridName());
		setMargin(info.getMargin());
		setScale(info.getScale());
		setTime(time);
	}
	
}
