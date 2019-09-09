package com.maywide.biz.inter.pojo.queSalesCommission;

/**
 * Created by lisongkang on 2019/5/20 0001.
 */
public class salesCommDayInfo implements java.io.Serializable{
    private String tctypename;//提成项内容
    private String price;//单价
    private String nums;//数量
    private String fees;//总价

    public String getTctypename() {
        return tctypename;
    }

    public void setTctypename(String tctypename) {
        this.tctypename = tctypename;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }
}
