package com.maywide.biz.core.pojo;

import java.io.Serializable;

/**
 * ��װ�����Ľṹ��
 *
 */
public class RequestWrapper implements Serializable {
    private static final long serialVersionUID = 5818789397079057608L;

    private String api;             // API�������
    private String requestBody;     //

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }
}
