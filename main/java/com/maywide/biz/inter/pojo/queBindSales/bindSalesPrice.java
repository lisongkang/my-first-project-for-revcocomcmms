package com.maywide.biz.inter.pojo.queBindSales;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/7/24 0001.
 */
public class bindSalesPrice implements Serializable {
    private String bindsubkind;//设备子类型
    private String bindkind;//设备类型
    private String useprop;//設備來源   頁面自己選的那個

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

    public String getUseprop() {
        return useprop;
    }

    public void setUseprop(String useprop) {
        this.useprop = useprop;
    }


}
