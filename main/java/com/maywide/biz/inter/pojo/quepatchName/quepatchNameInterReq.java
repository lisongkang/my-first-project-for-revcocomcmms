package com.maywide.biz.inter.pojo.quepatchName;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

/**
 * Created by lisongkang on 2019/3/11 0001.
 */
public class quepatchNameInterReq extends BaseApiRequest {

    private String patchid;

    public String getPatchid() {
        return patchid;
    }

    public void setPatchid(String patchid) {
        this.patchid = patchid;
    }
}
