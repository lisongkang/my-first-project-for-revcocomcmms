package com.maywide.biz.inter.pojo.saveSalesTerminal;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/3/5 0001.
 */
public class saveSalesTerminalInfoBossResp implements Serializable {
    private String cminfo;
    private String feename;
    private String linkmaxnum;
    private String newAddPermark;
    private String orderid;
    private String ordertype;
    private String splitInfoList;
    private String sums;

    public String getCminfo() {
        return cminfo;
    }

    public void setCminfo(String cminfo) {
        this.cminfo = cminfo;
    }

    public String getFeename() {
        return feename;
    }

    public void setFeename(String feename) {
        this.feename = feename;
    }

    public String getLinkmaxnum() {
        return linkmaxnum;
    }

    public void setLinkmaxnum(String linkmaxnum) {
        this.linkmaxnum = linkmaxnum;
    }

    public String getNewAddPermark() {
        return newAddPermark;
    }

    public void setNewAddPermark(String newAddPermark) {
        this.newAddPermark = newAddPermark;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public String getSplitInfoList() {
        return splitInfoList;
    }

    public void setSplitInfoList(String splitInfoList) {
        this.splitInfoList = splitInfoList;
    }

    public String getSums() {
        return sums;
    }

    public void setSums(String sums) {
        this.sums = sums;
    }
}
