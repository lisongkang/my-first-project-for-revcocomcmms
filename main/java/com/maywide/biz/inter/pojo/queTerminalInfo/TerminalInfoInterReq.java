package com.maywide.biz.inter.pojo.queTerminalInfo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

/**
 * Created by lisongkang on 2019/3/5 0001.
 */
public class TerminalInfoInterReq extends BaseApiRequest {
    private String kind;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
