package com.maywide.biz.core.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class LoginUser implements Serializable {
    private static final long serialVersionUID = 644837329426328974L;

    private Long opId = null;                   // ����
    private String opCode = null;               // ���
    private Long areaId = null;                  // ��¼Ӫҵ����
    private String orgName = null;              // ��¼Ӫҵ�����
    private Integer countyCode = null;          // ����������
    private String countyName = null;           // �������
    private Integer regionCode = null;          // ������д���
    private String regionName = null;           // �������
    private String MAC = null;                  // mac��ַ
    private String IMEI = null;                 // imei��
    private String clientIP = null;             // �ͻ���IP��ַ
    private String mobile = null;               // �󶨵��ֻ����
    private Timestamp loginTime = null;         // ��¼ʱ��

    public Long getOpId() {
        return opId;
    }

    public void setOpId(Long opId) {
        this.opId = opId;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Integer getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(Integer countyCode) {
        this.countyCode = countyCode;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public Integer getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(Integer regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }
}
