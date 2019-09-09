package com.maywide.biz.inter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;

import com.maywide.core.entity.PersistableEntity;

@Entity
@Table(name="SYS_CAT_MENU")
public class SysCatMenu extends PersistableEntity<Long> implements Persistable<Long>{

	private Long id;
	
	private Long cid;
	
	private Long menuid;
	
	private String pName;
	
	private String bizCode;
	
	private String urlAddr;
	
	private String city;
	
	private String desctipt;
	
	private int sort;
	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recid", unique = true, length = 16)
	public Long getId() {
		return id;
	}

	@Override
	@Transient
	public String getDisplay() {
		return null;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Long getMenuid() {
		return menuid;
	}

	public void setMenuid(Long menuid) {
		this.menuid = menuid;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getUrlAddr() {
		return urlAddr;
	}

	public void setUrlAddr(String urlAddr) {
		this.urlAddr = urlAddr;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDesctipt() {
		return desctipt;
	}

	public void setDesctipt(String desctipt) {
		this.desctipt = desctipt;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	/*public Long getRecId(){
		return id;
	}
	
	public void setRecId(Long recId){
		this.id = recId;
	}*/

}
