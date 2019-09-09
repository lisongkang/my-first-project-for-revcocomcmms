package com.maywide.biz.core.pojo.api;

import java.io.Serializable;
import java.util.Date;

public class ApiMethodInfo implements Serializable {
	private static final long serialVersionUID = -1932868451021314749L;

    private String apiId = null;                // �ӿ����
    private Date callTime = null;               // ����ʱ��
    private Date echoTime = null;               // ����ʱ��
    private boolean success = false;            // �Ƿ���óɹ�

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public Date getCallTime() {
        return callTime;
    }

    public void setCallTime(Date callTime) {
        this.callTime = callTime;
    }

    public Date getEchoTime() {
        return echoTime;
    }

    public void setEchoTime(Date echoTime) {
        this.echoTime = echoTime;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
