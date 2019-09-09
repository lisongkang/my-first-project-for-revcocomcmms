package com.maywide.biz.salary.entity;

import com.maywide.core.entity.PersistableEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
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
//@Table(name = "salary_base_wage")
//@ApiModel(value="BaseWage对象", description="")
@Entity
@Table(name = "salary_base_wage")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BaseWage  extends PersistableEntity<Long> implements Persistable<Long> {

    private static final long serialVersionUID = 1L;

    //@TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String city;

    private String areaid;

    private String grid;

    //@ApiModelProperty(value = "操作员id")
    private Long operid;

    //@ApiModelProperty(value = "工资金额")
    private BigDecimal amount;

    private Date stime;

    private Date etime;
    //岗位
    private String position;
    //职级
    private String positionLevel;
    //档次
    private String level;
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

    private BigDecimal achievementAmount;
    private String dateMonth;
    private String type;
    private Long achievementId;
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

    @Column(name = "stime")
    public Date getStime() {
        return stime;
    }

    public void setStime(Date stime) {
        this.stime = stime;
    }
    @Column(name = "etime")
    public Date getEtime() {
        return etime;
    }

    public void setEtime(Date etime) {
        this.etime = etime;
    }
    @Column(name = "position")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    @Column(name = "position_level")
    public String getPositionLevel() {
        return positionLevel;
    }

    public void setPositionLevel(String positionLevel) {
        this.positionLevel = positionLevel;
    }

    @Column(name = "level")
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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

    @Transient
    public BigDecimal getAchievementAmount() {
        return achievementAmount;
    }

    public void setAchievementAmount(BigDecimal achievementAmount) {
        this.achievementAmount = achievementAmount;
    }
    @Transient
    public String getDateMonth() {
        return dateMonth;
    }

    public void setDateMonth(String dateMonth) {
        this.dateMonth = dateMonth;
    }
    @Transient
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @Transient
    public Long getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(Long achievementId) {
        this.achievementId = achievementId;
    }

    @Override
    public String toString() {
        return "BaseWage{" +
        "id=" + id +
        ", operid=" + operid +
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
