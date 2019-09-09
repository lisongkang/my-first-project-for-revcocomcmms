package com.maywide.biz.az.constructors.entity;

import com.maywide.core.entity.PersistableEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

/**
 * Created by lisongkang on 2019/4/16 0001.
 */
@Entity
@Table(name = "PRV_OPERATOR_IDBANKCARD")
@SuppressWarnings("serial")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PrvOperatorInfo extends PersistableEntity<Long> implements Persistable<Long> {
    private Long id;
    private String operid;
    private String loginname;
    private String name;
    private String idcard;
    private String bankcard;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Column(name="OPERID")
    public String getOperid() {
        return operid;
    }

    public void setOperid(String operid) {
        this.operid = operid;
    }
    @Column(name="LOGINNAME")
    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }
    @Column(name="NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name="IDCARD")
    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }
    @Column(name="BANKCARD")
    public String getBankcard() {
        return bankcard;
    }

    public void setBankcard(String bankcard) {
        this.bankcard = bankcard;
    }

    @Override
    @Transient
    public String getDisplay() {
        // TODO Auto-generated method stub
        return null;
    }
}
