package com.maywide.biz.inter.entity;

import com.maywide.core.entity.PersistableEntity;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

/**
 * Created by lisongkang on 2019/4/25 0001.
 */
@Entity
@Table(name = "BIZ_APPLICATION_DISTRIBUTION")
public class BizApplicationDistribution extends PersistableEntity<Long> implements Persistable<Long> {
    private Long id; // 序列号
    private String proid;


    private String constructorid;
    private String constructorname;
    private String royalty;
    private String operatetime;

    @Override
    @Transient
    public String getDisplay() {
        return null;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADID", unique = true, length = 16)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getConstructorid() {
        return constructorid;
    }

    public void setConstructorid(String constructorid) {
        this.constructorid = constructorid;
    }
    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }
    public String getConstructorname() {
        return constructorname;
    }

    public void setConstructorname(String constructorname) {
        this.constructorname = constructorname;
    }

    public String getRoyalty() {
        return royalty;
    }

    public void setRoyalty(String royalty) {
        this.royalty = royalty;
    }

    public String getOperatetime() {
        return operatetime;
    }

    public void setOperatetime(String operatetime) {
        this.operatetime = operatetime;
    }
}
