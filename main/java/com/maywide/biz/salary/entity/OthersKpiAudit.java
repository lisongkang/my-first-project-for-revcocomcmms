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

@Entity
@Table(name = "salary_others_kpi_audit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@ApiModel(value="OthersKpi对象", description="")
public class OthersKpiAudit extends PersistableEntity<Long> implements Persistable<Long> {

    private static final long serialVersionUID = 1L;

    //@TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long othersKpiId;

    private Long commitUser;  //提交人

    private Long auditUser; //审核人

    private String dateMonth; //月份

    private String grid;

    private Long operid;  //网格人员

    private String status;

    private String remark;

    //@ApiModelProperty(value = "创建人")
    private String createBy;

    //@ApiModelProperty(value = "创建时间")
    private Date createAt;

    //@ApiModelProperty(value = "修改人")
    private String updateBy;

    //@ApiModelProperty(value = "修改时间")
    private Date updateAt;

    private String city;
    private String areaid;
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

    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "others_kpi_id")
    public Long getOthersKpiId() {
        return othersKpiId;
    }

    public void setOthersKpiId(Long othersKpiId) {
        this.othersKpiId = othersKpiId;
    }
    @Column(name = "commit_user")
    public Long getCommitUser() {
        return commitUser;
    }

    public void setCommitUser(Long commitUser) {
        this.commitUser = commitUser;
    }

    @Column(name = "audit_user")
    public Long getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(Long auditUser) {
        this.auditUser = auditUser;
    }
    @Column(name = "grid")
    public String getGrid() {
        return grid;
    }

    public void setGrid(String grid) {
        this.grid = grid;
    }
    @Transient
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    @Transient
    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }


    @Override
    public String toString() {
        return "OthersKpi{" +
        "id=" + id +
        ", operid=" + operid +
        ", dateMonth=" + dateMonth +
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
        return null;
    }
}
