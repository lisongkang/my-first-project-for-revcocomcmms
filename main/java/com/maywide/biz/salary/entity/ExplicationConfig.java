package com.maywide.biz.salary.entity;

import com.maywide.core.entity.PersistableEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Agile Code Generator
 * @since 2019-06-15
 */
@Entity
@Table(name = "salary_explication_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@ApiModel(value="ExplicationConfig对象", description="")
public class ExplicationConfig extends PersistableEntity<Long> implements Persistable<Long> {

    private static final long serialVersionUID = 1L;

    //@TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "类型 决定在界面上显示的位置")
    private String type;

    //@ApiModelProperty(value = "操作员city 为 * 为全部city可见")
    private String city;

    //@ApiModelProperty(value = "操作员areaid 为 * 为city下全部areaid可见")
    private String areaid;

    //@ApiModelProperty(value = "内容")
    private String context;

    //@ApiModelProperty(value = "生效时间")
    private Date stime;

    //@ApiModelProperty(value = "失效时间")
    private Date etime;

    //@ApiModelProperty(value = "备注")
    private String remark;

    //@ApiModelProperty(value = "创建人")
    private String createBy;

    //@ApiModelProperty(value = "创建时间")
    private Date createAt;

    //@ApiModelProperty(value = "修改人")
    private String updateBy;

    //@ApiModelProperty(value = "修改时间")
    private Date updateAt;

    @Override
    @Transient
    public String getDisplay() {
        return areaid;
    }
    /**
     * 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 类型 决定在界面上显示的位置
     */
    @Column(name = "type")
    public String getType() {
        return type;
    }

    /**
     * 类型 决定在界面上显示的位置
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 操作员city 为 * 为全部city可见
     */
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    /**
     * 操作员city 为 * 为全部city可见
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 操作员areaid 为 * 为city下全部areaid可见
     */
    @Column(name = "areaid")
    public String getAreaid() {
        return areaid;
    }

    /**
     * 操作员areaid 为 * 为city下全部areaid可见
     */
    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    /**
     * 内容
     */
    @Column(name = "context")
    public String getContext() {
        return context;
    }

    /**
     * 内容
     */
    public void setContext(String context) {
        this.context = context;
    }

    /**
     * 生效时间
     */
    @Column(name = "stime")
    public Date getStime() {
        return stime;
    }

    /**
     * 生效时间
     */
    public void setStime(Date stime) {
        this.stime = stime;
    }

    /**
     * 失效时间
     */
    @Column(name = "etime")
    public Date getEtime() {
        return etime;
    }

    /**
     * 失效时间
     */
    public void setEtime(Date etime) {
        this.etime = etime;
    }

    /**
     * 备注
     */
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 创建人
     */
    @Column(name = "create_by")
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 创建人
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 创建时间
     */
    @Column(name = "create_at")
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * 创建时间
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * 修改人
     */
    @Column(name = "update_by")
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * 修改人
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 修改时间
     */
    @Column(name = "update_at")
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * 修改时间
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }


    @Override
    public String toString() {
        return "ExplicationConfig{" +
        "id=" + id +
        ", type=" + type +
        ", city=" + city +
        ", areaid=" + areaid +
        ", context=" + context +
        ", stime=" + stime +
        ", etime=" + etime +
        ", remark=" + remark +
        ", createBy=" + createBy +
        ", createAt=" + createAt +
        ", updateBy=" + updateBy +
        ", updateAt=" + updateAt +
        "}";
    }
}
