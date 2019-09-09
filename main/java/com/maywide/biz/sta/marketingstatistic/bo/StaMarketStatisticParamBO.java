package com.maywide.biz.sta.marketingstatistic.bo;

import java.util.List;

/**
 * <p>
 * 营销业务单查询条件对象:
 * <p>
 * Create at 2015年9月17日
 * 
 * @author pengjianqiang
 */
@SuppressWarnings("serial")
public class StaMarketStatisticParamBO implements java.io.Serializable {
    private List<Long> areaids;
    private String     timeRange;
    private String     stime;
    private String     etime;
    private String     ifFilterRegression;
    private List<Long> gridlist;
    private String      city;

    private List<String> orderStatuss;
    private List<String> payStatuss;

    public List<String> getOrderStatuss() {
        return orderStatuss;
    }

    public void setOrderStatuss(List<String> orderStatuss) {
        this.orderStatuss = orderStatuss;
    }

    public List<String> getPayStatuss() {
        return payStatuss;
    }

    public void setPayStatuss(List<String> payStatuss) {
        this.payStatuss = payStatuss;
    }

    public String getIfFilterRegression() {
        return ifFilterRegression;
    }

    public void setIfFilterRegression(String ifFilterRegression) {
        this.ifFilterRegression = ifFilterRegression;
    }

    public List<Long> getAreaids() {
        return areaids;
    }

    public void setAreaids(List<Long> areaids) {
        this.areaids = areaids;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
        String[] timeRangeArray = this.timeRange.split("～");
        this.setStime(timeRangeArray[0].trim() + " 00:00:00");
        this.setEtime(timeRangeArray[1].trim() + " 23:59:59");
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public List<Long> getGridlist() {
        return gridlist;
    }

    public void setGridlist(List<Long> gridlist) {
        this.gridlist = gridlist;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


}