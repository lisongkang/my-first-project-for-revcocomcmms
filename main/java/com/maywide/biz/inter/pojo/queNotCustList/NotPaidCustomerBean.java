package com.maywide.biz.inter.pojo.queNotCustList;

public class NotPaidCustomerBean {

	private long custId;

    private String custName;

    private String linkMobile;

    private String linkAddr;

    private String account;

    private String gridcode;

    private String gridname;

    public long getCustId() {
        return custId;
    }

    public void setCustId(long custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getLinkMobile() {
        return linkMobile;
    }

    public void setLinkMobile(String linkMobile) {
        this.linkMobile = linkMobile;
    }

    public String getLinkAddr() {
        return linkAddr;
    }

    public void setLinkAddr(String linkAddr) {
        this.linkAddr = linkAddr;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getGridcode() {
        return gridcode;
    }

    public void setGridcode(String gridcode) {
        this.gridcode = gridcode;
    }

    public String getGridname() {
        return gridname;
    }

    public void setGridname(String gridname) {
        this.gridname = gridname;
    }
}
