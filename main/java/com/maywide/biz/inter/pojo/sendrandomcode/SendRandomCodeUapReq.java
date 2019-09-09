package com.maywide.biz.inter.pojo.sendrandomcode;

public class SendRandomCodeUapReq implements java.io.Serializable {
    private String phoneNum;
    private String userIP;
    private String branchNO;
    private String smsContent;
    private String opcode;
    private String areaid;
    
    public String getPhoneNum() {
        return phoneNum;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    public String getUserIP() {
        return userIP;
    }
    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }
    public String getBranchNO() {
        return branchNO;
    }
    public void setBranchNO(String branchNO) {
        this.branchNO = branchNO;
    }
    public String getSmsContent() {
        return smsContent;
    }
    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }
    public String getOpcode() {
        return opcode;
    }
    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }
    public String getAreaid() {
        return areaid;
    }
    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

}
