package com.maywide.biz.inter.pojo.queBizBusiness;

public class QueBizBusinessBossReq {

	private String custid;
	
	private Long erector;
	
	private Long erectordept;
	
	private String status;
	
	private String stime;
	
	private String etime;

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public Long getErector() {
		return erector;
	}

	public void setErector(Long erector) {
		this.erector = erector;
	}

	public Long getErectordept() {
		return erectordept;
	}

	public void setErectordept(Long erectordept) {
		this.erectordept = erectordept;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}
	
	
}
