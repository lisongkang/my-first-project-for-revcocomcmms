package com.maywide.biz.inter.pojo.materiel;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

/**
 * Created by lisongkang on 2019/4/22 0001.
 */
public class queinvclassByNameReq extends BaseApiRequest implements java.io.Serializable {
    private String invclassname;

    public String getInvclassname() {
        return invclassname;
    }

    public void setInvclassname(String invclassname) {
        this.invclassname = invclassname;
    }
}
