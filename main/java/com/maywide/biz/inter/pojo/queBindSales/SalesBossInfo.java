package com.maywide.biz.inter.pojo.queBindSales;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/7/20 0001.
 */
public class SalesBossInfo implements Serializable {
    private String bindsubkind;//设备子类型
    private String bindsubkindname;//设备子类型名称
    private String bindkind;//设备类型
    private String bindkindname;//设备名称
    private int salesnum;//销售数量
    private String useprop;//设备来源

    public String getBindsubkindname() {
        return bindsubkindname;
    }

    public void setBindsubkindname(String bindsubkindname) {
        this.bindsubkindname = bindsubkindname;
    }

    public String getBindkindname() {
        return bindkindname;
    }

    public void setBindkindname(String bindkindname) {
        this.bindkindname = bindkindname;
    }

    public String getBindsubkind() {
        return bindsubkind;
    }

    public void setBindsubkind(String bindsubkind) {
        this.bindsubkind = bindsubkind;
    }

    public String getBindkind() {
        return bindkind;
    }

    public void setBindkind(String bindkind) {
        this.bindkind = bindkind;
    }

    public int getSalesnum() {
        return salesnum;
    }

    public void setSalesnum(int salesnum) {
        this.salesnum = salesnum;
    }

    public String getUseprop() {
        return useprop;
    }

    public void setUseprop(String useprop) {
        this.useprop = useprop;
    }
}
