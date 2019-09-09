package com.maywide.biz.market.entity;

import java.util.Date;
import java.util.Set;

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
@Table(name = "RES_PATCH")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ResPatch extends PersistableEntity<Long> implements Persistable<Long> {
    private Long id;
	private Long patchid;
	private String patchname;
	private Long areaid;
	private String defcode;
	private Long placecode;
	private String status;
	private String mark;
	private String responer;
	private String param;
	private Long createoper;
	private Date createdate;
	private Long updateoper;
	private Date updatedate;
	private String memo;
	  
	private Set _in_patchid;
	
	@Override
    @Transient
    public String getDisplay() {
        return patchname;
    }
	
	@Id	
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "patchid")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Transient
	public Long getPatchid() {
		return id;
	}
	public void setPatchid(Long patchid) {
		this.patchid = patchid;
	}
	public String getPatchname() {
		return patchname;
	}
	public void setPatchname(String patchname) {
		this.patchname = patchname;
	}
	public Long getAreaid() {
		return areaid;
	}
	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}

	public String getDefcode() {
        return defcode;
    }

    public void setDefcode(String defcode) {
        this.defcode = defcode;
    }

    public Long getPlacecode() {
        return placecode;
    }

    public void setPlacecode(Long placecode) {
        this.placecode = placecode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getResponer() {
        return responer;
    }

    public void setResponer(String responer) {
        this.responer = responer;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Long getCreateoper() {
        return createoper;
    }

    public void setCreateoper(Long createoper) {
        this.createoper = createoper;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Long getUpdateoper() {
        return updateoper;
    }

    public void setUpdateoper(Long updateoper) {
        this.updateoper = updateoper;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Transient
	public Set get_in_patchid() {
		return _in_patchid;
	}

	public void set_in_patchid(Set inPatchid) {
		_in_patchid = inPatchid;
	}
}
