package com.maywide.biz.inter.task.pojo;

public class QuartzTaskBean {

	private Long orderid;
	
	private Long custid;
	
	private Long operid;
	
	private String sendContent;
	
	private String mobile;
	
	private String bossSerinol;
	
	private String city;
	
	private String sendMethod;

	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	public Long getOperid() {
		return operid;
	}

	public void setOperid(Long operid) {
		this.operid = operid;
	}

	public String getSendContent() {
		return sendContent;
	}

	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBossSerinol() {
		return bossSerinol;
	}

	public void setBossSerinol(String bossSerinol) {
		this.bossSerinol = bossSerinol;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSendMethod() {
		return sendMethod;
	}

	public void setSendMethod(String sendMethod) {
		this.sendMethod = sendMethod;
	}

	public QuartzTaskBean(Long orderid, Long custid, Long operid, String sendContent, String mobile, String bossSerinol,
			String city, String sendMethod) {
		super();
		this.orderid = orderid;
		this.custid = custid;
		this.operid = operid;
		this.sendContent = sendContent;
		this.mobile = mobile;
		this.bossSerinol = bossSerinol;
		this.city = city;
		this.sendMethod = sendMethod;
	}

	public QuartzTaskBean() {
		super();
	}
	
	
}
