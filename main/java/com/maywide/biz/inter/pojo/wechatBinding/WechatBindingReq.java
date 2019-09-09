package com.maywide.biz.inter.pojo.wechatBinding;

/**
 * Created by lisongkang on 2019/3/18 0001.
 */
public class WechatBindingReq {
    private String devno;
    private String openid;
    private String city;
    private String areaid;
    private String permark;

    public String getDevno() {
        return devno;
    }

    public void setDevno(String devno) {
        this.devno = devno;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
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

    public String getPermark() {
        return permark;
    }

    public void setPermark(String permark) {
        this.permark = permark;
    }
}
