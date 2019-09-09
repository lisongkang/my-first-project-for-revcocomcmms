package com.maywide.biz.inter.pojo.chgdev;

import com.maywide.biz.inter.pojo.createcust.CustInfoBO;

public class SysCustDevBO implements java.io.Serializable {
	private Long id;
	private Long custid;
	private String devno;
	private String kind;
	private String subkind;
	private java.util.Date intime;
	private String usestatus;
	private Long operator;
	private String useprop;
	private Long salesid;
	private Long bizfeedetid;
	private Long salespkgid;
	private Long resid;
	private Long depart;
	private String owertype;
	private java.util.Date property;
	private java.util.Date services;
	private Long contractid;
	private String asstno;
	private String city;
	private Long partlen;//设备摊销年限

	
	private CustInfoBO sysCust = new CustInfoBO();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCustid() {
		return custid;
	}
	public void setCustid(Long custid) {
		this.custid = custid;
	}
	public String getDevno() {
		return devno;
	}
	public void setDevno(String devno) {
		this.devno = devno;
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
	public java.util.Date getIntime() {
		return intime;
	}
	public void setIntime(java.util.Date intime) {
		this.intime = intime;
	}
	public String getUsestatus() {
		return usestatus;
	}
	public void setUsestatus(String usestatus) {
		this.usestatus = usestatus;
	}
	public Long getOperator() {
		return operator;
	}
	public void setOperator(Long operator) {
		this.operator = operator;
	}
	public String getUseprop() {
		return useprop;
	}
	public void setUseprop(String useprop) {
		this.useprop = useprop;
	}
	public Long getSalesid() {
		return salesid;
	}
	public void setSalesid(Long salesid) {
		this.salesid = salesid;
	}
	public Long getBizfeedetid() {
		return bizfeedetid;
	}
	public void setBizfeedetid(Long bizfeedetid) {
		this.bizfeedetid = bizfeedetid;
	}
	public Long getSalespkgid() {
		return salespkgid;
	}
	public void setSalespkgid(Long salespkgid) {
		this.salespkgid = salespkgid;
	}
	public Long getResid() {
		return resid;
	}
	public void setResid(Long resid) {
		this.resid = resid;
	}
	public Long getDepart() {
		return depart;
	}
	public void setDepart(Long depart) {
		this.depart = depart;
	}
	public String getOwertype() {
		return owertype;
	}
	public void setOwertype(String owertype) {
		this.owertype = owertype;
	}
	public java.util.Date getProperty() {
		return property;
	}
	public void setProperty(java.util.Date property) {
		this.property = property;
	}
	public java.util.Date getServices() {
		return services;
	}
	public void setServices(java.util.Date services) {
		this.services = services;
	}
	public Long getContractid() {
		return contractid;
	}
	public void setContractid(Long contractid) {
		this.contractid = contractid;
	}
	public String getAsstno() {
		return asstno;
	}
	public void setAsstno(String asstno) {
		this.asstno = asstno;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Long getPartlen() {
		return partlen;
	}
	public void setPartlen(Long partlen) {
		this.partlen = partlen;
	}
	public CustInfoBO getSysCust() {
		return sysCust;
	}
	public void setSysCust(CustInfoBO sysCust) {
		this.sysCust = sysCust;
	}
	
}
