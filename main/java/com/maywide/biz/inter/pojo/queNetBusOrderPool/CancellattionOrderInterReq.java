package com.maywide.biz.inter.pojo.queNetBusOrderPool;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

/**
 * Created by lisongkang on 2019/7/18 0001.
 */
public class CancellattionOrderInterReq extends BaseApiRequest {
    private String preacceptserialno;//预受理流水号
    private String reason;//处理原因

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
}
