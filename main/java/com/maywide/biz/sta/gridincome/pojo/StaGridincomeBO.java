package com.maywide.biz.sta.gridincome.pojo;

import java.util.List;

import com.maywide.biz.inter.pojo.bizpreprocess.OrderParamsInterBO;
import com.maywide.biz.inter.pojo.queservstinfo.CustInfosBO;

public class StaGridincomeBO implements java.io.Serializable {
  
    private String gridid;
    private String gridname;
    private String statime;
    private String income;
    
    public String getGridid() {
        return gridid;
    }
    public void setGridid(String gridid) {
        this.gridid = gridid;
    }
    public String getGridname() {
        return gridname;
    }
    public void setGridname(String gridname) {
        this.gridname = gridname;
    }
    public String getStatime() {
        return statime;
    }
    public void setStatime(String statime) {
        this.statime = statime;
    }
    public String getIncome() {
        return income;
    }
    public void setIncome(String income) {
        this.income = income;
    }
    
    
}
