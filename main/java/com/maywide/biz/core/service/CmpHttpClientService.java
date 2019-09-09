package com.maywide.biz.core.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.core.entity.RemotecallLog;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.PrvSysparamParam;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.security.remote.CmpHttpClientImpl;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.CheckUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class CmpHttpClientService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private PersistentService dao;
	
	public String requestPost(String bizorderid,String servName,Map<String, String> params) throws Exception{
		CheckUtils.checkEmpty(bizorderid, "访问远程服务：业务订单号不能为空");
		
		PostMethod postMethod = this.getPostMethod(servName, params);

		RemotecallLog retmotecallLog = CmpHttpClientImpl.requestPost(postMethod);
		
		retmotecallLog.setOrderid(Long.valueOf(bizorderid));
		logRemotecall(retmotecallLog);

		return retmotecallLog.getResponse();
		
	}
	
	private PostMethod getPostMethod(String servName,Map<String, String> params) throws Exception{

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(SysConfig.getCmpApiUrl(), "没有配置CMP_API接口地址");
		PostMethod postMethod = new PostMethod(SysConfig.getCmpApiUrl() + servName);
		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, SysConfig.getHttpTimeout());
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(1, false));
		postMethod.addParameter("key", getCMPKey4DB());
		for(String key:params.keySet()){
			if (params.get(key) != null) {
				postMethod.addParameter(key, params.get(key));
			}
		}
		postMethod.addParameter("operid", String.valueOf(loginInfo.getOperid()));
		postMethod.addParameter("orgid", String.valueOf(loginInfo.getDeptid()));
		return postMethod;
	}
	
	private String getCMPKey4DB() throws Exception{
		PrvSysparam param = new PrvSysparam();
		param.setGcode("CMP_URL_KEY");
		List<PrvSysparam> datas = dao.find(param, false);
		if(datas == null || datas.isEmpty()){
			CheckUtils.checkNull(null, "未配置cmp系统访问密钥");
		}
		return datas.get(0).getData();
	}

	public String requestPost(String bizorderid, String path, List<NameValuePair> params) throws Exception {
		// bizorderid,biz_trace表中的orderid，实际上对应biz_custorder表的id,在这里相当于业务流水号
		CheckUtils.checkEmpty(bizorderid, "访问远程服务：业务订单号不能为空");

		PostMethod postMethod = this.getPostMethod(path, params);

		RemotecallLog retmotecallLog = CmpHttpClientImpl.requestPost(postMethod);

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

	public PostMethod getPostMethod(String path, List<NameValuePair> params) throws Exception {
		CheckUtils.checkEmpty(SysConfig.getCmpUrl(), "没有配置CMP接口地址");
		PostMethod postMethod = new PostMethod(SysConfig.getCmpUrl() + path);

		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, SysConfig.getHttpTimeout());
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(1, false));

		postMethod.addParameters(params.toArray(new NameValuePair[0]));

		return postMethod;
	}
	

}
