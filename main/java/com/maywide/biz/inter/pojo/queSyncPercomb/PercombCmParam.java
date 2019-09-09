package com.maywide.biz.inter.pojo.queSyncPercomb;

import java.util.List;

import com.maywide.biz.system.entity.PrvSysparam;

public class PercombCmParam {

	private String cminputway;
	
	private List<PrvSysparam> inputWayList;
	
	private String authmode;
	
	private List<PrvSysparam> authmodeList;
	
	private String ipmode;
	
	private List<PrvSysparam> ipmodeList;
	
	private String cmtype;
	
	private List<PrvSysparam> typeList;
	
	private String cmacctno;
	
	private String cmacctnoPwd;
	
	private List<String> suffixList;
	
	private String cmLast;
	
	public String getCminputway() {
		return cminputway;
	}

	public void setCminputway(String cminputway) {
		this.cminputway = cminputway;
	}

	public String getAuthmode() {
		return authmode;
	}

	public void setAuthmode(String authmode) {
		this.authmode = authmode;
	}

	public String getIpmode() {
		return ipmode;
	}

	public void setIpmode(String ipmode) {
		this.ipmode = ipmode;
	}

	public String getCmtype() {
		return cmtype;
	}

	public void setCmtype(String cmtype) {
		this.cmtype = cmtype;
	}

	public List<PrvSysparam> getInputWayList() {
		return inputWayList;
	}

	public void setInputWayList(List<PrvSysparam> inputWayList) {
		this.inputWayList = inputWayList;
	}

	public List<PrvSysparam> getAuthmodeList() {
		return authmodeList;
	}

	public void setAuthmodeList(List<PrvSysparam> authmodeList) {
		this.authmodeList = authmodeList;
	}

	public List<PrvSysparam> getIpmodeList() {
		return ipmodeList;
	}

	public void setIpmodeList(List<PrvSysparam> ipmodeList) {
		this.ipmodeList = ipmodeList;
	}

	public List<PrvSysparam> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<PrvSysparam> typeList) {
		this.typeList = typeList;
	}

	public String getCmacctno() {
		return cmacctno;
	}

	public void setCmacctno(String cmacctno) {
		this.cmacctno = cmacctno;
	}

	public List<String> getSuffixList() {
		return suffixList;
	}

	public void setSuffixList(List<String> suffixList) {
		this.suffixList = suffixList;
	}

	public String getCmacctnoPwd() {
		return cmacctnoPwd;
	}

	public void setCmacctnoPwd(String cmacctnoPwd) {
		this.cmacctnoPwd = cmacctnoPwd;
	}

	public String getCmLast() {
		return cmLast;
	}

	public void setCmLast(String cmLast) {
		this.cmLast = cmLast;
	}
	
}
