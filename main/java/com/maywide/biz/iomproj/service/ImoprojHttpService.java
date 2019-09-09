package com.maywide.biz.iomproj.service;


import java.net.URL;

import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

@org.springframework.stereotype.Service
public class ImoprojHttpService {
	
	public String getResponeStr(String name,String url,Integer timeout,String jsonStr,String paramsName) throws Exception{
		Call call = getRequestCall(name, url, timeout, paramsName);
		return (String) call.invoke(new Object[]{jsonStr});
	}

	private Call getRequestCall(String name,String url,Integer timeout,String paramsName) throws Exception{
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new URL(url));
		call.setOperationName(name);
		call.addParameter(paramsName,XMLType.XSD_STRING, ParameterMode.IN);//参数
	     call.setOperationStyle(org.apache.axis.constants.Style.RPC);
		 call.setOperationUse(org.apache.axis.constants.Use.LITERAL);
		 call.setReturnType(XMLType.XSD_STRING);
		 call.setTimeout(timeout);
		 return call;
	}
	
}
