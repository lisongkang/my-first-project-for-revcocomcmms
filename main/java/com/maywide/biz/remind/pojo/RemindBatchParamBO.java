package com.maywide.biz.remind.pojo;

import java.io.Serializable;

public class RemindBatchParamBO implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8931982226204117452L;

	private Long remobjtype;
	
	private Long deptid;
	
	private String city;
	
	private String startPeriod; // 可以是开始月、开始日期
	
	private String endPeriod; // 可以是结束月、结束日期
	
	private String iscfm; // 是否确认

	public Long getRemobjtype() {
		return remobjtype;
	}

	public void setRemobjtype(Long remobjtype) {
		this.remobjtype = remobjtype;
	}

	public Long getDeptid() {
		return deptid;
	}

	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStartPeriod() {
		return startPeriod;
	}

	public void setStartPeriod(String startPeriod) {
		this.startPeriod = startPeriod;
	}

	public String getEndPeriod() {
		return endPeriod;
	}

	public void setEndPeriod(String endPeriod) {
		this.endPeriod = endPeriod;
	}

	public String getIscfm() {
		return iscfm;
	}

	public void setIscfm(String iscfm) {
		this.iscfm = iscfm;
	}
}
