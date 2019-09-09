package com.maywide.core.security.remote;

import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.jsonplugin.JSONException;
import com.googlecode.jsonplugin.JSONUtil;
import com.maywide.biz.core.entity.RemotecallLog;
import com.maywide.core.util.CheckUtils;

public class McrHttpClientImpl {

	private static Logger log = LoggerFactory.getLogger(McrHttpClientImpl.class);

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
		copyReqInfo2callLog(postMethod, retRemotecallLog);

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
				log.info("=========>" + postMethod.getParameter("servicecode") + " responseStr="
						+ responseStr);

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
			copyBaseRespInfo2callLog(responseStr, retRemotecallLog);

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
			copyBaseRespInfo2callLog(responseStr, retRemotecallLog);

			// 7.释放连接
			postMethod.releaseConnection();
		}

		return retRemotecallLog;
	}

	private static void copyBaseRespInfo2callLog(String respJsonStr, RemotecallLog remotecallLog) {
		remotecallLog.setResponse(respJsonStr);
		if (StringUtils.isBlank(respJsonStr)) return;
		try {
			JSONObject jsonObject = new JSONObject(respJsonStr);
			String code = jsonObject.optString("errcode");
			String msg = jsonObject.optString("errmsg");
			if (StringUtils.isNotBlank(code)) {
				remotecallLog.setRetcode(code);
			}
			if (StringUtils.isNotBlank(msg)) {
				remotecallLog.setRetmsg(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void copyReqInfo2callLog(PostMethod postMethod, RemotecallLog remotecallLog)
			throws URIException, JSONException {
		String serviceurl = postMethod.getURI().getURI();
		remotecallLog.setServiceurl(serviceurl);

		remotecallLog.setProtocol("http");

		String request = JSONUtil.serialize(postMethod.getParameters());
		remotecallLog.setRequest(request);
	}

}
