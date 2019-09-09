package com.maywide.biz.inter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "PRV_OPER_PHOTO")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PrvOperPhoto {
	
	private Long id;
	
	private String headImg;

	
	@Id
	@Column(name = "operid", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "head_img")
	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	
	

}
