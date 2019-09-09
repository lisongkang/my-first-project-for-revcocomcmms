package com.maywide.biz.inter.entity;

import com.maywide.core.entity.PersistableEntity;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

/**
 * Created by lisongkang on 2019/4/25 0001.
 */
@Entity
@Table(name = "BIZ_APPLICATION_MATERIEL")
public class BizApplicationMateriel extends PersistableEntity<Long> implements Persistable<Long> {
    private Long id; // 序列号
    private String proid;
    private String city;
    private String corpcode;
    private String corpname;
    private String invcode;
    private String invname;
    private String nums;
    private String nprice;
    private String cost;
    private String measname;
    private String operatetime;

    @Override
    @Transient
    public String getDisplay() {
        return null;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AMID", unique = true, length = 16)
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
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCorpcode() {
        return corpcode;
    }

    public void setCorpcode(String corpcode) {
        this.corpcode = corpcode;
    }

    public String getCorpname() {
        return corpname;
    }

    public void setCorpname(String corpname) {
        this.corpname = corpname;
    }

    public String getInvcode() {
        return invcode;
    }

    public void setInvcode(String invcode) {
        this.invcode = invcode;
    }

    public String getInvname() {
        return invname;
    }

    public void setInvname(String invname) {
        this.invname = invname;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public String getNprice() {
        return nprice;
    }

    public void setNprice(String nprice) {
        this.nprice = nprice;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getOperatetime() {
        return operatetime;
    }

    public void setOperatetime(String operatetime) {
        this.operatetime = operatetime;
    }

    public String getMeasname() {
        return measname;
    }

    public void setMeasname(String measname) {
        this.measname = measname;
    }
}
