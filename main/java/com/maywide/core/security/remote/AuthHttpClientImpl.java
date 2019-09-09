package com.maywide.core.security.remote;

import java.util.Date;

import org.apache.commons.httpclient.URIException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.googlecode.jsonplugin.JSONException;
import com.googlecode.jsonplugin.JSONUtil;
import com.maywide.biz.core.entity.RemotecallLog;
import com.maywide.core.util.CheckUtils;

public class AuthHttpClientImpl {
	
	public static RemotecallLog requestPost(String city,String requestid,Long orderid,String servicecode,String url,Object params) throws Exception {
		CheckUtils.checkNull(orderid, "请求参数不能为空");
		CheckUtils.checkNull(params,"请求参数不能为空");
		
		/*构建请求的参数*/
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-type", "text/plain; charset=utf8"); 
		Gson gson = new Gson();
		String paramStr = gson.toJson(params);
		StringEntity entity = new StringEntity(paramStr, "UTF-8");
		httpPost.setEntity(entity);
		CloseableHttpResponse response = null;
		
		/*记录请求的参数*/
		RemotecallLog retRemotecallLog = new RemotecallLog();
		copyReqInfo2callLog(city,requestid,orderid,servicecode,url,retRemotecallLog,params);
		
		response = client.execute(httpPost);
		int httpCode = response.getStatusLine().getStatusCode();
		String retMsg = "";
		String status = "0";
		String responseStr = "";
		
		if(httpCode == HttpStatus.SC_OK){
			HttpEntity httpEntity = response.getEntity();
			responseStr = EntityUtils.toString(httpEntity, "UTF-8");
		}else if(httpCode == HttpStatus.SC_NOT_FOUND){
			status = "11";
			retMsg = "接口不存在404";
		}else if(httpCode == HttpStatus.SC_INTERNAL_SERVER_ERROR){
			status = "12";
			retMsg = "网络服务异常";
		}else{
			status = "13";
			retMsg = "接口调用失败:"+httpCode;
		}
		retRemotecallLog.setEndtime(new Date());
		retRemotecallLog.setRetcode(status);
		retRemotecallLog.setRetmsg(retMsg);
		retRemotecallLog.setResponse(responseStr);
		client.close();
		return retRemotecallLog;
	}
	
	
	private static void copyReqInfo2callLog(String city,String requestid,Long orderid,String servicecode,
			String url, RemotecallLog remotecallLog,Object params)
			throws URIException, JSONException {
		remotecallLog.setServiceurl(url);
		remotecallLog.setCalltime(new Date());
		remotecallLog.setProtocol("http");
		String request = JSONUtil.serialize(params);
		remotecallLog.setRequest(request);
		remotecallLog.setClientcode(city);
		remotecallLog.setOrderid(orderid);
		remotecallLog.setRequestid(requestid);
		remotecallLog.setServicecode(servicecode);
	}
	

}
