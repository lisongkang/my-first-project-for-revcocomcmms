package com.maywide.biz.inter.pojo.bizMindate;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/3/8 0001.
 */
public class BizMindateBossReq implements Serializable {

    private String custid;

    private String opertype;

    private List<reviseMindateSalesParam> salesidparams;

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getOpertype() {
        return opertype;
    }

    public void setOpertype(String opertype) {
        this.opertype = opertype;
    }

    public List<reviseMindateSalesParam> getSalesidparams() {
        return salesidparams;
    }

    public void setSalesidparams(List<reviseMindateSalesParam> salesidparams) {
        this.salesidparams = salesidparams;
    }
}
