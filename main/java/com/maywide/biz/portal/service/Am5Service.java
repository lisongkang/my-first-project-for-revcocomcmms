package com.maywide.biz.portal.service;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.springframework.stereotype.Service;

import com.isprint.am.xmlrpc.api.API;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.biz.portal.pojo.Am5Result;
import com.maywide.core.exception.BusinessException;

@Service
public class Am5Service {

	private static final String REALM_ID = "SystemRealm";
	private static final String LOGIN_MODULE = "SystemPasswordBasic";
	private static final String USER_STORE = "SafeID";

	private API.Proxy getProxy() throws MalformedURLException {
		return API.ProxyFactory.registerFactory("MyFactory", new String[] { SysConfig.getAm5RpcUrl() }, 30)
				.createProxy();
	}

	public Am5Result resetPassword(String userId, String newPwd) throws BusinessException {
		Am5Result result = new Am5Result();
		try {
			API.Proxy proxy = getProxy();
			Map<String, String> params = new HashMap<String, String>();
			params.put("realmId", REALM_ID);
			proxy.getPasswordSvc().resetPassword("", userId, USER_STORE, LOGIN_MODULE, newPwd, params);
			result.msg = "密码重置成功";
		} catch (XmlRpcException e) {
			handleRpcException(e, result);
		} catch (Exception e) {
			e.printStackTrace();
			result.setUnkownError();
		}
		return result;
	}

	public Am5Result verifyPassword(String userId, String pwd) {
		Am5Result result = new Am5Result();
		try {
			API.Proxy proxy = getProxy();
			Map<String, String> params = new HashMap<String, String>();
			params.put("realmId", REALM_ID);
			proxy.getPasswordSvc().verifyPassword("", userId, USER_STORE, LOGIN_MODULE, pwd, params);
			result.msg = "密码校验成功";
		} catch (XmlRpcException e) {
			handleRpcException(e, result);
		} catch (Exception e) {
			e.printStackTrace();
			result.setUnkownError();
		}
		return result;
	}

	private void handleRpcException(XmlRpcException e, Am5Result result) {
		e.printStackTrace();
		String msg = null;
		switch (e.code) {
		case 10001:
			msg = "用户名不存在！";
			break;
		case 10020:
			msg = "密码不正确！";
			break;
		case 10403:
			msg = "账号已锁定，请联系管理员解锁！";
			break;
		}
		if (msg != null) {
			result.code = e.code;
			result.msg = msg;
		} else {
			result.setUnkownError();
		}
	}

}
