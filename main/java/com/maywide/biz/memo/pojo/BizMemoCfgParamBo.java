package com.maywide.biz.memo.pojo;

import java.io.Serializable;

/**
 * 备注配置查询对象
 * @author zhuangzhitang
 *
 */
@SuppressWarnings("serial")
public class BizMemoCfgParamBo implements Serializable{
    
	private String city;
	private String memotxt;
	private String opcodes;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMemotxt() {
		return memotxt;
	}

	public void setMemotxt(String memotxt) {
		this.memotxt = memotxt;
	}

	public String getOpcodes() {
		return opcodes;
	}

	public void setOpcodes(String opcodes) {
		this.opcodes = opcodes;
	}
}
