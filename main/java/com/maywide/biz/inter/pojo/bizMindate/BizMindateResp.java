package com.maywide.biz.inter.pojo.bizMindate;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/3/8 0001.
 */
public class BizMindateResp implements Serializable {

    private String custorderid;

    public String getCustorderid() {
        return custorderid;
    }

    public void setCustorderid(String custorderid) {
        this.custorderid = custorderid;
    }
}
