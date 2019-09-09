package com.maywide.biz.inter.pojo.bizfeedback;

import java.io.Serializable;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizFeedbackInterReq extends BaseApiRequest implements Serializable {

	//boss接口所需参数
	private String name;//姓名
	private String phone;//电话 
	private String email;//电子邮件
	private String content;//反馈内容  
	
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
	
	

}
