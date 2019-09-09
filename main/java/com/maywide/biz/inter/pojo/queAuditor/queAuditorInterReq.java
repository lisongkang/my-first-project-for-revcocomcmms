package com.maywide.biz.inter.pojo.queAuditor;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/4/3 0001.
 */
public class queAuditorInterReq extends BaseApiRequest implements Serializable {

    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
