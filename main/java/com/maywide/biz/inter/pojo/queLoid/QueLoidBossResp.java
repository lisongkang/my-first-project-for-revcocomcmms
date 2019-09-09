package com.maywide.biz.inter.pojo.queLoid;

import java.util.List;

public class QueLoidBossResp {

    //设备编号
    private String devno;
    //宽带密码
    private String loid;
    //vlan数组
    private List<VlanInfo> vlanlist;

    public String getDevno() {
        return devno;
    }

    public void setDevno(String devno) {
        this.devno = devno;
    }

    public String getLoid() {
        return loid;
    }

    public void setLoid(String loid) {
        this.loid = loid;
    }

    public List<VlanInfo> getVlanlist() {
        return vlanlist;
    }

    public void setVlanlist(List<VlanInfo> vlanlist) {
        this.vlanlist = vlanlist;
    }

}
