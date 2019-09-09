package com.maywide.biz.inter.pojo.queAuditor;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/4/4 0001.
 */
public class queAuditorInfo implements Serializable {
    private String operid;
    private String loginname;
    private String name;

    public String getOperid() {
        return operid;
    }

    public void setOperid(String operid) {
        this.operid = operid;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
