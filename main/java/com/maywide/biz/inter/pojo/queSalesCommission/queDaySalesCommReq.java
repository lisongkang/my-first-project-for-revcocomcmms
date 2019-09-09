package com.maywide.biz.inter.pojo.queSalesCommission;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

/**
 * Created by lisongkang on 2019/5/20 0001.
 */
public class queDaySalesCommReq extends BaseApiRequest implements java.io.Serializable{
    private Long operid;//请求操作员ID
    private String stadate;//请求起始日期
    private String endDate;//請求結束日期
    private String deptid;//请求的部门
    private String city;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getOperid() {
        return operid;
    }

    public void setOperid(Long operid) {
        this.operid = operid;
    }

    public String getStadate() {
        return stadate;
    }

    public void setStadate(String stadate) {
        this.stadate = stadate;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
