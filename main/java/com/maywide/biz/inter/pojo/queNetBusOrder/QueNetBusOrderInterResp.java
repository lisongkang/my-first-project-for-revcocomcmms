package com.maywide.biz.inter.pojo.queNetBusOrder;

/**
 * Created by lisongkang on 2019/7/11 0001.
 */
public class QueNetBusOrderInterResp extends QueNetBusOrderBossResp{
    public QueNetBusOrderInterResp(){

    }

    public QueNetBusOrderInterResp(QueNetBusOrderBossResp resp){
        this.setPageno(resp.getPageno());
        this.setPagesize(resp.getPagesize());
        this.setTotalpage(resp.getTotalpage());
        this.setTotalsize(resp.getTotalsize());
        this.setRetlist(resp.getRetlist());
    }

    private int orderNum;

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }
}
