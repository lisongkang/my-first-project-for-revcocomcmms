package com.maywide.biz.inter.pojo.queuserprd;

public class DivideInfo {
	private String salespkginsid;
	
	private String payid;
	
	private String payname;
	
	private String prname;

	private String servid;
	
	private String totalfees;
	
	private String payfees;
	
	private String unfees;
	
	private String totalcount;
	
	private String paycount;
	
	private String uncount;
	
	private String nextpay;
	
	private String nextexec;
	
	private String firsttime;
	
	private String logicdevno;
	
	private String memo;


	public String getPrname() {
		return prname;
	}

	public void setPrname(String prname) {
		this.prname = prname;
	}

	public String getSalespkginsid() {
		return salespkginsid;
	}

	public void setSalespkginsid(String salespkginsid) {
		this.salespkginsid = salespkginsid;
	}

	public String getPayid() {
		return payid;
	}

	public void setPayid(String payid) {
		this.payid = payid;
	}

	public String getPayname() {
		return payname;
	}

	public void setPayname(String payname) {
		this.payname = payname;
	}

	public String getServid() {
		return servid;
	}

	public void setServid(String servid) {
		this.servid = servid;
	}

	public String getTotalfees() {
		return totalfees;
	}

	public void setTotalfees(String totalfees) {
		this.totalfees = totalfees;
	}

	public String getPayfees() {
		return payfees;
	}

	public void setPayfees(String payfees) {
		this.payfees = payfees;
	}

	public String getUnfees() {
		return unfees;
	}

	public void setUnfees(String unfees) {
		this.unfees = unfees;
	}

	public String getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(String totalcount) {
		this.totalcount = totalcount;
	}

	public String getPaycount() {
		return paycount;
	}

	public void setPaycount(String paycount) {
		this.paycount = paycount;
	}

	public String getUncount() {
		return uncount;
	}

	public void setUncount(String uncount) {
		this.uncount = uncount;
	}

	public String getNextpay() {
		return nextpay;
	}

	public void setNextpay(String nextpay) {
		this.nextpay = nextpay;
	}

	public String getNextexec() {
		return nextexec;
	}

	public void setNextexec(String nextexec) {
		this.nextexec = nextexec;
	}

	public String getFirsttime() {
		return firsttime;
	}

	public void setFirsttime(String firsttime) {
		this.firsttime = firsttime;
	}
	
	

	public String getLogicdevno() {
		return logicdevno;
	}

	public void setLogicdevno(String logicdevno) {
		this.logicdevno = logicdevno;
	}

	
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((salespkginsid == null) ? 0 : salespkginsid.hashCode());
		result = prime * result + ((servid == null) ? 0 : servid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DivideInfo other = (DivideInfo) obj;
		if (salespkginsid == null) {
			if (other.salespkginsid != null)
				return false;
		} else if (!salespkginsid.equals(other.salespkginsid))
			return false;
		if (servid == null) {
			if (other.servid != null)
				return false;
		} else if (!servid.equals(other.servid))
			return false;
		return true;
	}
	

}
