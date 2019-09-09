package com.maywide.biz.mcr.web.action;

import java.util.Date;

import net.sf.json.JSON;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.HttpHeaders;
import org.codehaus.jettison.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maywide.core.mcr.AuthInfor;
import com.maywide.core.mcr.MCRConstants;
import com.maywide.core.util.MicroUtil;
import com.maywide.core.util.PropertyUtil;
import com.maywide.core.web.SimpleController;

public class UrlTokenController extends SimpleController{
//http://210.21.65.87/mcrweb/MicroCoreServlet
//appID:wx9bc05e1ce7a95e43
//appsecret:d6316a72ac74ef6705e86e0f695f17b5
//Token:mayWideMicro
//https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9bc05e1ce7a95e43&redirect_uri=http%3a%2f%2f210.21.65.87%2fmicroweb%2findex.html&response_type=code&scope=snsapi_base&state=1#wechat_redirect
//通过code换去网页授权：https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx9bc05e1ce7a95e43&secret=d6316a72ac74ef6705e86e0f695f17b5&code=CODE&grant_type=authorization_code

	private AuthInfor authentity;
	protected Boolean rtval = false;
	protected String msg = "";
	public static final String JSON = "jsonret";
	
	public HttpHeaders authinfo2() {
		try{
			authentity = new AuthInfor();
			authentity.setCode(getRequest().getParameter("code"));
			authentity.setState(getRequest().getParameter("state"));
			authentity.setPublishid(PropertyUtil.getValueFromProperites("application", "weixin.publishid"));
			authentity = queryToken(authentity);
			
			/*AuthInfor authInfor = (AuthInfor)getRequest().getSession().getAttribute(MCRConstants.SESSION_AUTHINFO);
			if(authInfor == null && authentity == null){//authentity为空说明是从网页进来的
				setModel(false);
			}else if(authInfor == null  && authentity!=null){
				queryToken(authentity);
				setRtval(true);
			}else if(authInfor!=null && authentity!=null){
				Date crdate = new Date();
				Date lstdate = authInfor.getCrdate();
				long a = crdate.getTime();
				long b = lstdate.getTime();
				int c = (int)((a - b) / 1000);
				if(c>=5*60){//大于5分钟的时候重新刷新
					queryToken(authInfor);
					setRtval(true);
				}else{
					setRtval(true);
				}
			}else{//下面这一步只能说微信公共平台与微厅的跳转是安全的，但不代表账号已绑定  
				setRtval(true);
			}*/
		}catch(Exception e){
			e.printStackTrace();
			setMsg(e.getMessage());
		}
		
		
		setModel(authentity.getOpenid());
		return buildDefaultHttpHeaders();
	}
	
	public String authinfo() throws Exception {//验证用户信息并保存至session
		
		try{
			AuthInfor authInfor = (AuthInfor)getRequest().getSession().getAttribute(MCRConstants.SESSION_AUTHINFO);
			if(authInfor == null && authentity == null){//authentity为空说明是从网页进来的
				setRtval(false);
			}else if(authInfor == null  && authentity!=null){
				queryToken(authentity);
				setRtval(true);
			}else if(authInfor!=null && authentity!=null){
				Date crdate = new Date();
				Date lstdate = authInfor.getCrdate();
				long a = crdate.getTime();
				long b = lstdate.getTime();
				int c = (int)((a - b) / 1000);
				if(c>=5*60){//大于5分钟的时候重新刷新
					queryToken(authInfor);
					setRtval(true);
				}else{
					setRtval(true);
				}
			}else{//下面这一步只能说微信公共平台与微厅的跳转是安全的，但不代表账号已绑定  
				setRtval(true);
			}
		}catch(Exception e){
			e.printStackTrace();
			setMsg(e.getMessage());
		}
		
		authentity = (AuthInfor)getRequest().getSession().getAttribute(MCRConstants.SESSION_AUTHINFO);
		return JSON;
	}

	private AuthInfor queryToken(AuthInfor authInfor) throws Exception{
		
		String requestUrl = MicroUtil.getAuthAcessTokenUrl(authInfor);
		JSONObject jsonObject = MicroUtil.httpRequest(requestUrl, "GET", null);
		ObjectMapper jsonMapper = new ObjectMapper();
		jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		
		AuthInfor authobj = jsonMapper.readValue(jsonObject.toString(),
				AuthInfor.class);
		if (authobj != null){
			if (authobj.getOpenid() != null && !"".equals(authobj.getOpenid())) {
				authobj.setCrdate(new Date());
				authobj.setPublishid(authInfor.getPublishid());
				authobj.setState(authInfor.getState());
				authobj.setCode(authInfor.getCode());
			}
		}

		return authobj;
	}
	public AuthInfor getAuthentity() {
		return authentity;
	}

	public void setAuthentity(AuthInfor authentity) {
		this.authentity = authentity;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setRtval(Boolean rtval) {
		this.rtval = rtval;
	}

	public Boolean getRtval() {
		return rtval;
	}
	
}
