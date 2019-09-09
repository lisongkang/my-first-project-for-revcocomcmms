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
@Table(name = "salary_others_kpi")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@ApiModel(value="OthersKpi对象", description="")
public class OthersKpi extends PersistableEntity<Long> implements Persistable<Long> {

    private static final long serialVersionUID = 1L;

    //@TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String city;

    private String areaid;

    private String grid;

    private String status;
    //@ApiModelProperty(value = "操作员id")
    private Long operid;

    //@ApiModelProperty(value = "月份")
    private String dateMonth;

    //@ApiModelProperty(value = "文字描述id")
    private Long textConfigId;

    //@ApiModelProperty(value = "得分")
    private Double score;

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
    public Double getScore() {
        return score;
    }

    /**
     * 得分
     */
    public void setScore(Double score) {
        this.score = score;
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

    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "OthersKpi{" +
                "id=" + id +
                ", operid=" + operid +
                ", dateMonth=" + dateMonth +
                ", textConfigId=" + textConfigId +
                ", score=" + score +
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



    //---查询条件用到,不生成数据库字段-----
    private Double scoreCount;

    private String textConfigType;

    private String textConfigName;

    private String showInTemplate;

    private String textConfigIds;

    private String scores;

    private String remarks;

    private Long auditUser;

    private String grids;

    private String operids;

    private OthersKpiExcelTemp othersKpiExcelTemp;


    @Transient
    public Double getScoreCount() {
        return scoreCount;
    }

    public void setScoreCount(Double scoreCount) {
        this.scoreCount = scoreCount;
    }

    @Transient
    public String getTextConfigType() {
        return textConfigType;
    }

    public void setTextConfigType(String textConfigType) {
        this.textConfigType = textConfigType;
    }
    @Transient
    public String getTextConfigIds() {
        return textConfigIds;
    }

    public void setTextConfigIds(String textConfigIds) {
        this.textConfigIds = textConfigIds;
    }
    @Transient
    public Long getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(Long auditUser) {
        this.auditUser = auditUser;
    }
    @Transient
    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }
    @Transient
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    @Transient
    public String getTextConfigName() {
        return textConfigName;
    }

    public void setTextConfigName(String textConfigName) {
        this.textConfigName = textConfigName;
    }
    @Transient
    public String getShowInTemplate() {
        return showInTemplate;
    }

    public void setShowInTemplate(String showInTemplate) {
        this.showInTemplate = showInTemplate;
    }
    @Transient
    public String getGrids() {
        return grids;
    }

    public void setGrids(String grids) {
        this.grids = grids;
    }
    @Transient
    public String getOperids() {
        return operids;
    }

    public void setOperids(String operids) {
        this.operids = operids;
    }
    @Transient
    public OthersKpiExcelTemp getOthersKpiExcelTemp() {
        return othersKpiExcelTemp;
    }

    public void setOthersKpiExcelTemp(OthersKpiExcelTemp othersKpiExcelTemp) {
        this.othersKpiExcelTemp = othersKpiExcelTemp;
    }
}
