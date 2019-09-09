package com.maywide.biz.inter.pojo.queLoid;

import java.io.Serializable;

public class VlanInfo implements Serializable {

    //用户编号
    private Long servid;
    //业务类型  2：宽带 3：互动
    private String permark;
    //vlan值
    private String vlan;

    public Long getServid() {
        return servid;
    }

    public void setServid(Long servid) {
        this.servid = servid;
    }

    public String getPermark() {
        return permark;
    }

    public void setPermark(String permark) {
        this.permark = permark;
    }

    public String getVlan() {
        return vlan;
    }

    public void setVlan(String vlan) {
        this.vlan = vlan;
    }
}
