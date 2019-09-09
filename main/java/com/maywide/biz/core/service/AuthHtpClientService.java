package com.maywide.biz.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.core.entity.RemotecallLog;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.core.security.remote.AuthHttpClientImpl;
import com.maywide.core.security.remote.BossHttpClientImpl;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.CheckUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class AuthHtpClientService {
	
	@Autowired
	private PersistentService dao;
	
	/**
	 *请求后台开发组接口
	 * @param loginInfo
	 * @param bizOrderid
	 * @param serviceName
	 * @param params
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public String requestPost(LoginInfo loginInfo,String bizOrderid,String serviceName,Object params) throws NumberFormatException, Exception{
		String requestid = BossHttpClientImpl.createRequestid(loginInfo
				.getLoginname());
		return requestPost(loginInfo.getCity(), requestid,Long.parseLong(bizOrderid), serviceName, params);
	}

	public String requestPost(String city,String requestid,Long orderid,String serviceName,Object params) throws Exception{
		CheckUtils.checkEmpty(serviceName, "请求方法名不能为空");
		String authUrl = SysConfig.getAuthUrl();
		CheckUtils.checkEmpty(authUrl, "请求地址不能为空");
		String url = getRequestUrl(serviceName, authUrl);
		RemotecallLog remotecallLog = AuthHttpClientImpl.requestPost(city,requestid,orderid,serviceName,url, params);
		dao.save(remotecallLog);
		return remotecallLog.getResponse();
	}
	
	private String getRequestUrl(String serviceName,String authUrl){
		String url = authUrl+"?method="+serviceName;
		return url;
	}

	
}
