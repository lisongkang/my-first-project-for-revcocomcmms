package com.maywide.biz.inter.pojo.sycnChlInstall;

public class SyncPrdParam {

	private Long servid;
	
	private String logicdevno;
	
	private String salespkgid;
	
	private String pid;
	
	private String count;
	
	private String unit;
	
	private String ispostpone;	
	
	private String selprds;
	
	private String selectid;

	private String salesid;
	
	private String stime;
	
	private String mindate;
	
	

	public String getMindate() {
		return mindate;
	}

	public void setMindate(String mindate) {
		this.mindate = mindate;
	}
	
	/*private String mincount;
	
	public String getMincount() {
		return mincount;
	}

	public void setMincount(String mincount) {
		this.mincount = mincount;
	}*/

	public Long getServid() {
		return servid;
	}

	public void setServid(Long servid) {
		this.servid = servid;
	}

	public String getLogicdevno() {
		return logicdevno;
	}

	public void setLogicdevno(String logicdevno) {
		this.logicdevno = logicdevno;
	}

	public String getSalespkgid() {
		return salespkgid;
	}

	public void setSalespkgid(String salespkgid) {
		this.salespkgid = salespkgid;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		if(unit.equalsIgnoreCase("天")){
			this.unit = "0";
		}else if(unit.equalsIgnoreCase("月")){
			this.unit = "1";
		}else if(unit.equalsIgnoreCase("年")){
			this.unit = "2";
		}else{
			this.unit = unit;
		}
	}

	public String getIspostpone() {
		return ispostpone;
	}

	public void setIspostpone(String ispostpone) {
		this.ispostpone = ispostpone;
	}

	public String getSelprds() {
		return selprds;
	}

	public void setSelprds(String selprds) {
		this.selprds = selprds;
	}

	public String getSelectid() {
		return selectid;
	}

	public void setSelectid(String selectid) {
		this.selectid = selectid;
	}

	public String getSalesid() {
		return salesid;
	}

	public void setSalesid(String salesid) {
		this.salesid = salesid;
	}

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		if (stime != null && stime.length() == 10) {
			this.stime = stime + " 00:00:00";
		} else {
			this.stime = stime;
		}
	}
	
	
	
}
