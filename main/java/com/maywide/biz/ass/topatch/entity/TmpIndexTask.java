package com.maywide.biz.ass.topatch.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import com.maywide.core.annotation.MetaData;
import com.maywide.core.entity.PersistableEntity;
import com.maywide.core.entity.annotation.EntityAutoCode;

@Entity
@Table(name = "TMP_INDEX_TASK")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TmpIndexTask extends PersistableEntity<Long> implements Persistable<Long> {
    /**
     * 
     */
    private static final long serialVersionUID = 5876654949404832376L;

    @MetaData(value = "记录序号")
    @EntityAutoCode
    private Long              id;

    @MetaData(value = "临时流水号")
    @EntityAutoCode(order = 1, listShow = true)
    private String            serialno;

    @MetaData(value = "期序号")
    @EntityAutoCode(order = 2, listShow = true)
    private String            objcode;

    @MetaData(value = "当期目标数")
    @EntityAutoCode(order = 3, listShow = true)
    private Double            nums;

    @MetaData(value = "操作部门")
    @EntityAutoCode(order = 4, listShow = true)
    private Long              depart;

    @MetaData(value = "操作员")
    @EntityAutoCode(order = 5, listShow = true)
    private Long              operator;

    @MetaData(value = "操作时间")
    @EntityAutoCode(order = 6, listShow = true)
    private Date              opdate;

    @MetaData(value = "检查结果")
    @EntityAutoCode(order = 7, listShow = true)
    private String            ispass;

    @MetaData(value = "结果说明")
    @EntityAutoCode(order = 8, listShow = true)
    private String            memo;

    public TmpIndexTask() {
    }

    public TmpIndexTask(String objcode, Double nums, boolean isPass, String memo) {
        this.objcode = objcode;
        this.nums = nums;
        this.ispass = isPass ? "Y" : "N";
        this.memo = memo;
    }

    public TmpIndexTask(String objcode, Double nums, boolean isPass, StringBuffer memo) {
        this(objcode, nums, isPass, null != memo ? memo.toString() : null);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recid", unique = true, length = 16)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SERIALNO", nullable = true, length = 20)
    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    @Column(name = "OBJCODE", nullable = true, length = 20)
    public String getObjcode() {
        return objcode;
    }

    public void setObjcode(String objcode) {
        this.objcode = objcode;
    }

    @Column(name = "NUMS", nullable = true)
    public Double getNums() {
        return nums;
    }

    public void setNums(Double nums) {
        this.nums = nums;
    }

    @Column(name = "DEPART", nullable = true, length = 16)
    public Long getDepart() {
        return depart;
    }

    public void setDepart(Long depart) {
        this.depart = depart;
    }

    @Column(name = "OPERATOR", nullable = true, length = 16)
    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    @Column(name = "OPDATE", nullable = true)
    public Date getOpdate() {
        return opdate;
    }

    public void setOpdate(Date opdate) {
        this.opdate = opdate;
    }

    @Column(name = "ISPASS", nullable = true, length = 1)
    public String getIspass() {
        return ispass;
    }

    public void setIspass(String ispass) {
        this.ispass = ispass;
    }

    @Column(name = "MEMO", nullable = true, length = 255)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    @Transient
    public String getDisplay() {
        return "";
    }
}