package com.maywide.biz.inter.pojo.authrandomcode;

public class AuthRandomCodeUapReq implements java.io.Serializable {
    private String phoneNum;
    private String randomCode;
    private String opcode;
    
    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }

    public String getOpcode() {
        return opcode;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }
    
}
