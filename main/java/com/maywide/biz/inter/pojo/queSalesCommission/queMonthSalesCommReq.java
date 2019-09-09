package com.maywide.biz.inter.pojo.queSalesCommission;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

/**
 * Created by lisongkang on 2019/5/20 0001.
 */
public class queMonthSalesCommReq extends BaseApiRequest implements java.io.Serializable  {
    private Long operid;//请求操作员ID
    private String staMonth;//请求月份
    private String deptid;//请求的部门
    private String city;

    public Long getOperid() {
        return operid;
    }

    public void setOperid(Long operid) {
        this.operid = operid;
    }

    public String getStaMonth() {
        return staMonth;
    }

    public void setStaMonth(String staMonth) {
        this.staMonth = staMonth;
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
