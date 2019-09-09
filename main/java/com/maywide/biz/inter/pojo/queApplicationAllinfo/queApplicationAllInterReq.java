package com.maywide.biz.inter.pojo.queApplicationAllinfo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

/**
 * Created by lisongkang on 2019/3/31 0001.
 */
public class queApplicationAllInterReq extends BaseApiRequest implements java.io.Serializable {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
