package com.maywide.biz.inter.pojo.queInterUnitPrice;

/**
 * Created by lisongkang on 2019/8/12 0001.
 */
public class QueInterUnitPriceLineReq {

    private String month;//选择的月份  201908

    private String data;//选择的当前日期的前一天  20190812

    private String gridcode;//选择的网格编码  BBDF001

    private String operid;

    public String getOperid() {
        return operid;
    }

    public void setOperid(String operid) {
        this.operid = operid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getGridcode() {
        return gridcode;
    }

    public void setGridcode(String gridcode) {
        this.gridcode = gridcode;
    }
}
