package com.maywide.biz.inter.pojo.constructionQuota;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

/**
 * Created by lisongkang on 2019/3/31 0001.
 */
public class constructionQuotaInterReq extends BaseApiRequest implements java.io.Serializable {
    //private String pagesize;//分页大小
    //private String currentPage;//请求页
    private String city;//地市

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
