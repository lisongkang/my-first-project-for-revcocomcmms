package com.maywide.biz.inter.pojo.chgdev;

public class DevInfoBean {

	private Long resid; // 资源标识
	
	private String kind; // 资源类型
	
	private String subkind; // SUBKIND
	
	private String devno; // 设备编号
	
	private String useprop; // 设备来源
	
	private String owertype; // 产权人
	
	private String property; // 产权期限
	
	private String services; // 保修期限
	
	private String reason; // 是否保修

	private Long id;
	
	private String properties; // 设备属性
	
	private String model; // 设备型号

	public Long getResid() {
		return resid;
	}

	public void setResid(Long resid) {
		this.resid = resid;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getSubkind() {
		return subkind;
	}

	public void setSubkind(String subkind) {
		this.subkind = subkind;
	}

	public String getDevno() {
		return devno;
	}

	public void setDevno(String devno) {
		this.devno = devno;
	}

	public String getUseprop() {
		return useprop;
	}

	public void setUseprop(String useprop) {
		this.useprop = useprop;
	}

	public String getOwertype() {
		return owertype;
	}

	public void setOwertype(String owertype) {
		this.owertype = owertype;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

}
