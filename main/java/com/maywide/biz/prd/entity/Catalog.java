package com.maywide.biz.prd.entity;

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
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.domain.Persistable;

import com.maywide.biz.prv.entity.PrvArea;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.entity.PersistableEntity;
import com.maywide.core.entity.annotation.EntityAutoCode;

@Entity
@Table(name = "prd_catalog")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Catalog extends PersistableEntity<Long> implements Persistable<Long> {
	@MetaData(value = "编号")
	@EntityAutoCode
	private Long id;
	
	@MetaData(value = "名称")
	@EntityAutoCode(order = 5, listShow = true)
	private String catalogname;
	
	@MetaData(value = "描述")
	@EntityAutoCode
	private String catalogdesc;
	
	@MetaData(value = "状态")
	@EntityAutoCode(order = 10, listShow = true)
	private String catalogstatus;
	
	//@MetaData(value = "区域")
	//@EntityAutoCode(order = 15, listShow = true)
	//private Long areaid;
	
	private PrvArea area = new PrvArea();
	
	@MetaData(value = "排序优先级", tooltips = "数字越大优先级越高")
	@EntityAutoCode(order = 20, listShow = true)
	private Long pri;
	
	private String condtiontype;
	
	private String condtionvalue;
	
	@MetaData(value = "创建操作员")
	@EntityAutoCode
	private Long createoper;
	
	@MetaData(value = "创建时间")
	@EntityAutoCode
	private Date createtime;
	
	@MetaData(value = "更新操作员")
	@EntityAutoCode
	private Long updateoper;
	
	@MetaData(value = "更新时间")
	@EntityAutoCode
	private Date updatetime;
	
	@MetaData(value = "地市")
	@EntityAutoCode(order = 35, listShow = true)
	private String city;


	@MetaData(value = "目录类型")
	@EntityAutoCode(order = 15, listShow = true)
	private String ctype ;
	
//	private  String bizopcodes;
	
// String  opcodesname;//页面展示使用
	
	@Override
    @Transient
    public String getDisplay() {
        return catalogname;
    }
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "catalogid", unique = true, length = 16)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(nullable = false, length = 40)
	public String getCatalogname() {
		return catalogname;
	}
	public void setCatalogname(String catalogname) {
		this.catalogname = catalogname;
	}
	@Column(nullable = false, length = 1)
	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}

	@Column(nullable = true, length = 50)
	public String getCatalogdesc() {
		return catalogdesc;
	}
	public void setCatalogdesc(String catalogdesc) {
		this.catalogdesc = catalogdesc;
	}
	@Column(nullable = false, length = 1)
	public String getCatalogstatus() {
		return catalogstatus;
	}
	public void setCatalogstatus(String catalogstatus) {
		this.catalogstatus = catalogstatus;
	}
	/*@Column(nullable = false, length = 8)
	public Long getAreaid() {
		return areaid;
	}
	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}*/
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "areaid", nullable = false)
    @NotFound(action=NotFoundAction.IGNORE)
	public PrvArea getArea() {
		return area;
	}

	public void setArea(PrvArea area) {
		this.area = area;
	}
	
	public Long getPri() {
		return pri;
	}
	
	public void setPri(Long pri) {
		this.pri = pri;
	}
	public String getCondtiontype() {
		return condtiontype;
	}
	public void setCondtiontype(String condtiontype) {
		this.condtiontype = condtiontype;
	}
	public String getCondtionvalue() {
		return condtionvalue;
	}
	public void setCondtionvalue(String condtionvalue) {
		this.condtionvalue = condtionvalue;
	}
	@Column(length = 2)
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public Long getCreateoper() {
		return createoper;
	}

	public void setCreateoper(Long createoper) {
		this.createoper = createoper;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Long getUpdateoper() {
		return updateoper;
	}

	public void setUpdateoper(Long updateoper) {
		this.updateoper = updateoper;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

}
