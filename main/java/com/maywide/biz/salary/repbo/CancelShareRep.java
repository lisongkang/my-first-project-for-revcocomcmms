package com.maywide.biz.salary.repbo;

public class CancelShareRep {
    //0成功 1失败
    private String rtcode;
    //结果说明
    private String message;

    public CancelShareRep(){}

    public CancelShareRep(String rtcode, String message) {
        this.rtcode = rtcode;
        this.message = message;
    }

    public String getRtcode() {
        return rtcode;
    }

    public void setRtcode(String rtcode) {
        this.rtcode = rtcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    
}
