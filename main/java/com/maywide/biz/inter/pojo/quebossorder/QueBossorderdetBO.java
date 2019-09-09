package com.maywide.biz.inter.pojo.quebossorder;

import java.util.List;

public class QueBossorderdetBO implements java.io.Serializable {
private String keyno;
private String ordertype;
private String pcode;
private String pname;
private String count;
private String unit;
private String ispostpone;
private String fee;

public String getKeyno() {
    return keyno;
}
public void setKeyno(String keyno) {
    this.keyno = keyno;
}
public String getOrdertype() {
    return ordertype;
}
public void setOrdertype(String ordertype) {
    this.ordertype = ordertype;
}
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
public String getCount() {
    return count;
}
public void setCount(String count) {
    this.count = count;
}
public String getUnit() {
    return unit;
}
public void setUnit(String unit) {
    this.unit = unit;
}
public String getIspostpone() {
    return ispostpone;
}
public void setIspostpone(String ispostpone) {
    this.ispostpone = ispostpone;
}
public String getFee() {
    return fee;
}
public void setFee(String fee) {
    this.fee = fee;
}


}
