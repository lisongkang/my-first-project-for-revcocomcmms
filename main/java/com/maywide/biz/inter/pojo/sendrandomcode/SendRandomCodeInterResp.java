package com.maywide.biz.inter.pojo.sendrandomcode;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
/*@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SendRandomCodeInterResp implements java.io.Serializable {
    // 为接口返回预留
    //使用以下注解避免jackson序列化不存在public getter方法的对象时报错
    //@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
}
*/
public class SendRandomCodeInterResp implements java.io.Serializable {
    private String custorderid;

    public String getCustorderid() {
        return custorderid;
    }

    public void setCustorderid(String custorderid) {
        this.custorderid = custorderid;
    }
    
}