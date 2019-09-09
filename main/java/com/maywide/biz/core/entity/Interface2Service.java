package com.maywide.biz.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "prv_interface_2_service")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Interface2Service implements Serializable {
	private static final long serialVersionUID = -5831535671392411375L;

    private String interfaceName = null;
    private String serviceImpl = null;
    private String methodName = null;
    private String inparamType = null;
    private String outparamType = null;
    private String remark = null;

    @Id	
	@Column(name="interface_name")
    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getServiceImpl() {
        return serviceImpl;
    }

    public void setServiceImpl(String serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getInparamType() {
        return inparamType;
    }

    public void setInparamType(String inparamType) {
        this.inparamType = inparamType;
    }

    public String getOutparamType() {
        return outparamType;
    }

    public void setOutparamType(String outparamType) {
        this.outparamType = outparamType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
