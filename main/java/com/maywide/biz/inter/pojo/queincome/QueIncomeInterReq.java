package com.maywide.biz.inter.pojo.queincome;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueIncomeInterReq  extends BaseApiRequest implements java.io.Serializable {
	private String gridmanager;//网格经理
	private String stime;//开始时间 >= stime
	private String etime;//截至时间 < etime
	private String opcode;//
	
    public String getGridmanager() {
        return gridmanager;
    }
    public void setGridmanager(String gridmanager) {
        this.gridmanager = gridmanager;
    }
    public String getStime() {
        return stime;
    }
    public void setStime(String stime) {
        this.stime = stime;
    }
    public String getEtime() {
        return etime;
    }
    public void setEtime(String etime) {
        this.etime = etime;
    }
    public String getOpcode() {
        return opcode;
    }
    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }


}
