package com.maywide.biz.inter.pojo.queMarketingComment;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

/**
 * Created by lisongkang on 2019/6/19 0001.
 */
public class QueReCommOrCountReq extends BaseApiRequest implements java.io.Serializable{
    private String visitMethod;
    private String custName;
    private String stime;
    private String etime;
    private String operid;
    private String city;

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getOperid() {
        return operid;
    }

    public void setOperid(String operid) {
        this.operid = operid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVisitMethod() {
        return visitMethod;
    }

    public void setVisitMethod(String visitMethod) {
        this.visitMethod = visitMethod;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }
}
