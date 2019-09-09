package com.maywide.biz.system.entity;

import java.io.Serializable;

public class ParamObj implements Serializable {
	private String mcode;
	private String mname;

	public String getMcode() {
		return mcode;
	}

	public void setMcode(String mcode) {
		this.mcode = mcode;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}
}
