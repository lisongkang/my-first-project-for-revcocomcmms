package com.maywide.biz.prd.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;

import com.maywide.core.entity.PersistableEntity;

@Entity
@Table(name = "PRV_PRD_RULE")
@SuppressWarnings("serial")
public class PrvPrdRule extends PersistableEntity<Long> implements Persistable<Long> {

	private Long id; // 规则ID
	private Long areaid; // 业务区
	private String objtype; // 推荐对像类型-商品：0，促销优惠：1
	private String value; // 推荐对像值
	private Long jumpmenuid; // 跳转菜单-菜单id
	private String message; // 提示语
	private Long createoperid; // 创建人
	private Date createtime; // 创建时间
	private Date exptime; // 规则失效时间

	private String prdname;
	private String opername;
	private String menuname;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAreaid() {
		return areaid;
	}

	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}

	public String getObjtype() {
		return objtype;
	}

	public void setObjtype(String objtype) {
		this.objtype = objtype;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getJumpmenuid() {
		return jumpmenuid;
	}

	public void setJumpmenuid(Long jumpmenuid) {
		this.jumpmenuid = jumpmenuid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getCreateoperid() {
		return createoperid;
	}

	public void setCreateoperid(Long createoperid) {
		this.createoperid = createoperid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getExptime() {
		return exptime;
	}

	public void setExptime(Date exptime) {
		this.exptime = exptime;
	}

	@Override
	@Transient
	public String getDisplay() {
		return null;
	}

	@Transient
	public String getPrdname() {
		return prdname;
	}

	public void setPrdname(String prdname) {
		this.prdname = prdname;
	}

	@Transient
	public String getOpername() {
		return opername;
	}

	public void setOpername(String opername) {
		this.opername = opername;
	}

	@Transient
	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

}
