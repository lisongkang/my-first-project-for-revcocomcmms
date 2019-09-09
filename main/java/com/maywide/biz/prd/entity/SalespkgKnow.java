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

@Entity
@Table(name = "PRD_SALESPKG_KNOW")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SalespkgKnow extends PersistableEntity<Long> implements Persistable<Long> {
	private Long id;
	@MetaData(value = "营销名称")
	private String knowname;
	@MetaData(value = "简要描述")
	private String brief;
	@MetaData(value = "对象类型")
	private String objtype;
	@MetaData(value = "对象名称")
	private Long objid;
	@MetaData(value = "价格")
	private Double price;
	@MetaData(value = "办理业务")
	private String opcodes;
	@MetaData(value = "建议推荐说明")
	private String tocust;
	@MetaData(value = "推荐导语")
	private String wordexp;
	@MetaData(value = "优惠内容")
	private String feeexp;
	private Long createoper;
	private Date createtime;
	private Long updateoper;
	private Date updatetime;
	@MetaData(value = "地市")
	private String city;
	@MetaData(value = "可销售次数")
	private Long maxtimes;
	@MetaData(value = "图标")
	private String icon;
	
	private String image;
	
	private String memo;

    private String objcode;   // 产品编码
    private String objname;   // 产品名称

	@Override
    @Transient
    public String getDisplay() {
        return knowname;
    }
	
	@Id	
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "knowid")
	public Long getId() {
		return id;
	}
	
	public void setId(Long knowid) {
		this.id = knowid;
	}
	
	@Transient
	public Long getKnowid() {
		return id;
	}
	public void setKnowid(Long knowid) {
		this.id = knowid;
	}
	@Column(nullable = false)
	public String getKnowname() {
		return knowname;
	}
	public void setKnowname(String knowname) {
		this.knowname = knowname;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	@Column(nullable = false)
	public String getObjtype() {
		return objtype;
	}
	public void setObjtype(String objtype) {
		this.objtype = objtype;
	}
	@Column(nullable = false)
	public Long getObjid() {
		return objid;
	}
	public void setObjid(Long objid) {
		this.objid = objid;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getOpcodes() {
		return opcodes;
	}
	public void setOpcodes(String opcodes) {
		this.opcodes = opcodes;
	}
	public String getTocust() {
		return tocust;
	}
	public void setTocust(String tocust) {
		this.tocust = tocust;
	}
	public String getWordexp() {
		return wordexp;
	}
	public void setWordexp(String wordexp) {
		this.wordexp = wordexp;
	}
	public String getFeeexp() {
		return feeexp;
	}
	public void setFeeexp(String feeexp) {
		this.feeexp = feeexp;
	}
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

	public Long getMaxtimes() {
		return maxtimes;
	}

	public void setMaxtimes(Long maxtimes) {
		this.maxtimes = maxtimes;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

    @Transient
    public String getObjcode() {
        return objcode;
    }

    public void setObjcode(String objcode) {
        this.objcode = objcode;
    }

    @Transient
    public String getObjname() {
        return objname;
    }

    public void setObjname(String objname) {
        this.objname = objname;
    }
}
