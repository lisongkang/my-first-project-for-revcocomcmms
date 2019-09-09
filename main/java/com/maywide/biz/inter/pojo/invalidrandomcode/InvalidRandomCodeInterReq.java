package com.maywide.biz.inter.pojo.invalidrandomcode;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class InvalidRandomCodeInterReq extends BaseApiRequest implements
		java.io.Serializable {
    private String phoneno;

    public String getPhoneno() {
        return phoneno;
    }
    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
	
}
