package com.maywide.biz.inter.pojo.queLoid;

import java.io.Serializable;

public class QueLoidData implements Serializable {
    //
    private String loidName;
    //值
    private String value;
    //类型  1是loid 2是vlan
    private String type;
    //业务类型
    private String permark;

    public String getLoidName() {
        return loidName;
    }

    public void setLoidName(String loidName) {
        this.loidName = loidName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPermark() {
        return permark;
    }

    public void setPermark(String permark) {
        this.permark = permark;
    }
}
