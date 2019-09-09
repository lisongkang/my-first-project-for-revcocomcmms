package com.maywide.biz.inter.pojo.quepreauthlog;

@SuppressWarnings("serial")
public class QuePreAuthLogBossReq implements java.io.Serializable {

	private String permark;
	private String operid;
	private String stime;
	private String etime;
	private String keyno;
	private String status;

	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}

	public String getOperid() {
		return operid;
	}

	public void setOperid(String operid) {
		this.operid = operid;
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

	public String getKeyno() {
		return keyno;
	}

	public void setKeyno(String keyno) {
		this.keyno = keyno;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
