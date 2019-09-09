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
@Table(name = "salary_others_exchange_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@ApiModel(value="OthersKpiTextConfig对象", description="")
public class OthersExchangeConfig extends PersistableEntity<Long> implements Persistable<Long> {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String city;

    private String areaid;

    private String grid;

//    private String dateMonth;

    private String type; // 0基础线 1标准线 2挑战线

    private String sdateMonth;   //开始年月
    private String edateMonth;  //结束年月
    private Double scontrol;  //起始控制线百分比
    private Double econtrol;  //截至控制线百分比
    private String scontrolStatus; //起始开闭  0开  1闭  2不判断
    private String econtrolStatus; //截至开闭  0开  1闭  2不判断
    private Double minCentsPrice; //最低积分单价百分比
    private Double maxCentsPrice; //最高积分单价百分比
    private String formulaType;  //计算公式类型  0按排名线性计算

    private Integer userNum;  //弃用

    private Double coefficient; //弃用

    private String status;  // 0启用  1停用
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

    @Column(name = "grid")
    public String getGrid() {
        return grid;
    }

    public void setGrid(String grid) {
        this.grid = grid;
    }

    @Column(name = "user_num")
    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    @Column(name = "coefficient")
    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "sdate_month")
    public String getSdateMonth() {
        return sdateMonth;
    }

    public void setSdateMonth(String sdateMonth) {
        this.sdateMonth = sdateMonth;
    }
    @Column(name = "edate_month")
    public String getEdateMonth() {
        return edateMonth;
    }

    public void setEdateMonth(String edateMonth) {
        this.edateMonth = edateMonth;
    }
    @Column(name = "scontrol")
    public Double getScontrol() {
        return scontrol;
    }

    public void setScontrol(Double scontrol) {
        this.scontrol = scontrol;
    }
    @Column(name = "econtrol")
    public Double getEcontrol() {
        return econtrol;
    }

    public void setEcontrol(Double econtrol) {
        this.econtrol = econtrol;
    }
    @Column(name = "scontrol_status")
    public String getScontrolStatus() {
        return scontrolStatus;
    }

    public void setScontrolStatus(String scontrolStatus) {
        this.scontrolStatus = scontrolStatus;
    }
    @Column(name = "econtrol_status")
    public String getEcontrolStatus() {
        return econtrolStatus;
    }

    public void setEcontrolStatus(String econtrolStatus) {
        this.econtrolStatus = econtrolStatus;
    }
    @Column(name = "min_cents_price")
    public Double getMinCentsPrice() {
        return minCentsPrice;
    }

    public void setMinCentsPrice(Double minCentsPrice) {
        this.minCentsPrice = minCentsPrice;
    }
    @Column(name = "max_cents_price")
    public Double getMaxCentsPrice() {
        return maxCentsPrice;
    }

    public void setMaxCentsPrice(Double maxCentsPrice) {
        this.maxCentsPrice = maxCentsPrice;
    }
    @Column(name = "formula_type")
    public String getFormulaType() {
        return formulaType;
    }

    public void setFormulaType(String formulaType) {
        this.formulaType = formulaType;
    }
}
