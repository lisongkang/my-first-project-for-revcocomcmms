package com.maywide.biz.sta.assindex.pojo;

import java.util.List;

import com.maywide.biz.inter.pojo.bizpreprocess.OrderParamsInterBO;
import com.maywide.biz.inter.pojo.queservstinfo.CustInfosBO;

public class StaAssindexParamBO implements java.io.Serializable {
  
    private String city;
    private String assparam;//考核方案类型
    private String assid;//考核方案id
    private List<Long> gridids;//统计网格,格式:gridid1,gridid2,gridid3...
    private String stime;//统计开始时段
    private String etime;//统计结束时段
    
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
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
    public List<Long> getGridids() {
        return gridids;
    }
    public void setGridids(List<Long> gridids) {
        this.gridids = gridids;
    }
    public String getAssid() {
        return assid;
    }
    public void setAssid(String assid) {
        this.assid = assid;
    }
    public String getAssparam() {
        return assparam;
    }
    public void setAssparam(String assparam) {
        this.assparam = assparam;
    }

    
}
