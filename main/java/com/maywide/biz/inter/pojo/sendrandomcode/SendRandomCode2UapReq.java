package com.maywide.biz.inter.pojo.sendrandomcode;

public class SendRandomCode2UapReq {

	private String phoneNum;
	
	private String userIP;
	
	private String city;
	
	private String areaid;
	
	private String smsTemplate;
	
	private String smsParamJson;

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getUserIP() {
		return userIP;
	}

	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getSmsTemplate() {
		return smsTemplate;
	}

	public void setSmsTemplate(String smsTemplate) {
		this.smsTemplate = smsTemplate;
	}

	public String getSmsParamJson() {
		return smsParamJson;
	}

	public void setSmsParamJson(String smsParamJson) {
		this.smsParamJson = smsParamJson;
	}
	
	
	
}
