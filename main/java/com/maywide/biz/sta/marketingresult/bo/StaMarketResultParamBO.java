package com.maywide.biz.sta.marketingresult.bo;

import java.util.List;

/**
 * <p>
 * 营销业务单查询条件对象:
 * <p>
 * Create at 2015年9月17日
 * 
 * @author pengjianqiang
 */
@SuppressWarnings("serial")
public class StaMarketResultParamBO implements java.io.Serializable {
    private List<Long> areaids;
    private List<Long> departs;
    private List<Long> operators;
    private String     customer;
    private String     orderStatus;
    private String     bossSerialNo;
    private String     opCode;
    private String     timeRange;
    private String     stime;
    private String     etime;
    private String     custid;
    private String     ifFilterRegression;

    private List<String> payStatuss;


    public List<String> getPayStatuss() {
        return payStatuss;
    }

    public void setPayStatuss(List<String> payStatuss) {
        this.payStatuss = payStatuss;
    }

    public String getIfFilterRegression() {
        return ifFilterRegression;
    }

    public void setIfFilterRegression(String ifFilterRegression) {
        this.ifFilterRegression = ifFilterRegression;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public List<Long> getAreaids() {
        return areaids;
    }

    public void setAreaids(List<Long> areaids) {
        this.areaids = areaids;
    }

    public List<Long> getDeparts() {
        return departs;
    }

    public void setDeparts(List<Long> departs) {
        this.departs = departs;
    }

    public List<Long> getOperators() {
        return operators;
    }

    public void setOperators(List<Long> operators) {
        this.operators = operators;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getBossSerialNo() {
        return bossSerialNo;
    }

    public void setBossSerialNo(String bossSerialNo) {
        this.bossSerialNo = bossSerialNo;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
        String[] timeRangeArray = this.timeRange.split("～");
        this.setStime(timeRangeArray[0].trim() + " 00:00:00");
        this.setEtime(timeRangeArray[1].trim() + " 23:59:59");
    }

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
}