package com.maywide.biz.salary.entity;

import com.maywide.core.entity.PersistableEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@Table(name = "salary_others_kpi_excel_temp")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OthersKpiExcelTemp extends PersistableEntity<Long> implements Persistable<Long> {

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
    private String score;

    //@ApiModelProperty(value = "备注")
    private String remark;

   private String gridName;//网格名称

   private String account;//网格人员账号

   private String configType;//积分类型

   private String configContext;//积分项目

   private String createBy;  //创建人

   private String checkFlag; //是否检查通过    0未通过  1通过

   private String errorMessage; //错误消息
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

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
    public String getScore() {
        return score;
    }

    /**
     * 得分
     */
    public void setScore(String score) {
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
    @Column(name = "gridname")
    public String getGridName() {
        return gridName;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }
    @Column(name = "account")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    @Column(name = "config_type")
    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }
    @Column(name = "config_context")
    public String getConfigContext() {
        return configContext;
    }

    public void setConfigContext(String configContext) {
        this.configContext = configContext;
    }


    @Column(name = "create_by")
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Column(name = "check_flag")
    public String getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag;
    }

    @Column(name = "error_message")
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    @Transient
    public String getDisplay() {
        return null;
    }


}
