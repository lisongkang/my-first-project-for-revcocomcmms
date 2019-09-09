package com.maywide.biz.inter.pojo.queBossHouse;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/9/3 0001.
 */
public class QueBossHouseInterReq extends BaseApiRequest implements Serializable {
    private String houseid;
    private String city;
    private String whladdr;

    public String getWhladdr() {
        return whladdr;
    }

    public void setWhladdr(String whladdr) {
        this.whladdr = whladdr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHouseid() {
        return houseid;
    }

    public void setHouseid(String houseid) {
        this.houseid = houseid;
    }
}
