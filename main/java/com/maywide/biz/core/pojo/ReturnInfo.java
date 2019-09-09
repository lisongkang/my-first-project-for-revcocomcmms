
package com.maywide.biz.core.pojo;

import java.io.Serializable;

/**
 * 
 *
 */
public class ReturnInfo implements Serializable {
    private static final long serialVersionUID = -3957351864958556724L;

    private Long code = null;           // ���ش���
    private String message = null;      // ������Ϣ

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
