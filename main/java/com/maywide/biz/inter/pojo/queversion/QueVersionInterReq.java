package com.maywide.biz.inter.pojo.queversion;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueVersionInterReq extends BaseApiRequest implements java.io.Serializable {
	private String appid;
	private String city;
	
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getAppid() {
        return appid;
    }
    public void setAppid(String appid) {
        this.appid = appid;
    }

	
}
