package com.maywide.biz.dl.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.hibernate.envers.Audited;

import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.security.remote.BossHttpClientImpl;

public class ProxyServlet extends HttpServlet {
	
	@Audited
	private ParamService paramService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try{
			String markNo = req.getParameter("markNo");
			String url = "http://10.129.10.150:18080/bdinterface/custportray/getCustPortray";
			
			HttpClient httpClient = new HttpClient();
			Map<String, String> params = new HashMap<String, String>();
			params.put("markNo", markNo);
			GetMethod getMethod = getPostMethod(url, params);
			httpClient.getParams().setContentCharset("UTF-8");
			int code = httpClient.executeMethod(getMethod);
			String responseStr = getMethod.getResponseBodyAsString().trim();
			responseStr = responseStr.replace("../", "http://222.161.198.187:18080/bdinterface/");
			resp.getWriter().write(responseStr);
		}catch(Exception e){
			resp.getWriter().write(404);
		}
	}
	
	private GetMethod getPostMethod(String url,Map<String, String> params){
		if (url.indexOf("?") == -1) {
			url += "?";
		} else {
			url += "&";
		}
		url += BossHttpClientImpl.encodeParameters(params, "UTF-8");
		GetMethod getMethod = new GetMethod(url);
		return getMethod;
	}

	
}
