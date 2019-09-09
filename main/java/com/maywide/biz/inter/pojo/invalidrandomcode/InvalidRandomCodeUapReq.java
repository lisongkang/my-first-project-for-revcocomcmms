package com.maywide.biz.inter.pojo.invalidrandomcode;

public class InvalidRandomCodeUapReq implements java.io.Serializable {
    private String phoneNum;
    private String opcode;
    
    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getOpcode() {
        return opcode;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }
}
