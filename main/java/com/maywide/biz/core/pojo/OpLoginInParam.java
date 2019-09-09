package com.maywide.biz.core.pojo;

import java.io.Serializable;

public class OpLoginInParam implements Serializable {
	private static final long serialVersionUID = 5592436468071547271L;
	
	private String userName; // ��¼�û���
	private String password; // ����Ա��¼����
	private String imei; // IMEI��
	private String mac; // MAC��ַ

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}
}
