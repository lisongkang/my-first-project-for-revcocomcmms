package com.maywide.biz.salary.reqbo;

public class CancelShareReq {
    private String recid; //分享id
    private String disreason;//取消原因

    public String getRecid() {
        return recid;
    }

    public void setRecid(String recid) {
        this.recid = recid;
    }

    public String getDisreason() {
        return disreason;
    }

    public void setDisreason(String disreason) {
        this.disreason = disreason;
    }
}
