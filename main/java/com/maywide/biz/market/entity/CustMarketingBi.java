package com.maywide.biz.market.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.domain.Persistable;

import com.maywide.biz.prv.entity.PrvArea;
import com.maywide.core.entity.PersistableEntity;

@Entity
@Table(name = "BIZ_CUST_MARKETING_BI")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustMarketingBi extends PersistableEntity<Long> implements Persistable<Long> {
	private Long id;
	private Long custid;
	private String name;
	private String linkphone;
	private Long houseid;
	private String whladdr;
	private PrvArea area;
	private ResPatch ptach;
	private String pri;
	@Temporal(TemporalType.TIMESTAMP)
	private Date appdate;
	private String dealstatus;
	private String city;
	private String ids;
	private String objtype;
	private Long objid;
	private String pname;
	
	@Override
    @Transient
    public String getDisplay() {
        return null;
    }
	
	@Id	
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "markid")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCustid() {
		return custid;
	}
	public void setCustid(Long custid) {
		this.custid = custid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLinkphone() {
		return linkphone;
	}
	public void setLinkphone(String linkphone) {
		this.linkphone = linkphone;
	}
	public Long getHouseid() {
		return houseid;
	}
	public void setHouseid(Long houseid) {
		this.houseid = houseid;
	}
	public String getWhladdr() {
		return whladdr;
	}
	public void setWhladdr(String whladdr) {
		this.whladdr = whladdr;
	}
	public String getPri() {
		return pri;
	}
	public void setPri(String pri) {
		this.pri = pri;
	}
	public Date getAppdate() {
		return appdate;
	}
	public void setAppdate(Date appdate) {
		this.appdate = appdate;
	}
	public String getDealstatus() {
		return dealstatus;
	}
	public void setDealstatus(String dealstatus) {
		this.dealstatus = dealstatus;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "ptachid")
    @NotFound(action=NotFoundAction.IGNORE)
	public ResPatch getPtach() {
		return ptach;
	}
	public void setPtach(ResPatch ptach) {
		this.ptach = ptach;
	}
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "areaid")
    public PrvArea getArea() {
		return area;
	}
	public void setArea(PrvArea area) {
		this.area = area;
	}
	@Transient
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getObjtype() {
		return objtype;
	}

	public void setObjtype(String objtype) {
		this.objtype = objtype;
	}

	public Long getObjid() {
		return objid;
	}

	public void setObjid(Long objid) {
		this.objid = objid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}
}
