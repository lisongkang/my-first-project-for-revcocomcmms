package com.maywide.biz.ass.store.entity;

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
@Table(name = "ASS_INDEX_OBJECT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AssIndexObject extends PersistableEntity<Long> implements Persistable<Long> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2994853381767860574L;

	@MetaData(value = "考核指标id")
    @EntityAutoCode
    private Long id;
	
	@MetaData(value = "考核指标id")
    @EntityAutoCode(order = 1, listShow = true)
	private Long assid;
	
	@MetaData(value = "考核参数对象")
    @EntityAutoCode(order = 2, listShow = true)
	private String assobj;
	
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "objid", unique = true, length = 16)
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

	@Column(name = "assobj", nullable = true, length = 255)
	public String getAssobj() {
		return assobj;
	}

	public void setAssobj(String assobj) {
		this.assobj = assobj;
	}

}
