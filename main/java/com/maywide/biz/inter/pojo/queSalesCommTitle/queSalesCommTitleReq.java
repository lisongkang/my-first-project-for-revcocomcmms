package com.maywide.biz.inter.pojo.queSalesCommTitle;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

/**
 * Created by lisongkang on 2019/5/17 0001.
 */
public class queSalesCommTitleReq extends BaseApiRequest implements java.io.Serializable {
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
