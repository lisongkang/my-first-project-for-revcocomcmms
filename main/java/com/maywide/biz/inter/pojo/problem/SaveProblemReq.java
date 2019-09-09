package com.maywide.biz.inter.pojo.problem;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class SaveProblemReq extends BaseApiRequest {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String pltype;
	private String plname;
	private String pldesc;
	private Long plstate;

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

	public Long getPlstate() {
		return plstate;
	}

	public void setPlstate(Long plstate) {
		this.plstate = plstate;
	}

}
