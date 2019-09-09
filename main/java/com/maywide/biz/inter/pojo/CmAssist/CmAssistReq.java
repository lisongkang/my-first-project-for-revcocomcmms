package com.maywide.biz.inter.pojo.CmAssist;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

import java.io.Serializable;

/**
 * @author wzy
 */
public class CmAssistReq extends BaseApiRequest implements Serializable {
    private String servid;

    public String getServid() {
        return servid;
    }

    public void setServid(String servid) {
        this.servid = servid;
    }
}
