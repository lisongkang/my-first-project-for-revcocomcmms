package com.maywide.biz.inter.pojo.queincome;

import java.util.List;

public class QueIncomeInterResp  implements java.io.Serializable {

    private List<IncomeRanksBO> incomeRanks;//营收统计排名列表

    public List<IncomeRanksBO> getIncomeRanks() {
        return incomeRanks;
    }

    public void setIncomeRanks(List<IncomeRanksBO> incomeRanks) {
        this.incomeRanks = incomeRanks;
    }

}
