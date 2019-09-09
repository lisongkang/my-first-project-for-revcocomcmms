package com.maywide.biz.salary.entity;

import com.maywide.core.entity.PersistableEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
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
//@Table(name = "salary_achievement_bonus_kpi_text_config")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@ApiModel(value="AchievementBonusKpiTextConfig对象", description="")
public class AchievementBonusKpiTextConfig extends PersistableEntity<Long> implements Persistable<Long> {

    private static final long serialVersionUID = 1L;

    //@TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "操作员city 为 * 为全部city可见")
    private String city;

    //@ApiModelProperty(value = "操作员areaid 为 * 为city下全部areaid可见")
    private String areaid;

    //@ApiModelProperty(value = "内容")
    private String context;

    //@ApiModelProperty(value = "排序")
    private Integer rank;

    //@ApiModelProperty(value = "是否在导出模板中显示")
    private String showInTemplate;

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
     * 是否在导出模板中显示
     */
    @Column(name = "show_in_template")
    public String getShowInTemplate() {
        return showInTemplate;
    }

    /**
     * 是否在导出模板中显示
     */
    public void setShowInTemplate(String showInTemplate) {
        this.showInTemplate = showInTemplate;
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
        return "AchievementBonusKpiTextConfig{" +
        "id=" + id +
        ", city=" + city +
        ", areaid=" + areaid +
        ", context=" + context +
        ", rank=" + rank +
        ", showInTemplate=" + showInTemplate +
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
