package com.maywide.biz.inter.service;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.entity.RemotecallLog;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.biz.inter.pojo.portal.PortalResp;
import com.maywide.biz.inter.pojo.portal.PortalUser;
import com.maywide.biz.inter.pojo.portal.PortalUserList;
import com.maywide.biz.inter.pojo.portal.QueryPortalUserReq;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.remote.BossHttpClientImpl;
import com.maywide.core.util.CheckUtils;

@Service
public class PortalService {
	protected final static Logger log = LoggerFactory.getLogger(PortalService.class);
	
	@Autowired
	private ParamService paramService;
	private static String portalUrl = "";
	
	public  String portalUrl() throws Exception{
		if(portalUrl.equals("")){
			PrvSysparam prvSysparam = paramService.getData("WEBSERVICES", "APP_PORTAL_URL");
			if(prvSysparam!=null&&prvSysparam.getData()!=null){
				portalUrl = prvSysparam.getData();
			}
		}
		return portalUrl ;
	}
	
	public  RemotecallLog getPost(String api,Object requestBody) throws Exception{
			
			CheckUtils.checkNull(api,"调用接口方法不能为空");
			
			JSONObject request = new JSONObject(); 
			RemotecallLog retRemotecallLog = new RemotecallLog();
			request.put("api",api);
			request.put("requestBody",new Gson().toJson(requestBody).toString());
			
			String url = portalUrl();
			CheckUtils.checkNull(url,"Portal接口路径不能为空");
			PostMethod method = new PostMethod(portalUrl());
			method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, SysConfig.getHttpTimeout());
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler(1, false));
			
			RequestEntity entity = new StringRequestEntity(request.toString(),"application/json","UTF-8");
	        method.setRequestEntity(entity);
	         
	        retRemotecallLog=  BossHttpClientImpl.requestPost(method);
	        retRemotecallLog.setServicecode(api);
	        retRemotecallLog.setRequest(request.toString());
	        return retRemotecallLog;
	}
	
	public  String requestPost(String api,Object requestBody) throws Exception{
		String response = "";
		RemotecallLog remotecallLog = getPost(api, requestBody);
		response = remotecallLog.getResponse();
		//解析 
		if (StringUtils.isEmpty(response)) {
	         throw new BusinessException("Portal接口调用出错，没有返回数据");
	    }
		if(StringUtils.isNotBlank(response)){
	        	log.info("=========>response:"+response+"<=========");
	    } 
		
        ObjectMapper jsonMapper = new ObjectMapper();

        PortalResp portalResp= jsonMapper.readValue(response, PortalResp.class);
        
        ReturnInfo returnInfo = portalResp.getReturnInfo();
        if(returnInfo != null &&returnInfo.getCode()!=IErrorDefConstant.ERROR_SUCCESS_CODE){
        	 throw new BusinessException(returnInfo.getMessage());
        }else{
        	response = portalResp.getResponseBody();
        }
        
       
		return response;
	}
	
	public  PortalUser  queryPortalUserByloginname(String loginname) throws Exception{
		CheckUtils.checkNull(loginname, "BOSS工号不能为空");
		PortalUser portalUser = new PortalUser();
		
		QueryPortalUserReq req = new QueryPortalUserReq();
		req.setClientid("4");//默认
		req.setUserid(loginname);
		String users = requestPost(BizConstant.PortalInterfaceService.QUERYPORTALUSER, req);
	    ObjectMapper objectMapper = new ObjectMapper();
       
	    PortalUserList list = objectMapper.readValue(users, PortalUserList.class);
		
	    if(list.getUsers().size()>0){
	    	return list.getUsers().get(0);
	    }
	    return portalUser;
	}
	
	public static void main(String[] args) throws Exception {
		QueryPortalUserReq req = new QueryPortalUserReq();
		req.setClientid("4");
		req.setUserid("fsgjbcyt");
		
		
		PortalUser user = new PortalUser();
		user.setId(2714);
		user.setClient_id("4");
		user.setOperid("18688271116");
		user.setStatus("1");
		user.setUserid("123131211");
	}
			
}
