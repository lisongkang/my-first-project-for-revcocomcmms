package com.maywide.biz.inter.pojo.queincome;

import java.util.List;

public class IncomeRanksBO implements java.io.Serializable {

    private String gridmgr;//营收总金额  
    private String gridmgrname;//营收总金额  
    private String totalfees;//营收总金额  
    private String rank;//排名  
    private List<IncomesBO> incomes;//订单统计列表
    public String getTotalfees() {
        return totalfees;
    }
    public void setTotalfees(String totalfees) {
        this.totalfees = totalfees;
    }
    public String getRank() {
        return rank;
    }
    public void setRank(String rank) {
        this.rank = rank;
    }
    public List<IncomesBO> getIncomes() {
        return incomes;
    }
    public void setIncomes(List<IncomesBO> incomes) {
        this.incomes = incomes;
    }
    public String getGridmgr() {
        return gridmgr;
    }
    public void setGridmgr(String gridmgr) {
        this.gridmgr = gridmgr;
    }
    public String getGridmgrname() {
        return gridmgrname;
    }
    public void setGridmgrname(String gridmgrname) {
        this.gridmgrname = gridmgrname;
    }
    
}
