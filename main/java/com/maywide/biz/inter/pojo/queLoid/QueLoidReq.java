package com.maywide.biz.inter.pojo.queLoid;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueLoidReq extends BaseApiRequest {
    //用户编号
    private String Servid;
    //设备编号
    private String Devno;

    public String getServid() {
        return Servid;
    }

    public void setServid(String servid) {
        Servid = servid;
    }

    public String getDevno() {
        return Devno;
    }

    public void setDevno(String devno) {
        Devno = devno;
    }
}
