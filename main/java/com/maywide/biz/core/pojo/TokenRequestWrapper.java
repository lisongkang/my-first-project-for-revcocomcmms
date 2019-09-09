package com.maywide.biz.core.pojo;

import java.io.Serializable;

/**
 *
 */
public class TokenRequestWrapper implements Serializable {
    private static final long serialVersionUID = 5818789397079057608L;

    private String api;             // 接口名称
    private Object requestBody;     // 参数

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public Object getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(Object requestBody) {
        this.requestBody = requestBody;
    }
}
