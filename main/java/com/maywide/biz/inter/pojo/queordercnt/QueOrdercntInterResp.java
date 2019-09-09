package com.maywide.biz.inter.pojo.queordercnt;

import java.util.List;

public class QueOrdercntInterResp  implements java.io.Serializable {

    private String totalcnt;//订单总数  
    private String rank;//排名  
    private List<OrdercntsBO> ordercnts;//订单统计列表
    
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
    public List<OrdercntsBO> getOrdercnts() {
        return ordercnts;
    }
    public void setOrdercnts(List<OrdercntsBO> ordercnts) {
        this.ordercnts = ordercnts;
    }
	
}
