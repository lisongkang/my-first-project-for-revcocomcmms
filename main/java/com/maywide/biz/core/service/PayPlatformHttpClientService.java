package com.maywide.biz.core.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.jsonplugin.JSONException;
import com.googlecode.jsonplugin.JSONUtil;
import com.maywide.biz.core.entity.RemotecallLog;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.api.BaseApiRequest;
import com.maywide.biz.core.pojo.platform.PlatformBO;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.core.cons.SysConstant;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.remote.BossHttpClientImpl;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.CheckUtils;
import com.maywide.core.util.DateUtils;
import com.maywide.payplat.security.SecureLink;

@Service
@Transactional(rollbackFor = Exception.class)
public class PayPlatformHttpClientService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PersistentService dao;
	
	public String requestPlatformPost(String bizorderid,String servicecode,String request) throws Exception{
		
		PostMethod postMethod = this.getPostMethod(servicecode, request);
		RemotecallLog retmotecallLog = BossHttpClientImpl
				.requestPost(postMethod);
		
		retmotecallLog.setOrderid(Long.valueOf(bizorderid));
		
		dao.save(retmotecallLog);
		
		return retmotecallLog.getResponse();
	}
	
	public PostMethod getPostMethod(String servicecode, String request) throws Exception{
		
		PostMethod postMethod = new PostMethod(SysConfig.getPlatform_url());
		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,
				SysConfig.getHttpTimeout());
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(1, false));
		postMethod.addParameter("clientcode", SysConfig.getPlatform_code());
		postMethod.addParameter("clientpwd", SysConfig.getPlatform_pwd());
		
		String requestid = getRequestId4Platform();
		postMethod.addParameter("requestid", requestid);
		logger.error("pay refund requestid:"+requestid);
		postMethod.addParameter("servicecode", servicecode);
		logger.error("pay refund servicecode:"+servicecode);
		postMethod.addParameter("requestContent", request);
		logger.error("pay refund requestContent:"+request);
		String dataSign = getDatasign4Plarform(getRequstBody(servicecode,request,requestid));
		postMethod.addParameter("dataSign", dataSign);
		logger.error("pay refund dataSign:"+dataSign);
		return postMethod;
	}
	
	private PlatformBO getRequstBody(String serviceCode,String requst,String requestid) throws JSONException{
		PlatformBO bo = new PlatformBO();
		bo.setClientcode(SysConfig.getPlatform_code());
		bo.setClientpwd(SysConfig.getPlatform_pwd());
		bo.setServicecode(serviceCode);
		bo.setVersion(SysConfig.getPlatform_version());
		bo.setRequestid(requestid);
		bo.setRequestContent(requst);
		return bo;
	}
	
	private String getRequestId4Platform(){
		return SysConfig.getPlatform_code()+DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYYMMDD) +UUID.randomUUID().toString().substring(0, 8);
	}
	
	private String getDatasign4Plarform(PlatformBO bo){
		String dataSign = "";
		Map<String, String> map = new HashMap<String, String>();
		map.put("servicecode", bo.getServicecode());
		map.put("clientcode", bo.getClientcode());
		logger.error("pay refund clientcode:"+bo.getClientcode());
		map.put("clientpwd", bo.getClientpwd());
		logger.error("pay refund clientpwd:"+bo.getClientpwd());
		map.put("requestid", bo.getRequestid());
		map.put("requestContent", bo.getRequestContent());
		try {
			logger.error("pay refund md5Key:"+SysConfig.getPlatform_MD5());
			dataSign = SecureLink.sign(map, SysConfig.getPlatform_MD5());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataSign;
	}

}
