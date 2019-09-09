package com.maywide.biz.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "prv_error_convert")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ErrorConvert implements Serializable {
	private static final long serialVersionUID = 760672695156109464L;

	private Long convertId = null;
    private String interfaceName = null;
    private String errorCode = null;
    private String errorMsg = null;
    private String remark = null;

    @Id	
	@Column(name="convert_id")
    public Long getConvertId() {
		return convertId;
	}

	public void setConvertId(Long convertId) {
		this.convertId = convertId;
	}

    public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
