package com.maywide.core.security.remote;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.maywide.biz.core.entity.RemotecallLog;
import com.maywide.biz.core.pojo.bosshttpinf.BossHttpBaseRespBO;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.CheckUtils;
import com.maywide.core.util.DateTimeUtil;

/**
 * 实现HTTP+JSON远程接口调用
 */
public class BossHttpClientImpl {

	private static Logger log = LoggerFactory.getLogger(BossHttpClientImpl.class);

	/**
	 * 构造post方法实例--使用配置文件中工号调boss接口
	 * 
	 * @param bizorderid
	 * @param servicecode
	 * @param request
	 * @param remotecallLog
	 * @return
	 * @throws Exception
	 */
	public static PostMethod getPostMethod(String servicecode, String request) throws Exception {

		PostMethod postMethod = new PostMethod(SysConfig.getBossUrl());

		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, SysConfig.getHttpTimeout());
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(1, false));

		postMethod.addParameter("version", SysConfig.getParamVersion());
		postMethod.addParameter("clientcode", SysConfig.getParamClientCode());
		postMethod.addParameter("clientpwd", SysConfig.getClientPasswd());
		postMethod.addParameter("citycode", SysConfig.getCityCode());
		postMethod.addParameter("servicecode", servicecode);
		postMethod.addParameter("system", "GRID");
		String requestid = createRequestid(SysConfig.getParamClientCode());
		postMethod.addParameter("requestid", requestid);

		postMethod.addParameter("requestContent", request);
		// postMethod.addParameter("deptid", "188234");

		return postMethod;
	}

	/**
	 * 执行post请求，返回请求日志对象
	 * 
	 * @param servicecode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static RemotecallLog requestPost(String servicecode, String request) throws Exception {

		PostMethod postMethod = getPostMethod(servicecode, request);

		return requestPost(postMethod);
	}

	/**
	 * 执行get请求,返回请求日志对象
	 * 
	 * @param url
	 * @param request
	 * @return
	 * @throws URIException 
	 */
	public static RemotecallLog requestGet(String clientcode,String url, String request) throws URIException {
		GetMethod getMethod = null;
		try {
			getMethod = getGetMethod(url, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestGet(clientcode,getMethod);
	}

	public static RemotecallLog requestGet(String clientcode,String url, Map<String, String> params) throws URIException {
		if (url.indexOf("?") == -1) {
			url += "?";
		} else {
			url += "&";
		}
		url += encodeParameters(params, "UTF-8");
		GetMethod getMethod = new GetMethod(url);
		return requestGet(clientcode,getMethod);
	}

	public static String encodeParameters(Map<String, String> params, String paramsEncoding) {
		StringBuilder encodedParams = new StringBuilder();
		try {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				if (entry.getValue() != null) {

					encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
					encodedParams.append('=');
					encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
					encodedParams.append('&');
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodedParams.toString();
	}

	/**
	 * 构造get请求方式
	 * 
	 * @param url
	 * @param request
	 * @return
	 */
	public static GetMethod getGetMethod(String url, String request) {
		String urlStr = url + "?" + request;
		GetMethod getMethod = new GetMethod(url + "?" + request);
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, SysConfig.getHttpTimeout());
		return getMethod;
	}

	/**
	 * 执行get请求
	 * 
	 * @param getMethod
	 * @return
	 * @throws URIException 
	 */
	public static RemotecallLog requestGet(String clientcode,GetMethod getMethod) throws URIException {
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("UTF-8");

		RemotecallLog retRemotecallLog = new RemotecallLog();
		retRemotecallLog.setCalltime(new Date());
		retRemotecallLog.setClientcode(clientcode);
		retRemotecallLog.setServiceurl(getMethod.getPath());
		retRemotecallLog.setRequest(getMethod.getURI().toString());
		
		JSONObject jsonobj = null;
		String status = "0";
		String retmsg = "";
		String responseStr = "";

		try {
			int httpCode = httpClient.executeMethod(getMethod);
			log.info("=========>httpCode=" + httpCode);
			if (httpCode == 200) {
				// 5.读取内容
				responseStr = getMethod.getResponseBodyAsString().trim();
				// log.info("=========>" + getMethod.getParameter("servicecode")
				// + " responseStr=" + responseStr);

			} else if (httpCode == 404) {
				status = "11";
				retmsg = "接口不存在404";
			} else if (httpCode == 500) {
				status = "12";
				retmsg = "接口调用失败500";
			} else {
				status = "13";
				retmsg = "接口调用失败" + httpCode;
			}
			// 6.组装返回信息
			retRemotecallLog.setEndtime(new Date());
			retRemotecallLog.setRetcode(status);
			retRemotecallLog.setRetmsg(retmsg);
			retRemotecallLog.setResponse(responseStr);
		} catch (Exception e) {
			System.out.print(e.getStackTrace());
		} finally {
			// 6.组装返回信息
			retRemotecallLog.setEndtime(new Date());
			retRemotecallLog.setRetcode(status);
			retRemotecallLog.setRetmsg(retmsg);

			getMethod.releaseConnection();
		}

		return retRemotecallLog;
	}

	/**
	 * 执行post请求，返回请求日志对象
	 * 
	 * @param servicecode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static RemotecallLog requestPost(PostMethod postMethod) throws Exception {
		CheckUtils.checkNull(postMethod, "执行post请求:post方法实例不能为空");

		// 1.构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("UTF-8");

		// 2.记录RemotecallLog
		RemotecallLog retRemotecallLog = new RemotecallLog();
		retRemotecallLog.setCalltime(new Date());
		copyBossReqInfo2callLog(postMethod, retRemotecallLog);

		JSONObject jsonobj = null;
		String status = "0";
		String retmsg = "";
		String responseStr = "";
		try {
			// 3.登记请求日志
			// logRequestInfo(remotecallLog);

			// 4.调用远程服务
			int httpCode = httpClient.executeMethod(postMethod);
			log.info("=========>httpCode=" + httpCode);
			if (httpCode == 200) {
				// 5.读取内容
				responseStr = postMethod.getResponseBodyAsString().trim();
				log.info("=========>" + postMethod.getParameter("servicecode") + " responseStr=" + responseStr);

			} else if (httpCode == 404) {
				status = "11";
				retmsg = "接口不存在404";
			} else if (httpCode == 500) {
				status = "12";
				retmsg = "接口调用失败500";
			} else {
				status = "13";
				retmsg = "接口调用失败" + httpCode;
			}

			// 6.组装返回信息
			retRemotecallLog.setEndtime(new Date());
			retRemotecallLog.setRetcode(status);
			retRemotecallLog.setRetmsg(retmsg);
			retRemotecallLog.setResponse(responseStr);
			copyBossBaseRespInfo2callLog(responseStr, retRemotecallLog);

		} catch (Exception e) {
			System.out.print(e.getStackTrace());
		} finally {
			/*
			 * System.out.println(DateTimeUtil.formatDate(new Date(),
			 * DateTimeUtil.PATTERN_DATETIME));
			 */
			// 6.组装返回信息
			retRemotecallLog.setEndtime(new Date());
			retRemotecallLog.setRetcode(status);
			retRemotecallLog.setRetmsg(retmsg);
			copyBossBaseRespInfo2callLog(responseStr, retRemotecallLog);

			// 7.释放连接
			postMethod.releaseConnection();
		}

		return retRemotecallLog;
	}

	/**
	 * 生成请求流水号
	 * 
	 * @param clientcode
	 * @return
	 */
	public static String createRequestid(String clientcode) {
		/* 交易流水号；clientcode+YYYYMMDD+8位流水 */
		String currentTime = DateTimeUtil.formatDate(new Date(), DateTimeUtil.PATTERN_DATE_COMPACT);
		return clientcode + currentTime + UUID.randomUUID().toString().split("-")[0];
	}
	
	/**
	 * 用于番禺发送短信时使用的生成流水号
	 * @return
	 */
	public static String createRequestid(){
		String currentTime = DateTimeUtil.formatDate(new Date(), DateTimeUtil.PATTERN_DATETIME_COMPACT);
		return currentTime;
	}

	/**
	 * 将请参数保存到RemotecallLog对象
	 * 
	 * @param postMethod
	 * @param retRemotecallLog
	 * @throws Exception
	 */
	private static void copyBossReqInfo2callLog(PostMethod postMethod, RemotecallLog remotecallLog) throws Exception {

		CheckUtils.checkNull(remotecallLog, "登记请日志:日志对象不能为空");

		if (null == postMethod) {
			return;
		}

		// NameValuePair version= postMethod.getParameter("version");
		// NameValuePair clientcode= postMethod.getParameter("clientcode");
		// NameValuePair clientpwd= postMethod.getParameter("clientpwd");
		NameValuePair citycode = postMethod.getParameter("citycode");
		NameValuePair servicecode = postMethod.getParameter("servicecode");
		NameValuePair requestid = postMethod.getParameter("requestid");
		NameValuePair requestContent = postMethod.getParameter("requestContent");

		String serviceurl = postMethod.getURI().getURI();

		remotecallLog.setServiceurl(serviceurl);
		remotecallLog.setProtocol("http");
		remotecallLog.setClientcode(getNameValuePairValue(citycode));
		remotecallLog.setServicecode(getNameValuePairValue(servicecode));
		remotecallLog.setRequest(getNameValuePairValue(requestContent));
		remotecallLog.setRequestid(getNameValuePairValue(requestid));
	}

	private static String getNameValuePairValue(NameValuePair nameValuePair) {
		if (null == nameValuePair) {
			return null;
		}

		return nameValuePair.getValue();
	}

	/**
	 * 将boss接口返回的公共数据保存至RemotecallLog对象
	 * 
	 * @param remotecallLog
	 * @throws Exception
	 */
	private static void copyBossBaseRespInfo2callLog(String respJsonStr, RemotecallLog remotecallLog) throws Exception {

		remotecallLog.setResponse(respJsonStr);
		BossHttpBaseRespBO bossBaseResp = getBossHttpBaseResp(respJsonStr);

		if (null != bossBaseResp) {
			if (StringUtils.isNotBlank(bossBaseResp.getStatus())) {
				remotecallLog.setRetcode(bossBaseResp.getStatus());
			}

			if (StringUtils.isNotBlank(bossBaseResp.getMessage())) {
				remotecallLog.setRetmsg(bossBaseResp.getMessage());
			}

			if (null != bossBaseResp.getOutput()) {
				remotecallLog.setResporderid(bossBaseResp.getOutput().getOrderid());
				remotecallLog.setRespserialno(bossBaseResp.getOutput().getSerialno());
			}
		}

	}

	/**
	 * 解析boss接口返回的公共数据
	 * 
	 * @param jsonStr
	 * @return
	 */
	private static BossHttpBaseRespBO getBossHttpBaseResp(String jsonStr) {

		BossHttpBaseRespBO RetBossRespBO = new BossHttpBaseRespBO();
		if (StringUtils.isBlank(jsonStr)) {
			return null;
		}

		String respserialno = null;
		try {

			// JsonNode nodeTree = objectMapper.readTree(jsonStr);
			// String outputStr = nodeTree.get("output").toString();
			// RetBossRespBO = (BossHttpBaseRespBO) BeanUtil.jsonToObject(
			// outputStr, BossHttpBaseRespBO.class);

			RetBossRespBO = (BossHttpBaseRespBO) BeanUtil.jsonToObject(jsonStr, BossHttpBaseRespBO.class);

		} catch (Exception e) {
			System.out.print(e.getStackTrace());
		}

		return RetBossRespBO;
	}

}
