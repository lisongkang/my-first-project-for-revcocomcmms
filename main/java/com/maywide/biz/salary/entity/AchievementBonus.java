package com.maywide.biz.salary.entity;

import com.maywide.core.entity.PersistableEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
//import java.time.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Agile Code Generator
 * @since 2019-06-15
 */
@Entity
@Table(name = "salary_achievement_bonus")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AchievementBonus  extends PersistableEntity<Long> implements Persistable<Long> {

    private static final long serialVersionUID = 1L;

//    //@TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String city;

    private String areaid;

    private String grid;
    ////@ApiModelProperty(value = "操作员id")
    private Long operid;

    ////@ApiModelProperty(value = "月份")
    private String dateMonth;

    ////@ApiModelProperty(value = "工资金额")
    private BigDecimal amount;

    ////@ApiModelProperty(value = "备注")
    private String remark;

    ////@ApiModelProperty(value = "创建人")
    private String createBy;

    ////@ApiModelProperty(value = "创建时间")
    private Date createAt;

    ////@ApiModelProperty(value = "修改人")
    private String updateBy;

    ////@ApiModelProperty(value = "修改时间")
    private Date updateAt;


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
     * 操作员id
     */
    @Column(name = "operid")
    public Long getOperid() {
        return operid;
    }

    /**
     * 操作员id
     */
    public void setOperid(Long operid) {
        this.operid = operid;
    }

    /**
     * 月份
     */
    @Column(name = "date_month")
    public String getDateMonth() {
        return dateMonth;
    }

    /**
     * 月份
     */
    public void setDateMonth(String dateMonth) {
        this.dateMonth = dateMonth;
    }

    /**
     * 工资金额
     */
    @Column(name = "amount")
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 工资金额
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
    @Column(name = "grid")
    public String getGrid() {
        return grid;
    }

    public void setGrid(String grid) {
        this.grid = grid;
    }

    @Override
    public String toString() {
        return "AchievementBonus{" +
        "id=" + id +
        ", operid=" + operid +
        ", dateMonth=" + dateMonth +
        ", amount=" + amount +
        ", remark=" + remark +
        ", createBy=" + createBy +
        ", createAt=" + createAt +
        ", updateBy=" + updateBy +
        ", updateAt=" + updateAt +
        "}";
    }

    @Override
    @Transient
    public String getDisplay() {
        return areaid;
    }
}
