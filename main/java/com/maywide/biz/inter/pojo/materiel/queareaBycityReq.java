package com.maywide.biz.inter.pojo.materiel;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

/**
 * Created by lisongkang on 2019/4/22 0001.
 */
public class queareaBycityReq extends BaseApiRequest implements java.io.Serializable {
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
