package com.maywide.biz.sta.marketingresult.bo;

import java.util.Date;

/**
 * <p>
 * 营销业务单查询列表对象
 * <p>
 * Create at 2015年9月17日
 * 
 * @author pengjianqiang
 */
@SuppressWarnings("serial")
public class StaMarketingResultBO implements java.io.Serializable {
    private Long   orderid;
    private String customer;
    private String custid;
    private String orderStatus;
    private String bossSerialNo;
    private String opCode;
    private String depart;
    private String operator;
    private Date   optime;
    private String payStatus;
    private String paytime;
    private String fees;
    private String saleName;
    private String areaid;
    private String areaName;
    private String gridname;
    private String gridcode;
    private String operid;

    public String getOperid() {
        return operid;
    }

    public void setOperid(String operid) {
        this.operid = operid;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getGridname() {
        return gridname;
    }

    public void setGridname(String gridname) {
        this.gridname = gridname;
    }

    public String getGridcode() {
        return gridcode;
    }

    public void setGridcode(String gridcode) {
        this.gridcode = gridcode;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
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

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOptime() {
        return optime;
    }

    public void setOptime(Date optime) {
        this.optime = optime;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }
}