
package com.maywide.biz.core.pojo;

/**
 * 
 *
 */
public class TokenReturnInfo extends ReturnInfo {
    private static final long serialVersionUID = -3957351864958556724L;

    private Object data;         // 输出数据

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
