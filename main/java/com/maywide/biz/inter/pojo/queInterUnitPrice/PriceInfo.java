package com.maywide.biz.inter.pojo.queInterUnitPrice;

/**
 * Created by lisongkang on 2019/8/12 0001.
 */
public class PriceInfo {
    private String type;// 0基础线  1标准线 2挑战线
    private Double bottomprice;
    private Double topprice;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getBottomprice() {
        return bottomprice;
    }

    public void setBottomprice(Double bottomprice) {
        this.bottomprice = bottomprice;
    }

    public Double getTopprice() {
        return topprice;
    }

    public void setTopprice(Double topprice) {
        this.topprice = topprice;
    }
}
