package com.maywide.biz.core.service;

import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.core.entity.RemotecallLog;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.core.security.remote.McrHttpClientImpl;
import com.maywide.core.security.remote.OssHttpClientImpl;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.CheckUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class OssHttpClientService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private PersistentService dao;

	public String requestPost(String bizorderid, String path, String content) throws Exception {
		CheckUtils.checkEmpty(bizorderid, "访问远程服务：业务订单号不能为空");

		PostMethod postMethod = this.getPostMethod(path, content);

		RemotecallLog retmotecallLog = OssHttpClientImpl.requestPost(postMethod,content);

		// 记录请求日志
		retmotecallLog.setOrderid(Long.valueOf(bizorderid));
		logRemotecall(retmotecallLog);

		return retmotecallLog.getResponse();
	}

	private void logRemotecall(RemotecallLog remotecallLog) throws Exception {
		try {
			dao.save(remotecallLog);
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
	
	public PostMethod getPostMethod(String path, String content) throws Exception {
		CheckUtils.checkEmpty(SysConfig.getOsshttp_url(), "没有配置OSSHTTP_URL接口地址");
		PostMethod postMethod = new PostMethod(SysConfig.getOsshttp_url() + path);
//		PostMethod postMethod = new PostMethod("http://10.205.29.222:8010/" + path);
//		PostMethod postMethod = new PostMethod("http://10.205.22.181:8010/" + path);
//		postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		postMethod.addRequestHeader("Access-App-Code", "cmms");
		postMethod.addRequestHeader("content-type", "application/json");
		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, SysConfig.getHttpTimeout());
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(1, false));
		
		 RequestEntity se = new StringRequestEntity (content ,"application/json" ,"UTF-8");

		    postMethod.setRequestEntity(se);

//		postMethod.addParameter("clientcode", SysConfig.getClientcode());
//		postMethod.addParameter("clientpwd", SysConfig.getClientpwd());
//		for (String key : params.keySet()) {
//			if (params.get(key) != null) {
//				postMethod.addParameter(key, params.get(key));
//			}
//		}
		return postMethod;
	}

}
