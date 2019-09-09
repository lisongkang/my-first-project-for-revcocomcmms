package com.maywide.biz.inter.pojo.queNetBusOrderPool;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/7/18 0001.
 */
public class CancellattionOrderBossReq implements Serializable {
    private String preacceptserialno;//预受理流水号
    private String reason;//处理原因
    private String preacceptstatus;//预受理状态 2 失败

    public String getPreacceptserialno() {
        return preacceptserialno;
    }

    public void setPreacceptserialno(String preacceptserialno) {
        this.preacceptserialno = preacceptserialno;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPreacceptstatus() {
        return preacceptstatus;
    }

    public void setPreacceptstatus(String preacceptstatus) {
        this.preacceptstatus = preacceptstatus;
    }
}
