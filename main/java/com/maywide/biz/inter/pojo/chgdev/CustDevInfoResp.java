package com.maywide.biz.inter.pojo.chgdev;



public class CustDevInfoResp implements java.io.Serializable {
	
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
