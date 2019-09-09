package com.maywide.biz.inter.pojo.queordercnt;

public class OrdercntsBO implements java.io.Serializable {

	private String opcode;// 业务代码 
	private String opcodename;//业务代码名称  
	private String cnt;//订单数	
	
    public String getOpcode() {
        return opcode;
    }
    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }
    public String getOpcodename() {
        return opcodename;
    }
    public void setOpcodename(String opcodename) {
        this.opcodename = opcodename;
    }
    public String getCnt() {
        return cnt;
    }
    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    
}
