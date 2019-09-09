package com.maywide.biz.core.service;

import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.core.entity.RemotecallLog;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.core.security.remote.McrHttpClientImpl;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.CheckUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class McrHttpClientService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private PersistentService dao;

	public String requestPost(String bizorderid, String path, Map<String, String> params) throws Exception {
		CheckUtils.checkEmpty(bizorderid, "访问远程服务：业务订单号不能为空");

		PostMethod postMethod = this.getPostMethod(path, params);

		RemotecallLog retmotecallLog = McrHttpClientImpl.requestPost(postMethod);

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

	public PostMethod getPostMethod(String path, Map<String, String> params) throws Exception {
		PostMethod postMethod = new PostMethod(SysConfig.getMcrUrl() + path);

		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, SysConfig.getHttpTimeout());
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(1, false));

		postMethod.addParameter("clientcode", SysConfig.getClientcode());
		postMethod.addParameter("clientpwd", SysConfig.getClientpwd());
		for (String key : params.keySet()) {
			if (params.get(key) != null) {
				postMethod.addParameter(key, params.get(key));
			}
		}
		return postMethod;
	}

}
