package com.maywide.biz.core.entity;

import java.io.Serializable;
import java.util.Date;

public class DeviceEnrollment implements Serializable {
	private static final long serialVersionUID = -4825723031346516660L;

    public enum EnrollmentSts {
    	STS_APPLY,      // 申请等审核
        STS_ACCEPT,     // 审核通过
        STS_REJECT,     // 审核拒绝
        STS_DELETE      // 记录作废
    }

    private String deviceMAC = null;
    private String deviceAlias = null;
    private String deviceIMEI = null;
    private String bindMobile = null;
    private Date enrollmentDate = null;
    private String enrollmentRemark = null;
    private Long enrollmentOp = null;
    private Date validateDate = null;
    private Date expiredDate = null;
    private Integer sts = null;
    private Long auditOp = null;
    private String auditRemark = null;
    private String sEnrollmentDate = null;
    private String sValidateDate = null;
    private String sExpiredDate = null;

    public String getDeviceMAC() {
        return deviceMAC;
    }

    public void setDeviceMAC(String deviceMAC) {
        this.deviceMAC = deviceMAC;
    }

    public String getDeviceAlias() {
        return deviceAlias;
    }

    public void setDeviceAlias(String deviceAlias) {
        this.deviceAlias = deviceAlias;
    }

    public String getDeviceIMEI() {
        return deviceIMEI;
    }

    public void setDeviceIMEI(String deviceIMEI) {
        this.deviceIMEI = deviceIMEI;
    }

    public String getBindMobile() {
        return bindMobile;
    }

    public void setBindMobile(String bindMobile) {
        this.bindMobile = bindMobile;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getEnrollmentRemark() {
        return enrollmentRemark;
    }

    public void setEnrollmentRemark(String enrollmentRemark) {
        this.enrollmentRemark = enrollmentRemark;
    }

    public Long getEnrollmentOp() {
        return enrollmentOp;
    }

    public void setEnrollmentOp(Long enrollmentOp) {
        this.enrollmentOp = enrollmentOp;
    }

    public Date getValidateDate() {
        return validateDate;
    }

    public void setValidateDate(Date validateDate) {
        this.validateDate = validateDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Integer getSts() {
        return sts;
    }

    public void setSts(Integer sts) {
        this.sts = sts;
    }

    public Long getAuditOp() {
        return auditOp;
    }

    public void setAuditOp(Long auditOp) {
        this.auditOp = auditOp;
    }

    public String getAuditRemark() {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark;
    }

	public String getsEnrollmentDate() {
		return sEnrollmentDate;
	}

	public void setsEnrollmentDate(String sEnrollmentDate) {
		this.sEnrollmentDate = sEnrollmentDate;
	}

	public String getsValidateDate() {
		return sValidateDate;
	}

	public void setsValidateDate(String sValidateDate) {
		this.sValidateDate = sValidateDate;
	}

	public String getsExpiredDate() {
		return sExpiredDate;
	}

	public void setsExpiredDate(String sExpiredDate) {
		this.sExpiredDate = sExpiredDate;
	}
}
