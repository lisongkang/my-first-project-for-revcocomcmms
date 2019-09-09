package com.maywide.biz.salary.reqbo;

public class ShareReq {
   private String srcid;  //采集编码
   private String groupcode;  //积分类型
   private String sendoper;  //发起方
   private String senddept;  //发起方部门
   private String accoper;  //接收方
   private String accdept;//接收方部门
   private String cent;  //分享积分
   private String reason; //分享说明

    public String getSrcid() {
        return srcid;
    }

    public void setSrcid(String srcid) {
        this.srcid = srcid;
    }

    public String getGroupcode() {
        return groupcode;
    }

    public void setGroupcode(String groupcode) {
        this.groupcode = groupcode;
    }

    public String getSendoper() {
        return sendoper;
    }

    public void setSendoper(String sendoper) {
        this.sendoper = sendoper;
    }

    public String getSenddept() {
        return senddept;
    }

    public void setSenddept(String senddept) {
        this.senddept = senddept;
    }

    public String getAccoper() {
        return accoper;
    }

    public void setAccoper(String accoper) {
        this.accoper = accoper;
    }

    public String getAccdept() {
        return accdept;
    }

    public void setAccdept(String accdept) {
        this.accdept = accdept;
    }

    public String getCent() {
        return cent;
    }

    public void setCent(String cent) {
        this.cent = cent;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
