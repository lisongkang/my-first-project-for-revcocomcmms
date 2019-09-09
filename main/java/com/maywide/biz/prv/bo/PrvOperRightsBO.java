package com.maywide.biz.prv.bo;

public class PrvOperRightsBO implements java.io.Serializable{
	private Long id;
	private String objtype;
	private Long objectid;
	private String type;
	private String value;
	private java.util.Date begintime;
	private java.util.Date endtime;
	private String memo;
	private String permark;
	private String areaid;
	private String valuename;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getObjtype() {
		return objtype;
	}

	public void setObjtype(String objtype) {
		this.objtype = objtype;
	}

	public Long getObjectid() {
		return objectid;
	}

	public void setObjectid(Long objectid) {
		this.objectid = objectid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public java.util.Date getBegintime() {
		return begintime;
	}

	public void setBegintime(java.util.Date begintime) {
		this.begintime = begintime;
	}

	public java.util.Date getEndtime() {
		return endtime;
	}

	public void setEndtime(java.util.Date endtime) {
		this.endtime = endtime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getValuename() {
		return valuename;
	}

	public void setValuename(String valuename) {
		this.valuename = valuename;
	}
}
