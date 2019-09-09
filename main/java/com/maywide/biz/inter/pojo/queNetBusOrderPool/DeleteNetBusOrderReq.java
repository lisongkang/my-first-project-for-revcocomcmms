package com.maywide.biz.inter.pojo.queNetBusOrderPool;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

/**
 * Created by lisongkang on 2019/7/18 0001.
 */
public class DeleteNetBusOrderReq extends BaseApiRequest {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
