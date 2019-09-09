package com.maywide.biz.inter.pojo.queservstinfo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueSoonExpcustReq extends BaseApiRequest implements java.io.Serializable {
	private String     custid        	;
	private String     servid         ;
	private String     objtype        ;
	private String     objid          ;
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getServid() {
		return servid;
	}
	public void setServid(String servid) {
		this.servid = servid;
	}
	public String getObjtype() {
		return objtype;
	}
	public void setObjtype(String objtype) {
		this.objtype = objtype;
	}
	public String getObjid() {
		return objid;
	}
	public void setObjid(String objid) {
		this.objid = objid;
	}

	
}
