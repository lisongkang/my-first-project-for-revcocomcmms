package com.maywide.biz.inter.pojo.chgdev;

import com.maywide.biz.core.pojo.api.BaseApiRequest;



public class QueFittingInfoReq  extends BaseApiRequest implements java.io.Serializable {
	
	private	Long custid;
    private String kind;
    private String areaid;
	public Long getCustid() {
		return custid;
	}
	public void setCustid(Long custid) {
		this.custid = custid;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

    
    
 

}
