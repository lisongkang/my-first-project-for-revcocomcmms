package com.maywide.biz.inter.pojo.common;

public class GridBean {

	public GridBean(String city, Long operid, Long areaid) {
		super();
		this.city = city;
		this.operid = operid;
		this.areaid = areaid;
	}

	public GridBean() {
		super();
	}

	private Long gridid;
	
	private String gcode;
	
	private String gtype;
	
	private String city;
	
	private Long operid;
	
	private Long areaid;

	public Long getGridid() {
		return gridid;
	}

	public void setGridid(Long gridid) {
		this.gridid = gridid;
	}

	public String getGcode() {
		return gcode;
	}

	public void setGcode(String gcode) {
		this.gcode = gcode;
	}

	public String getGtype() {
		return gtype;
	}

	public void setGtype(String gtype) {
		this.gtype = gtype;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getOperid() {
		return operid;
	}

	public void setOperid(Long operid) {
		this.operid = operid;
	}

	public Long getAreaid() {
		return areaid;
	}

	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}
	
	
	
}
