package com.maywide.biz.inter.pojo.bizMindate;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/3/8 0001.
 */
public class BizMindateReq extends BaseApiRequest implements Serializable {
    private String custid;

    private String custname;

    private String opertype;

    private String addr;

    private List<reviseMindateSalesParam> salesidparams;

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
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
