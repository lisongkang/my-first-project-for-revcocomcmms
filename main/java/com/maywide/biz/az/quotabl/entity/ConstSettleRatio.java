package com.maywide.biz.az.quotabl.entity;

import com.maywide.core.entity.PersistableEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

/**
 * Created by lisongkang on 2019/4/10 0001.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "biz_city_divde")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConstSettleRatio extends PersistableEntity<Long> implements Persistable<Long> {
    private Long id;
    private String city ;
    private String citydivde;
    private String quotaratio;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="NUMID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Column(name="CITY")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    @Column(name="CITYDIVDE")
    public String getCitydivde() {
        return citydivde;
    }

    public void setCitydivde(String citydivde) {
        this.citydivde = citydivde;
    }
    @Column(name="QUOTARATIO")
    public String getQuotaratio() {
        return quotaratio;
    }

    public void setQuotaratio(String quotaratio) {
        this.quotaratio = quotaratio;
    }

    @Override
    @Transient
    public String getDisplay() {
        // TODO Auto-generated method stub
        return null;
    }
}
