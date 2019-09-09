package com.maywide.biz.prd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import com.maywide.core.entity.PersistableEntity;

/**
 * <p>
 * 商品对象
 * <p>
 * Create at 2016年6月6日
 * 
 * @author pengjianqiang
 */
@Entity
@Table(name = "PRD_SALES")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Sales extends PersistableEntity<Long> implements Persistable<Long> {
    private Long           id;
    private Long           pid;
    private String         salesName;
    private String         memo;
    private String         isBasic;
    private String         orderSrc;
    private String         chargeMode;
    private String         firstFee;
    private String         payType;
    private String         isSeg;
    private String         checkType;
    private Double         sums;
    private Long           cycle;
    private String         unit;
    private Long           pri;
    private String         feeCode;
    private String         ifeeCode;
    private String         rfeeCode;
    private String         areaid;
    private Long           ruleid;
    private String         status;
    private java.util.Date stime;
    private java.util.Date etime;
    private Long           createOper;
    private java.util.Date createTime;
    private Long           updateOper;
    private java.util.Date updateTime;
    private Long           partlen;
    private String         salesCode;
    private String         btype;
    private String         salesType;
    private String         salesProp;
    private Long           basicSalesid;
    private String         isJoin;
    private String         salesLevel;
    private String         custMerge;
    private String         city;
    private Long           seriesid;
    private String         printMemo;
    private Long           agreementLen;
    private String         coreName;
    private String         channelSystem;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SALESID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Transient
    public String getDisplay() {
        return salesName;
    }

    @Column(name = "PID")
    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    @Column(name = "SALESNAME")
    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    @Column(name = "MEMO")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "ISBASIC")
    public String getIsBasic() {
        return isBasic;
    }

    public void setIsBasic(String isBasic) {
        this.isBasic = isBasic;
    }

    @Column(name = "ORDERSRC")
    public String getOrderSrc() {
        return orderSrc;
    }

    public void setOrderSrc(String orderSrc) {
        this.orderSrc = orderSrc;
    }

    @Column(name = "CHARGEMODE")
    public String getChargeMode() {
        return chargeMode;
    }

    public void setChargeMode(String chargeMode) {
        this.chargeMode = chargeMode;
    }

    @Column(name = "FIRSTFEE")
    public String getFirstFee() {
        return firstFee;
    }

    public void setFirstFee(String firstFee) {
        this.firstFee = firstFee;
    }

    @Column(name = "PAYTYPE")
    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    @Column(name = "ISSEG")
    public String getIsSeg() {
        return isSeg;
    }

    public void setIsSeg(String isSeg) {
        this.isSeg = isSeg;
    }

    @Column(name = "CHECKTYPE")
    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    @Column(name = "SUMS")
    public Double getSums() {
        return sums;
    }

    public void setSums(Double sums) {
        this.sums = sums;
    }

    @Column(name = "CYCLE")
    public Long getCycle() {
        return cycle;
    }

    public void setCycle(Long cycle) {
        this.cycle = cycle;
    }

    @Column(name = "UNIT")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Column(name = "PRI")
    public Long getPri() {
        return pri;
    }

    public void setPri(Long pri) {
        this.pri = pri;
    }

    @Column(name = "FEECODE")
    public String getFeeCode() {
        return feeCode;
    }

    public void setFeeCode(String feeCode) {
        this.feeCode = feeCode;
    }

    @Column(name = "IFEECODE")
    public String getIfeeCode() {
        return ifeeCode;
    }

    public void setIfeeCode(String ifeeCode) {
        this.ifeeCode = ifeeCode;
    }

    @Column(name = "RFEECODE")
    public String getRfeeCode() {
        return rfeeCode;
    }

    public void setRfeeCode(String rfeeCode) {
        this.rfeeCode = rfeeCode;
    }

    @Column(name = "AREAID")
    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    @Column(name = "RULEID")
    public Long getRuleid() {
        return ruleid;
    }

    public void setRuleid(Long ruleid) {
        this.ruleid = ruleid;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "STIME")
    public java.util.Date getStime() {
        return stime;
    }

    public void setStime(java.util.Date stime) {
        this.stime = stime;
    }

    @Column(name = "ETIME")
    public java.util.Date getEtime() {
        return etime;
    }

    public void setEtime(java.util.Date etime) {
        this.etime = etime;
    }

    @Column(name = "CREATEOPER")
    public Long getCreateOper() {
        return createOper;
    }

    public void setCreateOper(Long createOper) {
        this.createOper = createOper;
    }

    @Column(name = "CREATETIME")
    public java.util.Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "UPDATEOPER")
    public Long getUpdateOper() {
        return updateOper;
    }

    public void setUpdateOper(Long updateOper) {
        this.updateOper = updateOper;
    }

    @Column(name = "UPDATETIME")
    public java.util.Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "PARTLEN")
    public Long getPartlen() {
        return partlen;
    }

    public void setPartlen(Long partlen) {
        this.partlen = partlen;
    }

    @Column(name = "SALESCODE")
    public String getSalesCode() {
        return salesCode;
    }

    public void setSalesCode(String salesCode) {
        this.salesCode = salesCode;
    }

    @Column(name = "BTYPE")
    public String getBtype() {
        return btype;
    }

    public void setBtype(String btype) {
        this.btype = btype;
    }

    @Column(name = "SALESTYPE")
    public String getSalesType() {
        return salesType;
    }

    public void setSalesType(String salesType) {
        this.salesType = salesType;
    }

    @Column(name = "SALESPROP")
    public String getSalesProp() {
        return salesProp;
    }

    public void setSalesProp(String salesProp) {
        this.salesProp = salesProp;
    }

    @Column(name = "BASICSALESID")
    public Long getBasicSalesid() {
        return basicSalesid;
    }

    public void setBasicSalesid(Long basicSalesid) {
        this.basicSalesid = basicSalesid;
    }

    @Column(name = "ISJOIN")
    public String getIsJoin() {
        return isJoin;
    }

    public void setIsJoin(String isJoin) {
        this.isJoin = isJoin;
    }

    @Column(name = "SALESLEVEL")
    public String getSalesLevel() {
        return salesLevel;
    }

    public void setSalesLevel(String salesLevel) {
        this.salesLevel = salesLevel;
    }

    @Column(name = "CUSTMERGE")
    public String getCustMerge() {
        return custMerge;
    }

    public void setCustMerge(String custMerge) {
        this.custMerge = custMerge;
    }

    @Column(name = "CITY")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "SERIESID")
    public Long getSeriesid() {
        return seriesid;
    }

    public void setSeriesid(Long seriesid) {
        this.seriesid = seriesid;
    }

    @Column(name = "PRINTMEMO")
    public String getPrintMemo() {
        return printMemo;
    }

    public void setPrintMemo(String printMemo) {
        this.printMemo = printMemo;
    }

    @Column(name = "AGREEMENTLEN")
    public Long getAgreementLen() {
        return agreementLen;
    }

    public void setAgreementLen(Long agreementLen) {
        this.agreementLen = agreementLen;
    }

    @Column(name = "CORENAME")
    public String getCoreName() {
        return coreName;
    }

    public void setCoreName(String coreName) {
        this.coreName = coreName;
    }

    @Column(name = "CHANNELSYSTEM")
    public String getChannelSystem() {
        return channelSystem;
    }

    public void setChannelSystem(String channelSystem) {
        this.channelSystem = channelSystem;
    }

}