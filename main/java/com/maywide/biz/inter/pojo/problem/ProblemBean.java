package com.maywide.biz.inter.pojo.problem;

import java.util.Date;

public class ProblemBean {
	private Long id;
	private String pltype;
	private String plname;
	private String pldesc;
	private Long suboperid;
	private Long suboperdept;
	private Date subtime;
	private Long dealoperid;
	private Long dealoperdept;
	private Date dealtime;
	private String dealdesc;
	private Long plstate;
	private String city;
	private Long areaid;
	private String memo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPltype() {
		return pltype;
	}

	public void setPltype(String pltype) {
		this.pltype = pltype;
	}

	public String getPlname() {
		return plname;
	}

	public void setPlname(String plname) {
		this.plname = plname;
	}

	public String getPldesc() {
		return pldesc;
	}

	public void setPldesc(String pldesc) {
		this.pldesc = pldesc;
	}

	public Long getSuboperid() {
		return suboperid;
	}

	public void setSuboperid(Long suboperid) {
		this.suboperid = suboperid;
	}

	public Long getSuboperdept() {
		return suboperdept;
	}

	public void setSuboperdept(Long suboperdept) {
		this.suboperdept = suboperdept;
	}

	public Date getSubtime() {
		return subtime;
	}

	public void setSubtime(Date subtime) {
		this.subtime = subtime;
	}

	public Long getDealoperid() {
		return dealoperid;
	}

	public void setDealoperid(Long dealoperid) {
		this.dealoperid = dealoperid;
	}

	public Long getDealoperdept() {
		return dealoperdept;
	}

	public void setDealoperdept(Long dealoperdept) {
		this.dealoperdept = dealoperdept;
	}

	public Date getDealtime() {
		return dealtime;
	}

	public void setDealtime(Date dealtime) {
		this.dealtime = dealtime;
	}

	public String getDealdesc() {
		return dealdesc;
	}

	public void setDealdesc(String dealdesc) {
		this.dealdesc = dealdesc;
	}

	public Long getPlstate() {
		return plstate;
	}

	public void setPlstate(Long plstate) {
		this.plstate = plstate;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getAreaid() {
		return areaid;
	}

	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
