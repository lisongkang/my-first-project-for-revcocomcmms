package com.maywide.biz.inter.pojo.addNetBusOrderPool;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

/**
 * Created by lisongkang on 2019/7/17 0001.
 */
public class AddNetBusOrderPoolReq extends BaseApiRequest {
    private String preserialno;//预受理流水号

    public String getPreserialno() {
        return preserialno;
    }

    public void setPreserialno(String preserialno) {
        this.preserialno = preserialno;
    }
}
