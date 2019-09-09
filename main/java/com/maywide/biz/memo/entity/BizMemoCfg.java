package com.maywide.biz.memo.entity;

import com.maywide.core.annotation.MetaData;
import com.maywide.core.entity.PersistableEntity;
import com.maywide.core.entity.annotation.EntityAutoCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wzy
 */
@Entity
@Table(name = "biz_memo_cfg")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BizMemoCfg extends PersistableEntity<Long> implements Persistable<Long> {

    @MetaData(value = "主键")
    @EntityAutoCode
    private Long id;

    @MetaData(value = "城市")
    @EntityAutoCode
    private String city;

    @MetaData(value = "业务区，多个用逗号隔开")
    @EntityAutoCode
    private String areas;

    @MetaData(value = "业务操作mcode的值")
    @EntityAutoCode
    private String opcodes;

    @MetaData(value = "备注内容")
    @EntityAutoCode
    private String memotxt;

    @MetaData(value = "优先级(数字越大优先级越高)")
    @EntityAutoCode
    private Integer pri;

    @MetaData(value = "录入时间")
    @EntityAutoCode
    private Date intime;

    @MetaData(value = "操作员id")
    @EntityAutoCode
    private Long operator;

    private String  citynames;//城市名称
    private String  areanames;//业务区名称
    private String  opername;//操作员名称
    private String  opnames;//操作名称

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, length = 20)
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

    public String getCity() {
        return city;
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
    public String getOpnames() {
        return opnames;
    }

    public void setOpnames(String opnames) {
        this.opnames = opnames;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAreas() {
        return areas;
    }

    public void setAreas(String areas) {
        this.areas = areas;
    }

    public String getOpcodes() {
        return opcodes;
    }

    public void setOpcodes(String opcodes) {
        this.opcodes = opcodes;
    }

    public String getMemotxt() {
        return memotxt;
    }

    public void setMemotxt(String memotxt) {
        this.memotxt = memotxt;
    }

    public Integer getPri() {
        return pri;
    }

    public void setPri(Integer pri) {
        this.pri = pri;
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
}
