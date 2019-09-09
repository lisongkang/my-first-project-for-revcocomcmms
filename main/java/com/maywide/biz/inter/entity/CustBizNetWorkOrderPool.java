package com.maywide.biz.inter.entity;

import com.maywide.core.entity.PersistableEntity;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lisongkang on 2019/7/10 0001.
 */
@Table(name="biz_networkbus_custorder_pool")
@Entity
public class CustBizNetWorkOrderPool extends PersistableEntity<Long> implements Persistable<Long>{
    private Long id;
    private String custid;
    private String custname;
    private String preserialno;
    private String linkAddr;
    private String createoper;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createtime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date pretime;

    private String defName;
    private String opcode;
    private String defdesc;
    private String remark;
    private String applydept;
    private String payway;
    private String result;
    private String datadesc;
    private String whgridcode;
    private String applyoperid;
    private Long operid;
    @Temporal(TemporalType.TIMESTAMP)
    private Date optime;
    private String areaid;

    private String status;

    private String phone;

    private String salesfees;
    private String salesorder;
    private String preprocessfees;
    private String preprocessorder;

    public String getPreprocessorder() {
        return preprocessorder;
    }

    public void setPreprocessorder(String preprocessorder) {
        this.preprocessorder = preprocessorder;
    }

    public String getPreprocessfees() {
        return preprocessfees;
    }

    public void setPreprocessfees(String preprocessfees) {
        this.preprocessfees = preprocessfees;
    }

    public String getSalesorder() {
        return salesorder;
    }

    public void setSalesorder(String salesorder) {
        this.salesorder = salesorder;
    }

    public String getSalesfees() {
        return salesfees;
    }

    public void setSalesfees(String salesfees) {
        this.salesfees = salesfees;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="biznetorder_id")
    @Override
    public Long getId() {
        return id;
    }

    @Transient
    @Override
    public String getDisplay() {
        return null;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getPreserialno() {
        return preserialno;
    }

    public void setPreserialno(String preserialno) {
        this.preserialno = preserialno;
    }

    public String getLinkAddr() {
        return linkAddr;
    }

    public void setLinkAddr(String linkAddr) {
        this.linkAddr = linkAddr;
    }

    public String getCreateoper() {
        return createoper;
    }

    public void setCreateoper(String createoper) {
        this.createoper = createoper;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getPretime() {
        return pretime;
    }

    public void setPretime(Date pretime) {
        this.pretime = pretime;
    }

    public String getDefName() {
        return defName;
    }

    public void setDefName(String defName) {
        this.defName = defName;
    }

    public String getOpcode() {
        return opcode;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }

    public String getDefdesc() {
        return defdesc;
    }

    public void setDefdesc(String defdesc) {
        this.defdesc = defdesc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getApplydept() {
        return applydept;
    }

    public void setApplydept(String applydept) {
        this.applydept = applydept;
    }

    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDatadesc() {
        return datadesc;
    }

    public void setDatadesc(String datadesc) {
        this.datadesc = datadesc;
    }

    public String getWhgridcode() {
        return whgridcode;
    }

    public void setWhgridcode(String whgridcode) {
        this.whgridcode = whgridcode;
    }

    public String getApplyoperid() {
        return applyoperid;
    }

    public void setApplyoperid(String applyoperid) {
        this.applyoperid = applyoperid;
    }

    public Long getOperid() {
        return operid;
    }

    public void setOperid(Long operid) {
        this.operid = operid;
    }

    public Date getOptime() {
        return optime;
    }

    public void setOptime(Date optime) {
        this.optime = optime;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }
}
