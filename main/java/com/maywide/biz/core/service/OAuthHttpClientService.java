package com.maywide.biz.core.service;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Service;

import com.googlecode.jsonplugin.JSONUtil;
import com.maywide.biz.core.entity.RemotecallLog;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.core.security.encrypt.ISecurityService;
import com.maywide.core.security.encrypt.SecurityFactory;
import com.maywide.core.security.remote.OAuthHttpClientImpl;
import com.maywide.core.util.CheckUtils;

@Service
public class OAuthHttpClientService {

	private ISecurityService securityService;

	public String requestPost(String servName, Object params) throws Exception {
		PostMethod postMethod = this.getPostMethod(servName, params);
		RemotecallLog retmotecallLog = OAuthHttpClientImpl.requestPost(postMethod);
		return retmotecallLog.getResponse();
	}

	private PostMethod getPostMethod(String servName, Object params) throws Exception {
		CheckUtils.checkEmpty(SysConfig.getOAuthUrl(), "没有配置OAUTH_URL接口地址");
		PostMethod postMethod = new PostMethod(SysConfig.getOAuthUrl());
		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, SysConfig.getHttpTimeout());
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(1, false));
		JSONObject json = new JSONObject();
		json.put("api", servName);
		if (params != null) {
			String jsonStr = JSONUtil.serialize(params, null, null, true, true);
			if (securityService == null) {
				securityService = SecurityFactory.getSecurityService();
			}
			json.put("requestBody", securityService.encrypt(jsonStr));
		}
		StringRequestEntity entity = new StringRequestEntity(json.toString());
		postMethod.setRequestEntity(entity);

		return postMethod;
	}

}
