package com.maywide.biz.inter.pojo.queEquipdeInfo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueEquipdeInfoReq extends BaseApiRequest {

	
//	private String serialno;
	
	private String servorderid;
	
	private String channels;
	
	private String bizcodes;
	
	private String areaid;
	
//	private String patchids;
	
	private String gridcodes;
	
	private String custid;
	
	private String addr;
	
	private String soptime;
	
	private String eoptime;
	
//	private String stepcode;
	
	private String custorderid;

	public String getServorderid() {
		return servorderid;
	}

	public void setServorderid(String servorderid) {
		this.servorderid = servorderid;
	}

	public String getChannels() {
		return channels;
	}

	public void setChannels(String channels) {
		this.channels = channels;
	}

	public String getBizcodes() {
		return bizcodes;
	}

	public void setBizcodes(String bizcodes) {
		this.bizcodes = bizcodes;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getGridcodes() {
		return gridcodes;
	}

	public void setGridcodes(String gridcodes) {
		this.gridcodes = gridcodes;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getSoptime() {
		return soptime;
	}

	public void setSoptime(String soptime) {
		this.soptime = soptime;
	}

	public String getEoptime() {
		return eoptime;
	}

	public void setEoptime(String eoptime) {
		this.eoptime = eoptime;
	}

	public String getCustorderid() {
		return custorderid;
	}

	public void setCustorderid(String custorderid) {
		this.custorderid = custorderid;
	}
	
	
	
}
