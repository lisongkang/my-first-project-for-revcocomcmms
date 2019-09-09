package com.maywide.biz.salary.entity;


import com.maywide.core.entity.PersistableEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.List;

/**
 * Created by lisongkang on 2019/8/22 0001.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "salary_base_wage_honus")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BaseWageHonus extends PersistableEntity<Long> implements Persistable<Long> {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String city;

    private String areaid;

    private String areaidname;

    private String grid;

    //@ApiModelProperty(value = "操作员id")
    private Long operid;

    //@ApiModelProperty(value = "工资金额")
    private Double amount;

    private Double honus;

    private String loginname;

    private String name;

    //@ApiModelProperty(value = "备注")
    private String remark;

    private String sdateMonth;

    private String edateMonth;


    private String dateMonth;

    private List<String> areaids;

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    @Column(name = "areaid")
    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }
    @Column(name = "areaidname")
    public String getAreaidname() {
        return areaidname;
    }

    public void setAreaidname(String areaidname) {
        this.areaidname = areaidname;
    }
    @Column(name = "grid")
    public String getGrid() {
        return grid;
    }

    public void setGrid(String grid) {
        this.grid = grid;
    }
    @Column(name = "operid")
    public Long getOperid() {
        return operid;
    }

    public void setOperid(Long operid) {
        this.operid = operid;
    }
    @Column(name = "amount")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
    @Column(name = "honus")
    public Double getHonus() {
        return honus;
    }

    public void setHonus(Double honus) {
        this.honus = honus;
    }
    @Column(name = "loginname")
    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Column(name = "sdate_month")
    public String getSdateMonth() {
        return sdateMonth;
    }

    public void setSdateMonth(String sdateMonth) {
        this.sdateMonth = sdateMonth;
    }
    @Column(name = "edate_month")
    public String getEdateMonth() {
        return edateMonth;
    }

    public void setEdateMonth(String edateMonth) {
        this.edateMonth = edateMonth;
    }

    @Transient
    public String getDateMonth() {
        return dateMonth;
    }

    public void setDateMonth(String dateMonth) {
        this.dateMonth = dateMonth;
    }
    @Transient
    public List<String> getAreaids() {
        return areaids;
    }

    public void setAreaids(List<String> areaids) {
        this.areaids = areaids;
    }

    @Override
    public String toString() {
        return "BaseWageHonus{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", areaid='" + areaid + '\'' +
                ", areaidname='" + areaidname + '\'' +
                ", grid='" + grid + '\'' +
                ", operid=" + operid +
                ", amount=" + amount +
                ", honus=" + honus +
                ", loginname='" + loginname + '\'' +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", sdateMonth='" + sdateMonth + '\'' +
                ", edateMonth='" + edateMonth + '\'' +
                '}';
    }

    @Override
    @Transient
    public String getDisplay() {
        // TODO Auto-generated method stub
        return null;
    }
}
