package com.maywide.biz.inter.pojo.saveSalesTerminal;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

/**
 * Created by lisongkang on 2019/3/5 0001.
 */
public class saveSalesTerminalInfoInterReq extends BaseApiRequest {
    private String custid;
    private String custname;
    private String addr;

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    private String patchid;
    private String kind;
    private String supperid;
    private String subkind;
    private String rsrdfees;
    private String nums;
    private String delivermode;
    private String receiptname;
    private String receipttel;
    private String receiptcity;
    private String receiptaddr;
    private String receiptarea;

    public String getReceiptarea() {
        return receiptarea;
    }

    public void setReceiptarea(String receiptarea) {
        this.receiptarea = receiptarea;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getPatchid() {
        return patchid;
    }

    public void setPatchid(String patchid) {
        this.patchid = patchid;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getSupperid() {
        return supperid;
    }

    public void setSupperid(String supperid) {
        this.supperid = supperid;
    }

    public String getSubkind() {
        return subkind;
    }

    public void setSubkind(String subkind) {
        this.subkind = subkind;
    }

    public String getRsrdfees() {
        return rsrdfees;
    }

    public void setRsrdfees(String rsrdfees) {
        this.rsrdfees = rsrdfees;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public String getDelivermode() {
        return delivermode;
    }

    public void setDelivermode(String delivermode) {
        this.delivermode = delivermode;
    }

    public String getReceiptname() {
        return receiptname;
    }

    public void setReceiptname(String receiptname) {
        this.receiptname = receiptname;
    }

    public String getReceipttel() {
        return receipttel;
    }

    public void setReceipttel(String receipttel) {
        this.receipttel = receipttel;
    }

    public String getReceiptcity() {
        return receiptcity;
    }

    public void setReceiptcity(String receiptcity) {
        this.receiptcity = receiptcity;
    }

    public String getReceiptaddr() {
        return receiptaddr;
    }

    public void setReceiptaddr(String receiptaddr) {
        this.receiptaddr = receiptaddr;
    }
}
