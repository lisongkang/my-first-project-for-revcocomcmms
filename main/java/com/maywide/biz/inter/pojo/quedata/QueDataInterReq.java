package com.maywide.biz.inter.pojo.quedata;

import java.util.List;

public class QueDataInterReq {

	private String gcode;
	
	private String mcode;
	
	private String scope;//上级参数ID	
	
	private boolean def;
	
	private  String mcodeSeparator;	
	
	private List<Compose> composes; //用于临时加载组合城市支付方式

	
	public String getMcodeSeparator() {
		return mcodeSeparator;
	}
	public void setMcodeSeparator(String mcodeSeparator) {
		this.mcodeSeparator = mcodeSeparator;
	}
	public List<Compose> getComposes() {
		return composes;
	}
	public void setComposes(List<Compose> composes) {
		this.composes = composes;
	}
	public String getGcode() {
		return gcode;
	}
	public void setGcode(String gcode) {
		this.gcode = gcode;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getMcode() {
		return mcode;
	}
	public void setMcode(String mcode) {
		this.mcode = mcode;
	}
	public boolean isDef() {
		return def;
	}
	public void setDef(boolean def) {
		this.def = def;
	}

}
