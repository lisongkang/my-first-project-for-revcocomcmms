/**
 * Copyright (c) 2012
 */
package com.maywide.core.security;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.maywide.biz.core.filter.SecurityFilter;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.servlet.GlobalCacheMgr;

/**
 * 以ThreadLocal方式实现Web端登录信息传递到业务层的存取
 */
public class AuthContextHolder {

    public static final String SPRING_SECURITY_LAST_USERNAME_KEY = "SPRING_SECURITY_LAST_USERNAME";

    public static final String DEFAULT_UNKNOWN_PIN = "N/A";
    
    private static ThreadLocal<LoginInfo> localLoginInfo = new ThreadLocal<LoginInfo>();
    private static ThreadLocal<String> userIpAddr = new ThreadLocal<String>();
    private static final boolean IS_LOCAL_TEST_MODE = false;

    /**
     * 获取用户唯一登录标识
     */
    public static String getAuthUserPin() {
        String pin = DEFAULT_UNKNOWN_PIN;
        AuthUserDetails authUserDetails = getAuthUserDetails();
        if (authUserDetails != null && authUserDetails.getUsername() != null) {
            pin = authUserDetails.getUsername();
        }
        return pin;
    }

    /**
     * 基于Spring Security获取用户认证信息
     */
    public static AuthUserDetails getAuthUserDetails() {
        AuthUserDetails userDetails = null;
        if (SecurityContextHolder.getContext() == null
                || SecurityContextHolder.getContext().getAuthentication() == null
                || SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null) {
            return null;
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AuthUserDetails) {
            userDetails = (AuthUserDetails) principal;
        }
        Assert.notNull(userDetails);
        return userDetails;
    }

    public static boolean isAdminUser() {
        return getAuthUserDetails().isAdminUser();
    }

    /**
     * 获取用户ACL CODE
     */
    public static String getAclCode() {
        return getAuthUserDetails().getAclCode();
    }

    /**
     * 获取用户ACL CODE
     */
    public static String getAclCodePrefix() {
        String aclCode = getAclCode();
        if (StringUtils.isBlank(aclCode)) {
            return "";
        }
        Collection<String> aclCodePrefixs = getAclCodePrefixs();
        if (CollectionUtils.isEmpty(aclCodePrefixs)) {
            return "";
        }
        for (String aclCodePrefix : aclCodePrefixs) {
            if (aclCode.startsWith(aclCodePrefix)) {
                return aclCodePrefix;
            }
        }
        throw new IllegalStateException("ACL前缀计算异常");
    }

    public static Collection<String> getAclCodePrefixs() {
        return getAuthUserDetails().getAclCodePrefixs();
    }
    
    public static void setLoginInfo(LoginInfo loginInfo) {
    	localLoginInfo.set(loginInfo);
    }
    
    public static void setUserIpAddr(String ipAddr) {
    	userIpAddr.set(ipAddr);
    }
    
    public static LoginInfo getLoginInfo() {
		if(IS_LOCAL_TEST_MODE){
			return getLoginInfo4LocalTest();
		}
    	return localLoginInfo.get();
    }
    
    public static String getUserIpAddr() {
    	return userIpAddr.get();
    }
    
    private static LoginInfo  getLoginInfo4LocalTest(){
    	// //------------------测试 bengin -----------------------
    	//LoginInfo loginInfo = new LoginInfo();
		//loginInfo.setOperid(1L);
		//loginInfo.setDeptid(1002L);
		//loginInfo.setAreaid(100L);
		//loginInfo.setCity("GZ");
		// //------------------测试 bengin -----------------------
    	
    	HttpSession s = GlobalCacheMgr.getSession();
    	LoginInfo loginInfo = (LoginInfo) s.getAttribute(SecurityFilter.LOGIN_INFO);
		
		return loginInfo;
    }
}
