package com.maywide.biz.inter.pojo.quePreAuthPrds;

public class ParamBean {

	private String paramCode;
	
	private String paramName;

	public String getParamCode() {
		return paramCode;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public ParamBean(String paramCode, String paramName) {
		super();
		this.paramCode = paramCode;
		this.paramName = paramName;
	}

	public ParamBean() {
		super();
	}
	
	
	
}
