package com.maywide.biz.inter.pojo.queFeeinServ;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueFeeinServReq extends BaseApiRequest {

	private String custid;
	
	private String kind;
	
	private String ispostpone;
	
	private String permark;
	
	private String logicdevno;
	
	private String logindevs;
	

	public String getLogindevs() {
		return logindevs;
	}

	public void setLogindevs(String logindevs) {
		this.logindevs = logindevs;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getIspostpone() {
		return ispostpone;
	}

	public void setIspostpone(String ispostpone) {
		this.ispostpone = ispostpone;
	}

	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}

	public String getLogicdevno() {
		return logicdevno;
	}

	public void setLogicdevno(String logicdevno) {
		this.logicdevno = logicdevno;
	}
	
	
	
}
