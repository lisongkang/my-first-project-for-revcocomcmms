package com.maywide.biz.inter.pojo.queservprddet;

import java.util.Date;
import java.util.List;

public class ServPrddetsBO implements java.io.Serializable {
    private String pcode;//产品编码
    private String pname;//产品名称
    private String yxname;//所属营销方案名称
    private Date stime;//产品开始时间
    private Date etime;//产品结束时间
    private String fees;//金额
    private String payway;//缴费方式
    private  Date paytime;//缴费时间
    private  String channel;//缴费渠道
    
    public String getPcode() {
        return pcode;
    }
    public void setPcode(String pcode) {
        this.pcode = pcode;
    }
    public String getPname() {
        return pname;
    }
    public void setPname(String pname) {
        this.pname = pname;
    }
    public String getYxname() {
        return yxname;
    }
    public void setYxname(String yxname) {
        this.yxname = yxname;
    }
    public Date getStime() {
        return stime;
    }
    public void setStime(Date stime) {
        this.stime = stime;
    }
    public Date getEtime() {
        return etime;
    }
    public void setEtime(Date etime) {
        this.etime = etime;
    }
    public String getFees() {
        return fees;
    }
    public void setFees(String fees) {
        this.fees = fees;
    }
    public String getPayway() {
        return payway;
    }
    public void setPayway(String payway) {
        this.payway = payway;
    }
    public Date getPaytime() {
        return paytime;
    }
    public void setPaytime(Date paytime) {
        this.paytime = paytime;
    }
    public String getChannel() {
        return channel;
    }
    public void setChannel(String channel) {
        this.channel = channel;
    }
    
    
}
