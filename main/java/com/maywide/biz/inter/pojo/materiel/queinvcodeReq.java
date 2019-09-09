package com.maywide.biz.inter.pojo.materiel;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

/**
 * Created by lisongkang on 2019/4/23 0001.
 */
public class queinvcodeReq extends BaseApiRequest implements java.io.Serializable{
    private String corpcode;
    private String invclassid;
    private String invname;

    public String getCorpcode() {
        return corpcode;
    }

    public void setCorpcode(String corpcode) {
        this.corpcode = corpcode;
    }

    public String getInvclassid() {
        return invclassid;
    }

    public void setInvclassid(String invclassid) {
        this.invclassid = invclassid;
    }

    public String getInvname() {
        return invname;
    }

    public void setInvname(String invname) {
        this.invname = invname;
    }
}
