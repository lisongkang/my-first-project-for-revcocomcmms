package com.maywide.biz.inter.pojo.queTerminalInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/3/5 0001.
 */
public class TerminalInfoBossResp implements Serializable {
    private List<TerminallInfoParams> terminallInfoBossList;

    public List<TerminallInfoParams> getTerminallInfoBossList() {
        return terminallInfoBossList;
    }

    public void setTerminallInfoBossList(List<TerminallInfoParams> terminallInfoBossList) {
        this.terminallInfoBossList = terminallInfoBossList;
    }
}
