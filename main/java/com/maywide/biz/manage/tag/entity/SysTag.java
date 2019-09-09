package com.maywide.biz.manage.tag.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import com.maywide.core.entity.PersistableEntity;

@Entity
@Table(name = "sys_tag")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysTag extends PersistableEntity<Long> implements Persistable<Long> {
    private Long id;
    private String tagcode; //2017/2/22 追加 记录cmp获取的标签ID
    private String tagname;
    private String tagdesc;
    private String owner;
    private String isshow;
    private String city;
    private String memo;
   
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="TAGID")
	public Long getId() {
		return id;
	}
    
	@Transient
	public String getDisplay() {
		return null;
	}

	public String getTagname() {
		return tagname;
	}

	public void setTagname(String tagname) {
		this.tagname = tagname;
	}

	public String getTagdesc() {
		return tagdesc;
	}

	public void setTagdesc(String tagdesc) {
		this.tagdesc = tagdesc;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getIsshow() {
		return isshow;
	}

	public void setIsshow(String isshow) {
		this.isshow = isshow;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTagcode() {
		return tagcode;
	}

	public void setTagcode(String tagcode) {
		this.tagcode = tagcode;
	}
	
	
  
}
