package com.maywide.biz.inter.pojo.bizPreAuthPrds;

public class PreauthKeynos {

	private String keyno;
	
	private String mac;
	
	private String cmacctno;
	
	private String cmpwd;

	private String servcode;
	private String outermac;

	private String radiusflag;

	public String getRadiusflag() {
		return radiusflag;
	}

	public void setRadiusflag(String radiusflag) {
		this.radiusflag = radiusflag;
	}

	public String getServcode() {
		return servcode;
	}

	public void setServcode(String servcode) {
		this.servcode = servcode;
	}

	public String getOutermac() {
		return outermac;
	}

	public void setOutermac(String outermac) {
		this.outermac = outermac;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getCmacctno() {
		return cmacctno;
	}

	public void setCmacctno(String cmacctno) {
		this.cmacctno = cmacctno;
	}

	public String getCmpwd() {
		return cmpwd;
	}

	public void setCmpwd(String cmpwd) {
		this.cmpwd = cmpwd;
	}

	public String getKeyno() {
		return keyno;
	}

	public void setKeyno(String keyno) {
		this.keyno = keyno;
	}

	
}
