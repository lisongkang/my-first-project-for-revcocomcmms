package com.maywide.biz.inter.pojo.queryOdsLoginInfo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueryOdsLoginInfoReq extends BaseApiRequest {


    private String operid;

    public String getOperid() {
        return operid;
    }

    public void setOperid(String operid) {
        this.operid = operid;
    }
}
