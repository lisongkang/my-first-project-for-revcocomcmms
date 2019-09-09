package com.maywide.biz.inter.pojo.queNetBusOrderPool;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/7/18 0001.
 */
public class CancellattionOrderInterResp implements Serializable {
    private String preacceptserialno;//预受理流水号

    public String getPreacceptserialno() {
        return preacceptserialno;
    }

    public void setPreacceptserialno(String preacceptserialno) {
        this.preacceptserialno = preacceptserialno;
    }
}
