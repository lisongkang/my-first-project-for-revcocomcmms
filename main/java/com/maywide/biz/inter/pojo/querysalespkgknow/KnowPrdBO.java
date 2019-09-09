package com.maywide.biz.inter.pojo.querysalespkgknow;

public class KnowPrdBO implements java.io.Serializable {
	
	private String pid;//产品id
	private String pcode;//产品编码
	private String pname;//产品名称
	private String permark;//业务类型
	private String pclass;//产品类型
	private String psubclass;//产品子类型
	private String externalid;//外部实体id
	private String prodtype;//产品类别	0-单产品,1-组合产品
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getPermark() {
		return permark;
	}
	public void setPermark(String permark) {
		this.permark = permark;
	}
	public String getPclass() {
		return pclass;
	}
	public void setPclass(String pclass) {
		this.pclass = pclass;
	}
	public String getPsubclass() {
		return psubclass;
	}
	public void setPsubclass(String psubclass) {
		this.psubclass = psubclass;
	}
	public String getExternalid() {
		return externalid;
	}
	public void setExternalid(String externalid) {
		this.externalid = externalid;
	}
	public String getProdtype() {
		return prodtype;
	}
	public void setProdtype(String prodtype) {
		this.prodtype = prodtype;
	}
	
}
