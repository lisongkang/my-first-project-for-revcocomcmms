package com.maywide.core.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.maywide.biz.cons.NetWorkConstant;

public class RequestUtils {
	
	private static Logger log = LoggerFactory.getLogger(RequestUtils.class);
	
	private RequestUtils() {}
	
	public static HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	
	public static String getRemoteAddr() {
		 HttpServletRequest request = getRequest();
		 return getRemoteAddr(request);
	}
	
	public static String getRemoteAddr(HttpServletRequest request) {
		String ipAddress = request.getHeader(NetWorkConstant.X_REAL_IP);
		if (StringUtils.isEmpty(ipAddress) || NetWorkConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader(NetWorkConstant.X_FORWARDED_FOR);
		}
		if (StringUtils.isEmpty(ipAddress) || NetWorkConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader(NetWorkConstant.PROXY_CLIENT_IP);
		}
		if (StringUtils.isEmpty(ipAddress) || NetWorkConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader(NetWorkConstant.WL_PROXY_CLIENT_IP);
		}
		if (StringUtils.isEmpty(ipAddress) || NetWorkConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader(NetWorkConstant.HTTP_CLIENT_IP);
		}
		if (StringUtils.isEmpty(ipAddress) || NetWorkConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader(NetWorkConstant.HTTP_X_FORWARDED_FOR);
		}
		if (StringUtils.isEmpty(ipAddress) || NetWorkConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
		}
		if (StringUtils.isEmpty(ipAddress) || NetWorkConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (NetWorkConstant.LOCALHOST_IP.equals(ipAddress) || NetWorkConstant.LOCALHOST_IP_16.equals(ipAddress)) {
				//根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					log.error("获取IP地址, 出现异常={}", e.getMessage(), e);
				}
				assert inet != null;
				ipAddress = inet.getHostAddress();
			}
			log.info("获取IP地址 ipAddress={}", ipAddress);
		}
		// 对于通过多个代理的情况, 第一个IP为客户端真实IP,多个IP按照','分割 //"***.***.***.***".length() = 15
		if (ipAddress != null && ipAddress.length() > NetWorkConstant.MAX_IP_LENGTH) {
			if (ipAddress.indexOf(NetWorkConstant.Symbol.COMMA) > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(NetWorkConstant.Symbol.COMMA));
			}
		}
		return ipAddress;
	}

}
