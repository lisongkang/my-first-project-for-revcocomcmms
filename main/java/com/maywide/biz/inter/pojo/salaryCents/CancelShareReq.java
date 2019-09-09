package com.maywide.biz.inter.pojo.salaryCents;

import java.io.Serializable;

public class CancelShareReq implements Serializable {
    private String beforehandSrcid;
    private String cycleid;
    private  String operid;
    private String disreason;

    public String getBeforehandSrcid() {
        return beforehandSrcid;
    }

    public void setBeforehandSrcid(String beforehandSrcid) {
        this.beforehandSrcid = beforehandSrcid;
    }

    public String getCycleid() {
        return cycleid;
    }

    public void setCycleid(String cycleid) {
        this.cycleid = cycleid;
    }

    public String getOperid() {
        return operid;
    }

    public void setOperid(String operid) {
        this.operid = operid;
    }

    public String getDisreason() {
        return disreason;
    }

    public void setDisreason(String disreason) {
        this.disreason = disreason;
    }
}
