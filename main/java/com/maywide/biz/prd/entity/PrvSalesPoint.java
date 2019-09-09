package com.maywide.biz.prd.entity;

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
@Table(name="prv_sales_point")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PrvSalesPoint extends PersistableEntity<Long> implements Persistable<Long> {
    
	private Long id;
    private String opcode;
    private Long salesid;
    private String salesname;
    private Long wtype;
    private Long points;
    private Long isvalid;
    private Date etime;
    private String city;
  //  private Long areaid;
    private String memo;
    private Date intime;
    
    private String cityname;
 //   private String areaname;
    private String opcodename;
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SID")
	public Long getId() {
		return id;
	}
	
	@Override
	@Transient
	public String getDisplay() {
		return "";
	}

	@Column(name = "OPCODE")
	public String getOpcode() {
		return opcode;
	}

	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}
    
	@Column(name = "SALESID")
	public Long getSalesid() {
		return salesid;
	}

	public void setSalesid(Long salesid) {
		this.salesid = salesid;
	}

	@Column(name = "SALESNAME")
	public String getSalesname() {
		return salesname;
	}

	public void setSalesname(String salesname) {
		this.salesname = salesname;
	}

	@Column(name = "WTYPE")
	public Long getWtype() {
		return wtype;
	}

	public void setWtype(Long wtype) {
		this.wtype = wtype;
	}

	@Column(name = "POINTS")
	public Long getPoints() {
		return points;
	}

	public void setPoints(Long points) {
		this.points = points;
	}

	@Column(name = "ISVALID")
	public Long getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(Long isvalid) {
		this.isvalid = isvalid;
	}

	@Column(name = "ETIME")
	public Date getEtime() {
		return etime;
	}

	public void setEtime(Date etime) {
		this.etime = etime;
	}

	@Column(name = "CITY")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name = "MEMO")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	
	public void setId(Long id) {
		this.id = id;
	}

	@Transient
	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
    
	@Transient
	public String getOpcodename() {
		return opcodename;
	}

	public void setOpcodename(String opcodename) {
		this.opcodename = opcodename;
	}

	public Date getIntime() {
		return intime;
	}

	public void setIntime(Date intime) {
		this.intime = intime;
	}
    
}
