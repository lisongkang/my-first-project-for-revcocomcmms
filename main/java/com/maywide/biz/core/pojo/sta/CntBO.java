package com.maywide.biz.core.pojo.sta;

import java.util.List;

public class CntBO  implements java.io.Serializable {

    private String objid;//对象id  
    private String objname;//对象名称 
    private String totalcnt;//订单总数  
    private String rank;//排名  
	
    public String getTotalcnt() {
        return totalcnt;
    }
    public void setTotalcnt(String totalcnt) {
        this.totalcnt = totalcnt;
    }
    public String getRank() {
        return rank;
    }
    public void setRank(String rank) {
        this.rank = rank;
    }
    public String getObjid() {
        return objid;
    }
    public void setObjid(String objid) {
        this.objid = objid;
    }
    public String getObjname() {
        return objname;
    }
    public void setObjname(String objname) {
        this.objname = objname;
    }

	
}
