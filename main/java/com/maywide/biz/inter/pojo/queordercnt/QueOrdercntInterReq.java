package com.maywide.biz.inter.pojo.queordercnt;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueOrdercntInterReq  extends BaseApiRequest implements java.io.Serializable {
	private String gridmanager;//网格经理
	private String stime;//开始时间 >= stime
	private String etime;//截至时间 < etime
	private String status;//订单状态 :1-成功,2-失败,3-未完成
	
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
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}
