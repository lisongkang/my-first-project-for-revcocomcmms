package com.maywide.biz.inter.pojo.editcust;

public class CustPubInfoInterInfoResp implements java.io.Serializable {
	private Long id;
	private String interest;
	private java.util.Date birth;
	private String grade;
	private String trade;
	private String occupation;
	private String company;
	private String linkaddr;

	public Long getId() {
		return id;
	}

	public String getLinkaddr() {
		return linkaddr;
	}

	public void setLinkaddr(String linkaddr) {
		this.linkaddr = linkaddr;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public java.util.Date getBirth() {
		return birth;
	}

	public void setBirth(java.util.Date birth) {
		this.birth = birth;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getTrade() {
		return trade;
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

}
