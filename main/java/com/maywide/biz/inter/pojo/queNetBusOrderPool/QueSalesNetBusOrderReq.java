package com.maywide.biz.inter.pojo.queNetBusOrderPool;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

/**
 * Created by lisongkang on 2019/7/20 0001.
 */
public class QueSalesNetBusOrderReq extends BaseApiRequest {
    private String custid;

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }


}
