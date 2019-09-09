package com.maywide.biz.ass.togrid.entity;

import java.util.Date;
import java.util.List;

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
@Table(name = "ASS_INDEX_TOGRID")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AssIndexTogrid extends PersistableEntity<Long> implements Persistable<Long> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 914667073517473751L;

	@MetaData(value = "任务序号id")
    @EntityAutoCode
    private Long id;
	
	@MetaData(value = "考核指标id")
    @EntityAutoCode(order = 1, listShow = true)
	private Long assid;
	
	@MetaData(value = "网格id")
    @EntityAutoCode(order = 2, listShow = true)
	private Long gridid;
	
	@MetaData(value = "网格类型")
    @EntityAutoCode(order = 3, listShow = true)
	private String gridtype;
	
	private String gridname;
	
	private List<String> gridids;
	
	private String gridcode ;
	
	@MetaData(value = "考核起始日期")
    @EntityAutoCode(order = 4, listShow = true)
	private Date bdate;
	
	@MetaData(value = "完成目标方式")
    @EntityAutoCode(order = 5, listShow = true)
	private String mode;
	
	@MetaData(value = "每期考核数")
    @EntityAutoCode(order = 6, listShow = true)
	private Double assnum;
	
	@MetaData(value = "下达时间")
    @EntityAutoCode(order = 7, listShow = true)
	private Date assdate;
	
	@MetaData(value = "下达部门")
    @EntityAutoCode(order = 8, listShow = true)
	private Long depart;
	
	@MetaData(value = "下达单位类型")
    @EntityAutoCode(order = 9, listShow = true)
    private String asstype;
	
	@MetaData(value = "下达单位")
    @EntityAutoCode(order = 10, listShow = true)
    private String assunit;
	
	@MetaData(value = "操作员")
    @EntityAutoCode(order = 11, listShow = true)
	private Long operator;
	
	private String asscontent;
	private String assStoreUnit;
	
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "taskid", unique = true, length = 16)
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

	@Column(name = "assid", nullable = true, length = 16)
	public Long getAssid() {
		return assid;
	}

	public void setAssid(Long assid) {
		this.assid = assid;
	}

	@Column(name = "gridid", nullable = true, length = 16)
	public Long getGridid() {
		return gridid;
	}

	public void setGridid(Long gridid) {
		this.gridid = gridid;
	}
	
	@Column(name = "gridtype", nullable = true, length = 1)
	public String getGridtype() {
        return gridtype;
    }

    public void setGridtype(String gridtype) {
        this.gridtype = gridtype;
    }

    @Transient
	public String getGridname() {
		return gridname;
	}

	public void setGridname(String gridname) {
		this.gridname = gridname;
	}

	@Transient
	public List<String> getGridids() {
		return gridids;
	}

	public void setGridids(List<String> gridids) {
		this.gridids = gridids;
	}

	@Column(name = "bdate", nullable = true)
	public Date getBdate() {
		return bdate;
	}

	public void setBdate(Date bdate) {
		this.bdate = bdate;
	}

	@Column(name = "mode", nullable = true, length = 1)
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	@Column(name = "assnum", nullable = true)
	public Double getAssnum() {
		return assnum;
	}

	public void setAssnum(Double assnum) {
		this.assnum = assnum;
	}

	@Column(name = "assdate", nullable = true)
	public Date getAssdate() {
		return assdate;
	}

	public void setAssdate(Date assdate) {
		this.assdate = assdate;
	}

	@Column(name = "depart", nullable = true, length = 16)
	public Long getDepart() {
		return depart;
	}

	public void setDepart(Long depart) {
		this.depart = depart;
	}
	
	@Column(name = "asstype", nullable = true, length = 1)
	public String getAsstype() {
        return asstype;
    }

    public void setAsstype(String asstype) {
        this.asstype = asstype;
    }

    @Column(name = "assunit", nullable = true, length = 30)
    public String getAssunit() {
        return assunit;
    }

    public void setAssunit(String assunit) {
        this.assunit = assunit;
    }

    @Column(name = "operator", nullable = true, length = 16)
	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	@Transient
    public String getAsscontent() {
        return asscontent;
    }

    public void setAsscontent(String asscontent) {
        this.asscontent = asscontent;
    }

    @Transient
    public String getAssStoreUnit() {
        return assStoreUnit;
    }

    public void setAssStoreUnit(String assStoreUnit) {
        this.assStoreUnit = assStoreUnit;
    }

    
	@Transient
	public String getGridcode() {
		return gridcode;
	}

	public void setGridcode(String gridcode) {
		this.gridcode = gridcode;
	}
    
    
}
