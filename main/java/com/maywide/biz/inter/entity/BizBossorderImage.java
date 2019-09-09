package com.maywide.biz.inter.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;

import com.maywide.core.entity.PersistableEntity;

@Entity
@Table(name = "BIZ_BOSSORDER_IMAGE")
public class BizBossorderImage extends PersistableEntity<Long> implements Persistable<Long> {

	private static final long serialVersionUID = 1;

	private Long id; // 服务订单号id
	private String city; // 地市
	private Long bossorderid; // 客户订单号id
	private Long areaid; // 业务区
	private String imagecatalog1;// 图片1路径
	private String imagecatalog2;// 图片2路径
	private String imagecatalog3;// 图片3路径
	private Long operid;// 申请登记工号
	private Long deptid;// 申请部门id
	private Date optime;// 申请登记时间

	@Override
	@Transient
	public String getDisplay() {
		return null;
	}

	@Id
	@Column(name = "servorderid", unique = true, length = 16)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Transient
	public Long getServorderid() {
		return id;
	}

	public void setServorderid(Long servorderid) {
		this.id = servorderid;
	}

	public Long getBossorderid() {
		return bossorderid;
	}

	public void setBossorderid(Long bossorderid) {
		this.bossorderid = bossorderid;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getAreaid() {
		return areaid;
	}

	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}

	public String getImagecatalog1() {
		return imagecatalog1;
	}

	public void setImagecatalog1(String imagecatalog1) {
		this.imagecatalog1 = imagecatalog1;
	}

	public String getImagecatalog2() {
		return imagecatalog2;
	}

	public void setImagecatalog2(String imagecatalog2) {
		this.imagecatalog2 = imagecatalog2;
	}

	public String getImagecatalog3() {
		return imagecatalog3;
	}

	public void setImagecatalog3(String imagecatalog3) {
		this.imagecatalog3 = imagecatalog3;
	}

	public Long getOperid() {
		return operid;
	}

	public void setOperid(Long operid) {
		this.operid = operid;
	}

	public Long getDeptid() {
		return deptid;
	}

	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}

	public Date getOptime() {
		return optime;
	}

	public void setOptime(Date optime) {
		this.optime = optime;
	}

}
