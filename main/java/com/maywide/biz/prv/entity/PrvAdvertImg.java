package com.maywide.biz.prv.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PRV_ADVERT_IMG")
public class PrvAdvertImg implements java.io.Serializable {
	
	private Long id;
	
	private String city;
	
	private String toClz;
	
	private String params;
	
	private String params_value;
	
	private String url;
	
	private String path;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "city", nullable = false)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "to_Clz", nullable = false)
	public String getToClz() {
		return toClz;
	}

	public void setToClz(String toClz) {
		this.toClz = toClz;
	}

	@Column(name = "params", nullable = false)
	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	@Column(name = "params_value", nullable = false)
	public String getParams_value() {
		return params_value;
	}

	public void setParams_value(String params_value) {
		this.params_value = params_value;
	}

	@Column(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "path")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	

}
