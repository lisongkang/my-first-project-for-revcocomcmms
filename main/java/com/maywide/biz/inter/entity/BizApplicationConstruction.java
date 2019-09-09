package com.maywide.biz.inter.entity;

import com.maywide.core.entity.PersistableEntity;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by lisongkang on 2019/4/25 0001.
 */
@Entity
@Table(name = "BIZ_APPLICATION_CONSTRUCTION")
public class BizApplicationConstruction extends PersistableEntity<Long> implements Persistable<Long> {
    private Long id; // 序列号
    private String proid;

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    private String constid;
    private String constname;
    private String nums;
    private String cost;
    private String company;
    private String constprice;
    private String operatetime;


    @Override
    @Transient
    public String getDisplay() {
        return null;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACID", unique = true, length = 16)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getConstid() {
        return constid;
    }

    public void setConstid(String constid) {
        this.constid = constid;
    }

    public String getConstname() {
        return constname;
    }

    public void setConstname(String constname) {
        this.constname = constname;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getConstprice() {
        return constprice;
    }

    public void setConstprice(String constprice) {
        this.constprice = constprice;
    }
}
