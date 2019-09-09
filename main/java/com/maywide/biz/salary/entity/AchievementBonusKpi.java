package com.maywide.biz.salary.entity;

import com.maywide.core.entity.PersistableEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Date;
import org.hibernate.annotations.Cache;
/**
 * <p>
 * 
 * </p>
 *
 * @author Agile Code Generator
 * @since 2019-06-15
 */
//@Entity
//@Table(name = "salary_achievement_bonus_kpi")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@ApiModel(value="AchievementBonusKpi对象", description="")
public class AchievementBonusKpi extends PersistableEntity<Long> implements Persistable<Long> {

    private static final long serialVersionUID = 1L;

    //@TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "操作员id")
    private Long operid;

    //@ApiModelProperty(value = "月份")
    private String dateMonth;

    //@ApiModelProperty(value = "文字描述id")
    private Long textConfigId;

    //@ApiModelProperty(value = "得分")
    private Integer score;

    //@ApiModelProperty(value = "描述")
    private String description;

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
     * 文字描述id
     */
    @Column(name = "text_config_id")
    public Long getTextConfigId() {
        return textConfigId;
    }

    /**
     * 文字描述id
     */
    public void setTextConfigId(Long textConfigId) {
        this.textConfigId = textConfigId;
    }

    /**
     * 得分
     */
    @Column(name = "score")
    public Integer getScore() {
        return score;
    }

    /**
     * 得分
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * 描述
     */
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    /**
     * 描述
     */
    public void setDescription(String description) {
        this.description = description;
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
        return "AchievementBonusKpi{" +
        "id=" + id +
        ", operid=" + operid +
        ", dateMonth=" + dateMonth +
        ", textConfigId=" + textConfigId +
        ", score=" + score +
        ", description=" + description +
        ", remark=" + remark +
        ", createBy=" + createBy +
        ", createAt=" + createAt +
        ", updateBy=" + updateBy +
        ", updateAt=" + updateAt +
        "}";
    }

    @Override
    public String getDisplay() {
        return null;
    }
}
