package com.maywide.biz.az.quota.entity;

import com.maywide.core.entity.PersistableEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

/**
 * Created by lisongkang on 2019/4/8 0001.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "construction_settlement_unit_price")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConstSettlement extends PersistableEntity<Long> implements Persistable<Long> {
    private Long id;
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private String newnumber;
    private String oldnumber;
    private String constname;
    private String constdetail;
    private String company;
    private Double oneprice;
    private Double twoprice;
    private Double threeprice;
    private Double fourprice;
    private String constcontent;
    private String fileids;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CONSTID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="NEWNUMBER")
    public String getNewnumber() {
        return newnumber;
    }

    public void setNewnumber(String newnumber) {
        this.newnumber = newnumber;
    }
    @Column(name="OLDNUMBER")
    public String getOldnumber() {
        return oldnumber;
    }

    public void setOldnumber(String oldnumber) {
        this.oldnumber = oldnumber;
    }

    @Column(name="CONSTNAME")
    public String getConstname() {
        return constname;
    }

    public void setConstname(String constname) {
        this.constname = constname;
    }
    @Column(name="CONSTDETAIL")
    public String getConstdetail() {
        return constdetail;
    }

    public void setConstdetail(String constdetail) {
        this.constdetail = constdetail;
    }

    @Column(name="COMPANY")
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Column(name="ONEPRICE")
    public Double getOneprice() {
        return oneprice;
    }

    public void setOneprice(Double oneprice) {
        this.oneprice = oneprice;
    }
    @Column(name="TWOPRICE")
    public Double getTwoprice() {
        return twoprice;
    }

    public void setTwoprice(Double twoprice) {
        this.twoprice = twoprice;
    }
    @Column(name="THREEPRICE")
    public Double getThreeprice() {
        return threeprice;
    }

    public void setThreeprice(Double threeprice) {
        this.threeprice = threeprice;
    }
    @Column(name="FOURPRICE")
    public Double getFourprice() {
        return fourprice;
    }

    public void setFourprice(Double fourprice) {
        this.fourprice = fourprice;
    }
    @Column(name="CONSTCONTENT")
    public String getConstcontent() {
        return constcontent;
    }

    public void setConstcontent(String constcontent) {
        this.constcontent = constcontent;
    }
    @Column(name="FILEIDS")
    public String getFileids() {
        return fileids;
    }

    public void setFileids(String fileids) {
        this.fileids = fileids;
    }

    @Override
    public String toString() {
        return "ConstSettlement{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", newnumber='" + newnumber + '\'' +
                ", oldnumber='" + oldnumber + '\'' +
                ", constname='" + constname + '\'' +
                ", constdetail='" + constdetail + '\'' +
                ", company='" + company + '\'' +
                ", oneprice=" + oneprice +
                ", twoprice=" + twoprice +
                ", threeprice=" + threeprice +
                ", fourprice=" + fourprice +
                ", constcontent='" + constcontent + '\'' +
                ", fileids='" + fileids + '\'' +
                '}';
    }

    @Override
    @Transient
    public String getDisplay() {
        // TODO Auto-generated method stub
        return null;
    }

}
