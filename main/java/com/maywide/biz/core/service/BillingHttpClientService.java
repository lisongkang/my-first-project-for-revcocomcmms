package com.maywide.biz.core.service;

import com.maywide.biz.core.entity.RemotecallLog;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.security.remote.AuthHttpClientImpl;
import com.maywide.core.security.remote.BillingHttpClientImpl;
import com.maywide.core.security.remote.BossHttpClientImpl;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.CheckUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class BillingHttpClientService {
	
	@Autowired
	private PersistentService dao;
	@Autowired
	private ParamService paramService;
	/**
	 *请求后台开发组接口
	 * @param loginInfo
	 * @param serviceName
	 * @param params
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public String requestPost(LoginInfo loginInfo,String serviceName,Map params) throws NumberFormatException, Exception{
		String requestid = BossHttpClientImpl.createRequestid(loginInfo
				.getLoginname());
		return requestPost(loginInfo.getCity(), requestid, serviceName, params);
	}

	public String requestPost(String city,String requestid,String serviceName,Map params) throws Exception{

		String url = getRequestUrl(serviceName);
		RemotecallLog remotecallLog = BillingHttpClientImpl.getContentPostByJson(url, params);
		remotecallLog.setClientcode(city);
		remotecallLog.setRequestid(requestid);
		remotecallLog.setServicecode(serviceName);
//		String bizOrderid = dao.getSequence("SEQ_BIZ_CUSTORDER_ID").toString();
		remotecallLog.setOrderid(System.currentTimeMillis());
		logRemotecall(remotecallLog);
		return remotecallLog.getResponse();
	}
	
	private String getRequestUrl(String serviceName) throws Exception{
		String url = null;
		//通过字典表获取接口地址，如果字典表没有配置则通过配置文件获取接口地址
		PrvSysparam param = paramService.getData("BILLING","API_BILLING_URL");
		if(param!=null && StringUtils.isNotEmpty(param.getData())){
			url=param.getData()+"/"+serviceName;
		}
		CheckUtils.checkEmpty(url,"billing接口地址暂无配置!");
		return url;
	}

	private void logRemotecall(RemotecallLog remotecallLog){
		// 记录请求日志
		try {
			dao.save(remotecallLog);
		} catch (Exception e) {
			e.getStackTrace();
			//System.out.print(e.getStackTrace());
		}
	}
}
