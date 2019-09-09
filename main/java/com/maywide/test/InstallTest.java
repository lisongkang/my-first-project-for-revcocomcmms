package com.maywide.test;

import org.codehaus.jettison.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maywide.biz.core.pojo.RequestWrapper;
import com.maywide.core.security.remote.HttpClientImpl;
import com.maywide.core.security.remote.RemoteCall;

public class InstallTest {
	public static void main(String[] args) {
		try {
			ObjectMapper jsonMapper = null;
			jsonMapper = new ObjectMapper();
            jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            
			RemoteCall remoteCall = new RemoteCall();
	        remoteCall.setProtocol("HTTP");
	        //remoteCall.setRemoteIP("10.205.28.102");
	        //remoteCall.setRemotePort(8082);
	        remoteCall.setRemoteIP("127.0.0.1");
	        remoteCall.setRemotePort(8180);
	        remoteCall.setCallUrl("/moss-web/api");
	        
	        /*JSONObject jsonObject = new JSONObject(); 
	        jsonObject.put("api", "getInstallBasedata");
	        JSONObject bodyObj = new JSONObject(); 
	        bodyObj.put("city", "GZ");
	        //bodyObj.put("mcode", "0");
	        jsonObject.put("requestBody", bodyObj.toString());
	        //jsonObject.put("requestBody", "\"SYS_ACCT_TYPE\"");
	        
	        System.out.println(jsonObject);
	        
	        RequestWrapper requestWrapper = new RequestWrapper();
            requestWrapper = jsonMapper.readValue(jsonObject.toString(), RequestWrapper.class);
	        String response = HttpClientImpl.call(remoteCall, jsonObject.toString());
	        System.out.println(response);*/
	        
	        /*JSONObject jsonObject = new JSONObject(); 
	        jsonObject.put("api", "queryResHouse");
	        JSONObject bodyObj = new JSONObject(); 
	        bodyObj.put("addr", "星汇园");
	        bodyObj.put("city", "GZ");
	        jsonObject.put("requestBody", bodyObj.toString());
	        //jsonObject.put("requestBody", "\"SYS_ACCT_TYPE\"");
	        
	        System.out.println(jsonObject);
	        
	        RequestWrapper requestWrapper = new RequestWrapper();
            requestWrapper = jsonMapper.readValue(jsonObject.toString(), RequestWrapper.class);
	        String response = HttpClientImpl.call(remoteCall, jsonObject.toString());
	        System.out.println(response);*/
	        
	        /* --测试查询产目录
	        JSONObject jsonObject = new JSONObject(); 
	        jsonObject.put("api", "queryCatalog");
	        JSONObject bodyObj = new JSONObject(); 
	        jsonObject.put("requestBody", bodyObj.toString());
	        //jsonObject.put("requestBody", "\"SYS_ACCT_TYPE\"");
	        
	        System.out.println(jsonObject);
	        
	        RequestWrapper requestWrapper = new RequestWrapper();
            requestWrapper = jsonMapper.readValue(jsonObject.toString(), RequestWrapper.class);
	        String response = HttpClientImpl.call(remoteCall, jsonObject.toString());
	        System.out.println(response);
	        
	        */
	        
	        
	        /*JSONObject jsonObject = new JSONObject(); 
	        jsonObject.put("api", "querySalespkgKnow");
	        JSONObject bodyObj = new JSONObject(); 
	        bodyObj.put("catalogid", 2);
	        jsonObject.put("requestBody", bodyObj.toString());
	        //jsonObject.put("requestBody", "\"SYS_ACCT_TYPE\"");
	        
	        System.out.println(jsonObject);
	        
	        RequestWrapper requestWrapper = new RequestWrapper();
            requestWrapper = jsonMapper.readValue(jsonObject.toString(), RequestWrapper.class);
	        String response = HttpClientImpl.call(remoteCall, jsonObject.toString());
	        System.out.println(response);*/
	        
	        /*JSONObject jsonObject = new JSONObject(); 
	        jsonObject.put("api", "queryCustInfo");
	        JSONObject bodyObj = new JSONObject(); 
	        bodyObj.put("identifierType", "1");
	        bodyObj.put("identifier", "13800138000");
	        bodyObj.put("city", "GZ");
	        jsonObject.put("requestBody", bodyObj.toString());
	        //jsonObject.put("requestBody", "\"SYS_ACCT_TYPE\"");
	        
	        System.out.println(jsonObject);
	        
	        RequestWrapper requestWrapper = new RequestWrapper();
            requestWrapper = jsonMapper.readValue(jsonObject.toString(), RequestWrapper.class);
	        String response = HttpClientImpl.call(remoteCall, jsonObject.toString());
	        System.out.println(response);*/
	        
	        /*JSONObject jsonObject = new JSONObject(); 
	        jsonObject.put("api", "queryUserInfo");
	        JSONObject bodyObj = new JSONObject(); 
	        bodyObj.put("icno", "8002003224584610");
	        jsonObject.put("requestBody", bodyObj.toString());
	        //jsonObject.put("requestBody", "\"SYS_ACCT_TYPE\"");
	        
	        System.out.println(jsonObject);
	        
	        RequestWrapper requestWrapper = new RequestWrapper();
            requestWrapper = jsonMapper.readValue(jsonObject.toString(), RequestWrapper.class);
	        String response = HttpClientImpl.call(remoteCall, jsonObject.toString());
	        System.out.println(response);*/
	        
	        /*JSONObject jsonObject = new JSONObject(); 
	        jsonObject.put("api", "applyInstall");
	        JSONObject bodyObj = new JSONObject(); 
	        bodyObj.put("custid", 3600041634L);
	        bodyObj.put("name", "罗华荣");
	        bodyObj.put("cardtype", "1");
	        bodyObj.put("cardno", "8002003224584610");
	        bodyObj.put("linkphone", "13888888888");
	        bodyObj.put("areaid", 188188);
	        bodyObj.put("houseid", 201);
	        bodyObj.put("patchid", 20);
	        bodyObj.put("whladdr", "天河路140号703房");
	        bodyObj.put("knowids", "1,2,3");
	        bodyObj.put("descrip", "业务说明");
	        bodyObj.put("logicdevno", "8002003224584610");
	        bodyObj.put("stbno", "800200");
	        bodyObj.put("bankcode", "A");
	        bodyObj.put("acctkind", "0");
	        bodyObj.put("acctno", "476666666666666");
	        bodyObj.put("accttype", "0");
	        bodyObj.put("acctname", "罗华荣");
	        jsonObject.put("requestBody", bodyObj.toString());
	        //jsonObject.put("requestBody", "\"SYS_ACCT_TYPE\"");
	        
	        System.out.println(jsonObject);
	        
	        RequestWrapper requestWrapper = new RequestWrapper();
            requestWrapper = jsonMapper.readValue(jsonObject.toString(), RequestWrapper.class);
	        String response = HttpClientImpl.call(remoteCall, jsonObject.toString());
	        System.out.println(response);*/
	        
	        
	        
	        
	        JSONObject jsonObject = new JSONObject(); 
	        jsonObject.put("api", "queryDepartment");
	        JSONObject bodyObj = new JSONObject(); 
	        bodyObj.put("loginname", "GZCYKFA001");
	        jsonObject.put("requestBody", bodyObj.toString());
	        //jsonObject.put("requestBody", "\"SYS_ACCT_TYPE\"");
	        
	        System.out.println(jsonObject);
	        
	        RequestWrapper requestWrapper = new RequestWrapper();
            requestWrapper = jsonMapper.readValue(jsonObject.toString(), RequestWrapper.class);
	        String response = HttpClientImpl.call(remoteCall, jsonObject.toString());
	        System.out.println(response);
	        
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
