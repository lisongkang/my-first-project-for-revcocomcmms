
package com.maywide.biz.core.pojo;

import java.io.Serializable;

/**
 * ���ر��ķ�װ�ṹ
 *
 */
public class ResponseWrapper implements Serializable {
    private static final long serialVersionUID = -8240719319595485919L;

    private ReturnInfo returnInfo = null;       // ���ؽ��
    private String responseBody = null;         // ��Ӧ����

    public ReturnInfo getReturnInfo() {
        return returnInfo;
    }

    public void setReturnInfo(ReturnInfo returnInfo) {
        this.returnInfo = returnInfo;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }
}
