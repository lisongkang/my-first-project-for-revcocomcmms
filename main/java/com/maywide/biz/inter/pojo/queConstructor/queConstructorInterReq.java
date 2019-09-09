package com.maywide.biz.inter.pojo.queConstructor;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/4/4 0001.
 */
public class queConstructorInterReq extends BaseApiRequest implements Serializable {

    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
