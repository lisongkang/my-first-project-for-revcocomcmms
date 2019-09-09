package com.maywide.biz.inter.entity;

import com.maywide.core.entity.PersistableEntity;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

/**
 * Created by lisongkang on 2019/8/6 0001.
 */
@Entity
@Table(name = "BIZ_APPLICATION_TRACK")
public class BizApplicationTrack extends PersistableEntity<Long> implements Persistable<Long> {

    private Long id; // 序列号
    private String proid;//申请单id
    private String subtime;//操作时间
    private String opinion;//意见
    private Long approveid;//操作人ID
    private String operationtype;//操作类型
    private String operationresult;//操作结果

    @Override
    @Transient
    public String getDisplay() {
        return null;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TKID", unique = true, length = 16)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public String getSubtime() {
        return subtime;
    }

    public void setSubtime(String subtime) {
        this.subtime = subtime;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public Long getApproveid() {
        return approveid;
    }

    public void setApproveid(Long approveid) {
        this.approveid = approveid;
    }

    public String getOperationtype() {
        return operationtype;
    }

    public void setOperationtype(String operationtype) {
        this.operationtype = operationtype;
    }

    public String getOperationresult() {
        return operationresult;
    }

    public void setOperationresult(String operationresult) {
        this.operationresult = operationresult;
    }
}
