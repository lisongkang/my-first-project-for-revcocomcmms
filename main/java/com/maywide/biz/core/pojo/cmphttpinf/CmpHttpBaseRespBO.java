package com.maywide.biz.core.pojo.cmphttpinf;

import java.io.Serializable;

public class CmpHttpBaseRespBO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String errcode;
	private String result;

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
