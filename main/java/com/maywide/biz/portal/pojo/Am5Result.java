package com.maywide.biz.portal.pojo;

import org.apache.commons.lang3.StringUtils;

public class Am5Result {

	public static final int UNKOWN_ERROR = -1;

	public int code;
	public String msg;

	public void setUnkownError() {
		code = UNKOWN_ERROR;
		msg = "未知错误";
	}

	public String getMessage(String def) {
		return (code == UNKOWN_ERROR || StringUtils.isBlank(msg)) ? def : msg;
	}

	@Override
	public String toString() {
		return "Am5Result [code=" + code + ", msg=" + msg + "]";
	}

}
