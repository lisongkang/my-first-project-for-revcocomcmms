package com.maywide.biz.inter.pojo.queincome;

public class IncomesBO implements java.io.Serializable {

	private String opcode;// 业务代码 
	private String opcodename;//业务代码名称  
	private String fees;//营收金额	
	
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
    public String getFees() {
        return fees;
    }
    public void setFees(String fees) {
        this.fees = fees;
    }

    
}
