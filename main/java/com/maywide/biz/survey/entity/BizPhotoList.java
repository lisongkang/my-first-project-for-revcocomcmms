package com.maywide.biz.survey.entity;

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
@Table(name="biz_photo_list")
@Cache(usage= CacheConcurrencyStrategy.READ_WRITE)
public class BizPhotoList extends PersistableEntity<Long> implements Persistable<Long> {
	
	@MetaData(value = "图片编号")
	@EntityAutoCode
	private Long id;
	
	@MetaData(value = "图片名")
	@EntityAutoCode
	private String filename;
	
	@MetaData(value = "图片唯一标识")
	@EntityAutoCode
	private String uuid;

	@MetaData(value = "图片相对路径")
	@EntityAutoCode
	private String imagepath;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fileid", unique = true, length = 16)
	public Long getId() {
		return id;
	}
	
	@Override
	@Transient
	public String getDisplay() {
		return "";
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getImagepath() {
		return imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}
	
	
	
}
