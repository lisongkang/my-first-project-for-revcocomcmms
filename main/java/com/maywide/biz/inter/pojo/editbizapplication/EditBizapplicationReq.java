package com.maywide.biz.inter.pojo.editbizapplication;

import com.maywide.biz.core.pojo.api.BaseApiRequest;
import org.quartz.DailyTimeIntervalTrigger;


/**
 * Created by lisongkang on 2019/3/1 0001.
 */
public class EditBizapplicationReq extends BaseApiRequest {
    private Long id;//项目id
    private String status;//状态

    private String appopinion;//审批意见
    private String approveid;//审批人id
    private String approveor;//审批人

    private String accepterid;//验收人id
    private String acceptername;//验收人
    private String accopinion;//验收意见

    private String settlementopinion;//分管审批意见

    public String getSettlementopinion() {
        return settlementopinion;
    }

    public void setSettlementopinion(String settlementopinion) {
        this.settlementopinion = settlementopinion;
    }

    public String getAccepterid() {
        return accepterid;
    }

    public void setAccepterid(String accepterid) {
        this.accepterid = accepterid;
    }

    public String getAcceptername() {
        return acceptername;
    }

    public void setAcceptername(String acceptername) {
        this.acceptername = acceptername;
    }

    public String getAccopinion() {
        return accopinion;
    }

    public void setAccopinion(String accopinion) {
        this.accopinion = accopinion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAppopinion() {
        return appopinion;
    }

    public void setAppopinion(String appopinion) {
        this.appopinion = appopinion;
    }

    public String getApproveid() {
        return approveid;
    }

    public void setApproveid(String approveid) {
        this.approveid = approveid;
    }

    public String getApproveor() {
        return approveor;
    }

    public void setApproveor(String approveor) {
        this.approveor = approveor;
    }
}
