package com.maywide.core.mcr;

public class BusinessException extends Exception {
	
	private static final long serialVersionUID = -5239656872190470249L;
	private String msg;
	private Long retcode;
	private Object retbo;
	
	
	public Long getRetcode() {
		return retcode;
	}

	public void setRetcode(Long retcode) {
		this.retcode = retcode;
	}

	public BusinessException(String msg){
		this.msg = msg; 
	}
	
	
	public BusinessException(String msg,Long retcode){
		this.msg = msg; 
		this.retcode = retcode;
	}
	
	public String toString(){
		return "com.maywide.mcr.utils.BusinessException:" + msg;
	}
	
	public String getMessage(){
		return msg;
	}

	public Object getRetbo() {
		return retbo;
	}

	public void setRetbo(Object retbo) {
		this.retbo = retbo;
	}
}
