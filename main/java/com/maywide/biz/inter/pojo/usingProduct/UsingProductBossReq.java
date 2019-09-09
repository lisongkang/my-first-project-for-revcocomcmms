package com.maywide.biz.inter.pojo.usingProduct;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

import java.io.Serializable;

/**
 * 查询在用产品boss请求参数
 */
public class UsingProductBossReq extends BaseApiRequest implements Serializable {
    /**
     * custid : 3600390521
     * servid : 3601745728
     */

    private String custid;
    private String servid;


    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getServid() {
        return servid;
    }

    public void setServid(String servid) {
        this.servid = servid;
    }
}
