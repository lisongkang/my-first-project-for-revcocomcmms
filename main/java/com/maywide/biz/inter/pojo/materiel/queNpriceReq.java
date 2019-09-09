package com.maywide.biz.inter.pojo.materiel;

import com.maywide.biz.core.pojo.api.BaseApiRequest;


/**
 * Created by lisongkang on 2019/4/23 0001.
 */
public class queNpriceReq extends BaseApiRequest implements java.io.Serializable {
    private String corpcode;
    private String invcode;


    public String getCorpcode() {
        return corpcode;
    }

    public void setCorpcode(String corpcode) {
        this.corpcode = corpcode;
    }

    public String getInvcode() {
        return invcode;
    }

    public void setInvcode(String invcode) {
        this.invcode = invcode;
    }
}
