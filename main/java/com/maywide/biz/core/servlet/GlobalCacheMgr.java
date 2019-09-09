package com.maywide.biz.core.servlet;


import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.maywide.biz.core.pojo.LoginUser;

public final class GlobalCacheMgr {
	private static Logger log = LoggerFactory.getLogger(GlobalCacheMgr.class);
	private final static String LOGIN_USER = "LOGIN_USER";
	private static ThreadLocal<HttpSession> localSession = new ThreadLocal<HttpSession>();
	
    public static LoginUser getLoginUser() {
    	return (LoginUser) getSession().getAttribute(LOGIN_USER);
    }
    
    public static void setLoginUser(LoginUser loginUser) {
    	getSession().setAttribute(LOGIN_USER, loginUser);
    }
    
    public static HttpSession getSession() {
    	return localSession.get();
    }
    
    public static void setSession(HttpSession httpSession) {
    	localSession.set(httpSession);
    }
}
