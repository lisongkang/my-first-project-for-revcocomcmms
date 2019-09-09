package com.maywide.biz.inter.pojo.quebossorder;

import java.util.List;

public class QueBossorderBO {

    private String orderid;
    private String status;
    private String ordertype;
    private String pname;
    private String fees;
    private String feecode;
    private String createtime;
    private String commitmsg;
    private List<QueBossorderdetBO> orderdets;
    
    public String getOrderid() {
        return orderid;
    }
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getOrdertype() {
        return ordertype;
    }
    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }
    public String getPname() {
        return pname;
    }
    public void setPname(String pname) {
        this.pname = pname;
    }
    public String getFees() {
        return fees;
    }
    public void setFees(String fees) {
        this.fees = fees;
    }
    public String getFeecode() {
        return feecode;
    }
    public void setFeecode(String feecode) {
        this.feecode = feecode;
    }
    public String getCreatetime() {
        return createtime;
    }
    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
    public String getCommitmsg() {
        return commitmsg;
    }
    public void setCommitmsg(String commitmsg) {
        this.commitmsg = commitmsg;
    }
    public List<QueBossorderdetBO> getOrderdets() {
        return orderdets;
    }
    public void setOrderdets(List<QueBossorderdetBO> orderdets) {
        this.orderdets = orderdets;
    }
    
    

    
}
