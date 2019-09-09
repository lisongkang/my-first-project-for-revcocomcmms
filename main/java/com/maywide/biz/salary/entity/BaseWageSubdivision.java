package com.maywide.biz.salary.entity;

import com.maywide.core.entity.PersistableEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Agile Code Generator
 * @since 2019-06-15
 */
//@Entity
//@Table(name = "salary_base_wage_subdivision")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@ApiModel(value="BaseWageSubdivision对象", description="")
public class BaseWageSubdivision extends PersistableEntity<Long> implements Persistable<Long> {

    private static final long serialVersionUID = 1L;

    //@TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "基础表数据")
    private Long operid;

    //@ApiModelProperty(value = "工资月份")
    private String dateMonth;

    //@ApiModelProperty(value = "类型，建议直接记录文字")
    private String subdivisionType;

    //@ApiModelProperty(value = "排序")
    private Integer rank;

    //@ApiModelProperty(value = "金额")
    private BigDecimal amount;

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
    public String getDisplay() {
        return null;
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
     * 基础表数据
     */
    @Column(name = "operid")
    public Long getOperid() {
        return operid;
    }

    /**
     * 基础表数据
     */
    public void setOperid(Long operid) {
        this.operid = operid;
    }

    /**
     * 工资月份
     */
    @Column(name = "date_month")
    public String getDateMonth() {
        return dateMonth;
    }

    /**
     * 工资月份
     */
    public void setDateMonth(String dateMonth) {
        this.dateMonth = dateMonth;
    }

    /**
     * 类型，建议直接记录文字
     */
    @Column(name = "subdivision_type")
    public String getSubdivisionType() {
        return subdivisionType;
    }

    /**
     * 类型，建议直接记录文字
     */
    public void setSubdivisionType(String subdivisionType) {
        this.subdivisionType = subdivisionType;
    }

    /**
     * 排序
     */
    @Column(name = "rank")
    public Integer getRank() {
        return rank;
    }

    /**
     * 排序
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * 金额
     */
    @Column(name = "amount")
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
        return "BaseWageSubdivision{" +
        "id=" + id +
        ", operid=" + operid +
        ", dateMonth=" + dateMonth +
        ", subdivisionType=" + subdivisionType +
        ", rank=" + rank +
        ", amount=" + amount +
        ", remark=" + remark +
        ", createBy=" + createBy +
        ", createAt=" + createAt +
        ", updateBy=" + updateBy +
        ", updateAt=" + updateAt +
        "}";
    }


}
