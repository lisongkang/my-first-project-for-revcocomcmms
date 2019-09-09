package com.maywide.biz.survey.entity;

import java.util.Date;

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

import com.maywide.core.annotation.MetaData;
import com.maywide.core.entity.PersistableEntity;
import com.maywide.core.entity.annotation.EntityAutoCode;

@Entity
@Table(name = "biz_survey_list")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BizSurveyList extends PersistableEntity<Long> implements Persistable<Long> {

    @MetaData(value = "编号")
    @EntityAutoCode
    private Long    id;

    @MetaData(value = "问卷名称")
    @EntityAutoCode
    private String  sname;

    @MetaData(value = "录入时间")
    @EntityAutoCode
    private Date    intime;

    @MetaData(value = "操作员ID")
    @EntityAutoCode
    private Long    operator;

    @MetaData(value = "实名制")
    @EntityAutoCode
    private String  isreal;

    @MetaData(value = "状态")
    @EntityAutoCode
    private Integer status;

    @MetaData(value = "备注")
    @EntityAutoCode
    private String  memo;

    @MetaData(value = "地市")
    @EntityAutoCode
    private String  city;

    @MetaData(value = "业务区")
    @EntityAutoCode
    private String  areaid;

    private String  citynames;
    private String  areanames;
    private String  opername;

    private String  areaids;  // 修改表单时使用
    private String  qids;     // 关联题目时使用

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid", unique = true, length = 16)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    @Transient
    public String getDisplay() {
        return "";
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public Date getIntime() {
        return intime;
    }

    public void setIntime(Date intime) {
        this.intime = intime;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public String getIsreal() {
        return isreal;
    }

    public void setIsreal(String isreal) {
        this.isreal = isreal;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Transient
    public String getCitynames() {
        return citynames;
    }

    public void setCitynames(String citynames) {
        this.citynames = citynames;
    }

    @Transient
    public String getAreanames() {
        return areanames;
    }

    public void setAreanames(String areanames) {
        this.areanames = areanames;
    }

    @Transient
    public String getOpername() {
        return opername;
    }

    public void setOpername(String opername) {
        this.opername = opername;
    }

    @Transient
    public String getAreaids() {
        return areaids;
    }

    public void setAreaids(String areaids) {
        this.areaids = areaids;
    }

    @Transient
    public String getQids() {
        return qids;
    }

    public void setQids(String qids) {
        this.qids = qids;
    }
}
