package com.maywide.biz.inter.pojo.bizMindate;

/**
 * Created by lisongkang on 2019/3/8 0001.
 */
public class reviseMindateSalesParam {
    private String servid; // 用户ID

    private String pid; // 商品ID

    private String mindate; // 最少使用期限

    public String getServid() {
        return servid;
    }

    public void setServid(String servid) {
        this.servid = servid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getMindate() {
        return mindate;
    }

    public void setMindate(String mindate) {
        this.mindate = mindate;
    }
}
