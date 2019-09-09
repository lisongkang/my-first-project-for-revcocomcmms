package com.maywide.biz.inter.pojo.sendrandomcode;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class SendRandomCodeInterReq extends BaseApiRequest implements
		java.io.Serializable {
    private String phoneno;
    private String userip;
    private String smscontent;
    private String city;
    private String areaid;
    private String custid;
    public String getPhoneno() {
        return phoneno;
    }
    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
    public String getUserip() {
        return userip;
    }
    public void setUserip(String userip) {
        this.userip = userip;
    }
    public String getSmscontent() {
        return smscontent;
    }
    public void setSmscontent(String smscontent) {
        this.smscontent = smscontent;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getAreaid() {
        return areaid;
    }
    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
    
	
}
