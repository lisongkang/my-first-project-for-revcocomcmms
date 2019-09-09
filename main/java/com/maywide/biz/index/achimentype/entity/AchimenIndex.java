package com.maywide.biz.index.achimentype.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import com.maywide.core.annotation.MetaData;
import com.maywide.core.entity.PersistableEntity;

@Entity
@Table(name = "ACHIMEN_INDEX_MODEL")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AchimenIndex extends PersistableEntity<Long> implements Persistable<Long> {

	@MetaData(value = "id")
    
    private Long id;
	
	@MetaData(value = "指标编码")
	
	private String kpicode;
	
	@MetaData(value = "指标名称")
	
	private String kpiname;
	
	@MetaData(value = "指标图标")
	private String iconame;
	
	@MetaData(value = "指标类型")
	private String kpitype;
	
	@MetaData(value = "地市")
	private String city;
	
	@MetaData(value = "新建时间")
	private Date createtime;
	
	@MetaData(value = "指标状态")
	private String kpistatus;
	
	
	@MetaData(value = "指标说明")
	private String display;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	@Override
	public Long getId() {
		return id;
	}

	@Override
	@Column(name = "explain", nullable = false)
	public String getDisplay() {
		return display;
	}
	
	

	public void setDisplay(String display) {
		this.display = display;
	}

	@Column(name = "kpicode", nullable = false)
	public String getKpicode() {
		return kpicode;
	}

	public void setKpicode(String kpicode) {
		this.kpicode = kpicode;
	}

	@Column(name = "kpiname", nullable = false)
	public String getKpiname() {
		return kpiname;
	}

	public void setKpiname(String kpiname) {
		this.kpiname = kpiname;
	}

	public String getIconame() {
		return iconame;
	}

	public void setIconame(String iconame) {
		this.iconame = iconame;
	}

	public String getKpitype() {
		return kpitype;
	}

	public void setKpitype(String kpitype) {
		this.kpitype = kpitype;
	}

	@Column(name = "city", nullable = false)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "createtime", nullable = false)
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Column(name = "kpistatus", nullable = false)
	public String getKpistatus() {
		return kpistatus;
	}

	public void setKpistatus(String kpistatus) {
		this.kpistatus = kpistatus;
	}


	public void setId(Long id) {
		this.id = id;
	}

	/*public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}*/
	
	

}
