package com.maywide.biz.sta.assindex.pojo;

import java.util.Date;
import java.util.List;

import com.maywide.biz.inter.pojo.bizpreprocess.OrderParamsInterBO;
import com.maywide.biz.inter.pojo.queservstinfo.CustInfosBO;

public class StaAssindexBO implements java.io.Serializable {
  
    private Date stadate;
    private String assid;
    private String assparamname;
    private String assname;
    private String gridid;
    private String gridname;
    private Date sdate;
    private Date edate;
    private String assnum;
    private String comnum;
    private String diffnum;
    
    public Date getStadate() {
        return stadate;
    }
    public void setStadate(Date stadate) {
        this.stadate = stadate;
    }
    public String getAssid() {
        return assid;
    }
    public void setAssid(String assid) {
        this.assid = assid;
    }
    public String getAssparamname() {
        return assparamname;
    }
    public void setAssparamname(String assparamname) {
        this.assparamname = assparamname;
    }
    public String getAssname() {
        return assname;
    }
    public void setAssname(String assname) {
        this.assname = assname;
    }
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
    public Date getSdate() {
        return sdate;
    }
    public void setSdate(Date sdate) {
        this.sdate = sdate;
    }
    public Date getEdate() {
        return edate;
    }
    public void setEdate(Date edate) {
        this.edate = edate;
    }
    public String getAssnum() {
        return assnum;
    }
    public void setAssnum(String assnum) {
        this.assnum = assnum;
    }
    public String getComnum() {
        return comnum;
    }
    public void setComnum(String comnum) {
        this.comnum = comnum;
    }
    public String getDiffnum() {
        return diffnum;
    }
    public void setDiffnum(String diffnum) {
        this.diffnum = diffnum;
    }

}
