package com.maywide.biz.inter.pojo.authrandomcode;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class AuthRandomCodeInterReq extends BaseApiRequest implements
		java.io.Serializable {
    private String phoneno;
    private String randomcode;

    public String getPhoneno() {
        return phoneno;
    }
    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
    public String getRandomcode() {
        return randomcode;
    }
    public void setRandomcode(String randomcode) {
        this.randomcode = randomcode;
    }
	
}
