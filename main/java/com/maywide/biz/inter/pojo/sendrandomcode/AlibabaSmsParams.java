package com.maywide.biz.inter.pojo.sendrandomcode;

public class AlibabaSmsParams {
	
	private String verify;
	
	private String phone;

	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public AlibabaSmsParams(String verify, String phone) {
		super();
		this.verify = verify;
		this.phone = phone;
	}

	public AlibabaSmsParams() {
		super();
	}
	
	

}
