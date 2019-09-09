package com.maywide.biz.inter.pojo.queEarlyWarLoss;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/7/4 0001.
 */
public class changeEarlyLossReq extends BaseApiRequest implements Serializable {
    private Long id;
    private String biId;//bi标识
    private String tableNmae;//bi表名
    private String custid;//客户编号
    private String custName;//客户姓名
    private String whgridCode;//网格编号
    private String whgridNmae;//网格名称
    private String address;//地址
    private String addressCode;//地址编号 唯一标识一个大标题下
    private String iphone;//手机号码
    private int status;//状态  0未处理 1待处理 2已完成
    private String time;//对应时间
    private Double tomothArreas;//往月欠费
    private Double thisArreas;//本月欠费
    private String city;
    private String areaid;

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBiId() {
        return biId;
    }

    public void setBiId(String biId) {
        this.biId = biId;
    }

    public String getTableNmae() {
        return tableNmae;
    }

    public void setTableNmae(String tableNmae) {
        this.tableNmae = tableNmae;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getWhgridCode() {
        return whgridCode;
    }

    public void setWhgridCode(String whgridCode) {
        this.whgridCode = whgridCode;
    }

    public String getWhgridNmae() {
        return whgridNmae;
    }

    public void setWhgridNmae(String whgridNmae) {
        this.whgridNmae = whgridNmae;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getTomothArreas() {
        return tomothArreas;
    }

    public void setTomothArreas(Double tomothArreas) {
        this.tomothArreas = tomothArreas;
    }

    public Double getThisArreas() {
        return thisArreas;
    }

    public void setThisArreas(Double thisArreas) {
        this.thisArreas = thisArreas;
    }
}
