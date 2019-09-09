package com.maywide.biz.ass.topatch.entity;

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
@Table(name = "BIZ_GRID_INFO")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BizGridInfo extends PersistableEntity<Long> implements Persistable<Long> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1847597820496148711L;

	@MetaData(value = "网格id")
    @EntityAutoCode
    private Long id;
	
	@MetaData(value = "网格代码")
    @EntityAutoCode(order = 1, listShow = true)
	private String gridcode;
	
	@MetaData(value = "网格名称")
    @EntityAutoCode(order = 2, listShow = true)
	private String gridname;
	
	@MetaData(value = "网格类型")
    @EntityAutoCode(order = 3, listShow = true)
	private Long gtype;
	
	@MetaData(value = "网格经理id")
    @EntityAutoCode(order = 4, listShow = true)
	private Long mnrid;
	
	@MetaData(value = "排序优先级")
    @EntityAutoCode(order = 5, listShow = true)
	private Long prio;
	
	@MetaData(value = "上一级网格id")
    @EntityAutoCode(order = 6, listShow = true)
	private Long previd;
	
	@MetaData(value = "片区id")
    @EntityAutoCode(order = 7, listShow = true)
	private Long patchid;
	
	private String patchname;
	
	@MetaData(value = "备注")
	private String memo;
	
    @MetaData(value = "网格所属分公司")
    private String city;
	
	private Long subgridid;
	
	@MetaData(value = "统计网格ID")
	private Long statid;
	
	private String gridPath;
	private String preGridName;

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "gridid", unique = true, length = 16)
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

	@Column(name = "gridcode", nullable = true, length = 32)
	public String getGridcode() {
		return gridcode;
	}

	public void setGridcode(String gridcode) {
		this.gridcode = gridcode;
	}

	@Column(name = "gridname", nullable = true, length = 128)
	public String getGridname() {
		return gridname;
	}

	public void setGridname(String gridname) {
		this.gridname = gridname;
	}
	
	@Column(name = "gtype", nullable = true, length = 16)
	public Long getGtype() {
		return gtype;
	}

	public void setGtype(Long gtype) {
		this.gtype = gtype;
	}

	@Column(name = "mnrid", nullable = true, length = 16)
	public Long getMnrid() {
		return mnrid;
	}

	public void setMnrid(Long mnrid) {
		this.mnrid = mnrid;
	}

	@Column(name = "prio", nullable = true, length = 16)
	public Long getPrio() {
		return prio;
	}

	public void setPrio(Long prio) {
		this.prio = prio;
	}

	@Column(name = "previd", nullable = true, length = 16)
	public Long getPrevid() {
		return previd;
	}

	public void setPrevid(Long previd) {
		this.previd = previd;
	}

	@Column(name = "patchid", nullable = true, length = 16)
	public Long getPatchid() {
		return patchid;
	}

	public void setPatchid(Long patchid) {
		this.patchid = patchid;
	}

	@Transient
	public String getPatchname() {
		return patchname;
	}

	public void setPatchname(String patchname) {
		this.patchname = patchname;
	}

	@Column(name = "memo", nullable = true, length = 256)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
    @Column(name = "city", nullable = true, length = 2)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Transient
    public Long getSubgridid() {
        return subgridid;
    }

    public void setSubgridid(Long subgridid) {
        this.subgridid = subgridid;
    }

    @Column(name = "statid", nullable = true, length = 16)
    public Long getStatid() {
        return statid;
    }

    public void setStatid(Long statid) {
        this.statid = statid;
    }

    @Transient
    public String getGridPath() {
        return gridPath;
    }

    public void setGridPath(String gridPath) {
        this.gridPath = gridPath;
    }

    @Transient
    public String getPreGridName() {
        return preGridName;
    }

    public void setPreGridName(String preGridName) {
        this.preGridName = preGridName;
    }
}