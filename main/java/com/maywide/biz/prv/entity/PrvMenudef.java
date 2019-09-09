package com.maywide.biz.prv.entity;

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

import com.maywide.core.entity.PersistableEntity;

@Entity
@Table(name = "PRV_MENUDEF")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PrvMenudef extends PersistableEntity<Long> implements Persistable<Long> {

	private Long id;
	private Long premenuid;
	private String name;
	private String linkurl;
	private String iconid;
	private String flag;
	private String prefix;
	private String bizcode;
	private String attr;
	private String pinyin;
	private String memo;
	private String target;
	private Long seqno;
	private String oflag1;
	private String oflag2;
	private Integer sysid;
	private String atype;
	
	private String _orderby_prefix;
	private String _orderby_seqno;
	
	@Override
    @Transient
    public String getDisplay() {
        return name;
    }

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MENUID", nullable = false, unique = true, insertable = true, updatable = true, precision = 16, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "PREMENUID", nullable = true, unique = false, insertable = true, updatable = true, precision = 16, scale = 0)
	public Long getPremenuid() {
		return premenuid;
	}

	public void setPremenuid(Long premenuid) {
		this.premenuid = premenuid;
	}

	@Column(name = "NAME", nullable = false, unique = false, insertable = true, updatable = true, length = 64)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "LINKURL", nullable = true, unique = false, insertable = true, updatable = true, length = 255)
	public String getLinkurl() {
		return linkurl;
	}

	public void setLinkurl(String linkurl) {
		this.linkurl = linkurl;
	}

	@Column(name = "ICONID", nullable = true, unique = false, insertable = true, updatable = true, length = 255)
	public String getIconid() {
		return iconid;
	}

	public void setIconid(String iconid) {
		this.iconid = iconid;
	}

	@Column(name = "FLAG", nullable = true, unique = false, insertable = true, updatable = true, length = 1)
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Column(name = "PREFIX", nullable = true, unique = false, insertable = true, updatable = true, length = 20)
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Column(name = "BIZCODE", nullable = true, unique = false, insertable = true, updatable = true, length = 20)
	public String getBizcode() {
		return bizcode;
	}

	public void setBizcode(String bizcode) {
		this.bizcode = bizcode;
	}

	@Column(name = "ATTR", nullable = true, unique = false, insertable = true, updatable = true, length = 1)
	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	@Column(name = "PINYIN", nullable = true, unique = false, insertable = true, updatable = true, length = 64)
	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	@Column(name = "MEMO", nullable = true, unique = false, insertable = true, updatable = true, length = 255)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "TARGET", nullable = true, unique = false, insertable = true, updatable = true, length = 20)
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Column(name = "SEQNO", nullable = true, unique = false, insertable = true, updatable = true, precision = 16, scale = 0)
	public Long getSeqno() {
		return seqno;
	}

	public void setSeqno(Long seqno) {
		this.seqno = seqno;
	}
	
	@Column(name = "OFLAG1", nullable = true, unique = false, insertable = true, updatable = true, length = 1)
	public String getOflag1() {
		return oflag1;
	}

	public void setOflag1(String oflag1) {
		this.oflag1 = oflag1;
	}
	
	@Column(name = "OFLAG2", nullable = true, unique = false, insertable = true, updatable = true, length = 1)
	public String getOflag2() {
		return oflag2;
	}

	public void setOflag2(String oflag2) {
		this.oflag2 = oflag2;
	}
	public Integer getSysid() {
		return sysid;
	}

	public void setSysid(Integer sysid) {
		this.sysid = sysid;
	}

	@Column(name = "ATYPE", nullable = true, unique = false, insertable = true, updatable = true, length = 2)
	public String getAtype() {
		return atype;
	}

	public void setAtype(String atype) {
		this.atype = atype;
	}
	
	@Transient
	public String get_orderby_prefix() {
		return _orderby_prefix;
	}

	public void set_orderby_prefix(String orderbyPrefix) {
		_orderby_prefix = orderbyPrefix;
	}
	@Transient
	public String get_orderby_seqno() {
		return _orderby_seqno;
	}

	public void set_orderby_seqno(String orderbySeqno) {
		_orderby_seqno = orderbySeqno;
	}
}