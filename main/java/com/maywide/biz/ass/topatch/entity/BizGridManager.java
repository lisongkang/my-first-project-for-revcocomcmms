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
@Table(name = "BIZ_GRID_MANAGER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BizGridManager extends PersistableEntity<Long> implements Persistable<Long> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1847597820496148711L;

	@MetaData(value = "经理id")
    @EntityAutoCode
    private Long id;
	
	@MetaData(value = "网格id")
    @EntityAutoCode(order = 1, listShow = true)
	private Long gridid;
	
	@MetaData(value = "网格经理id")
    @EntityAutoCode(order = 2, listShow = true)
	private Long operid;
	
	private String opername;
	
	@MetaData(value = "是否主经理")
    @EntityAutoCode(order = 4, listShow = true)
	private String isMain;
	
	@MetaData(value = "最后更新时间")
    @EntityAutoCode(order = 9, listShow = true)
	private Date updatetime;
	
	@MetaData(value = "备注")
	private String memo;
	
	private Long areamger; //配合Biz.getOperatorOptions所增加的临时字段
	
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mnrid", unique = true, length = 16)
	public Long getId() {
		return id;
	}

    public void setId(Long id) {
		this.id = id;
	}
    
	@Override
	@Transient
	public String getDisplay() {
		return "";
	}

	@Column(name = "gridid", nullable = true, length = 16)
	public Long getGridid() {
		return gridid;
	}

	public void setGridid(Long gridid) {
		this.gridid = gridid;
	}

	@Column(name = "operid", nullable = true, length = 16)
	public Long getOperid() {
		return operid;
	}

	public void setOperid(Long operid) {
		this.operid = operid;
	}

	@Column(name = "ismain", nullable = true, length = 1)
	public String getIsMain() {
		return isMain;
	}

	public void setIsMain(String isMain) {
		this.isMain = isMain;
	}

	@Column(name = "updatetime", nullable = true)
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	@Column(name = "memo", nullable = true, length = 256)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Transient
    public Long getAreamger() {
        return areamger;
    }

    public void setAreamger(Long areamger) {
        this.areamger = areamger;
    }

    @Transient
	public String getOpername() {
		return opername;
	}

	public void setOpername(String opername) {
		this.opername = opername;
	}
    
}
