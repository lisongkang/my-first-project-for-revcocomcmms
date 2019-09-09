package com.maywide.biz.core.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import com.maywide.core.entity.PersistableEntity;

@Entity
@Table(name = "prv_apk_package")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ApkPackage extends PersistableEntity<Long> implements Persistable<Long>,Serializable {
	private static final long serialVersionUID = 8074658782534433218L;
	private Long id;
	private String packages;
	private String version;
	private String downloadUrl;
	private String descript;
	private Date updateTime;
	private Integer force;
	private String advertimg;
	private String operate;
	
	@Id	
	@Column(name="package_id")
	public Long getId() {
		return id;
	}
	public void setId(Long packageId) {
		this.id = packageId;
	}
	public String getPackages() {
		return packages;
	}
	public void setPackages(String packages) {
		this.packages = packages;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
    public String getDescript() {
        return descript;
    }
    public void setDescript(String descript) {
        this.descript = descript;
    }
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public Integer getForce() {
		return force;
	}
	public void setForce(Integer force) {
		this.force = force;
	}
	public String getAdvertimg() {
		return advertimg;
	}
	public void setAdvertimg(String advertimg) {
		this.advertimg = advertimg;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	@Override
	@Transient
	public String getDisplay() {
		return packages;
	}
	
}
