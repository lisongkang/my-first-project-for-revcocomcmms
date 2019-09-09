package com.maywide.common.pub.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.ParamSettingService;
import com.maywide.biz.prv.service.PrvMenuService;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.service.PropertiesConfigService;
import com.maywide.core.util.PropertyUtil;
import com.maywide.core.web.SimpleController;
import com.maywide.core.web.captcha.ImageCaptchaServlet;
import com.maywide.core.web.view.OperationResult;
import com.maywide.common.ctx.DynamicConfigService;
import com.maywide.common.ctx.MailService;
import com.maywide.common.sys.vo.NavMenuVO;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.jasig.cas.client.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;

/**
 * 登录处理
 */
public class SigninController extends SimpleController {

    @Autowired
    private MailService mailService;
    
    @Autowired
    private ParamSettingService paramSettingService;
    
    @Autowired
    private PrvMenuService menuService;

    public HttpHeaders index() {
        return new DefaultHttpHeaders("/pub/signin").disableCaching();
    }

    public boolean isDevMode() {
        return PropertiesConfigService.isDevMode();
    }

    public boolean isDemoMode() {
        return PropertiesConfigService.isDemoMode();
    }

    public String getSystemTitle() {
    	String systemTitle = PropertyUtil.getValueFromProperites("sysconfig", "system.title");
        return StringUtils.isBlank(systemTitle) ? "" : systemTitle;
    }

    public boolean isSignupEnabled() {
    	String signupEnabled = PropertyUtil.getValueFromProperites("sysconfig", "signup.enabled");
        return StringUtils.isBlank(signupEnabled) ? true : Boolean.valueOf(signupEnabled).booleanValue();
    }

    public boolean isCasSupport() {
        return casAuthenticationEntryPoint != null;
    }

    public boolean isMailServiceEnabled() {
        return mailService.isEnabled();
    }

    @Autowired(required = false)
    private CasAuthenticationEntryPoint casAuthenticationEntryPoint;

    public String getCasRedirectUrl() {
        HttpServletRequest request = ServletActionContext.getRequest();
        final StringBuilder buffer = new StringBuilder();
        buffer.append(request.isSecure() ? "https://" : "http://");
        buffer.append(request.getServerName());
        buffer.append(request.getServerPort() == 80 ? "" : ":" + request.getServerPort());
        buffer.append(request.getContextPath());
        buffer.append("/j_spring_cas_security_check");

        final String urlEncodedService = ServletActionContext.getResponse().encodeURL(buffer.toString());
        final String redirectUrl = CommonUtils.constructRedirectUrl(casAuthenticationEntryPoint.getLoginUrl(),
                casAuthenticationEntryPoint.getServiceProperties().getServiceParameter(), urlEncodedService,
                casAuthenticationEntryPoint.getServiceProperties().isSendRenew(), false);
        return redirectUrl;
    }

    @MetaData("请求找回密码")
    public HttpHeaders forget() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String uid = getRequiredParameter("uid");
        String jCaptcha = getRequiredParameter("j_captcha");
        if (!ImageCaptchaServlet.validateResponse(request, jCaptcha)) {
            setModel(OperationResult.buildFailureResult("验证码不正确，请重新输入"));
        } else {
            
        }
        return buildDefaultHttpHeaders();
    }

    @MetaData("重置密码")
    public HttpHeaders resetpwd() {
        
        return buildDefaultHttpHeaders();
    }

    @MetaData("会话过期")
    public HttpHeaders expired() {
        return buildDefaultHttpHeaders("expired");
    }
    
    public HttpHeaders enums() {
        ServletContext sc = ServletActionContext.getServletContext();
        setModel(sc.getAttribute("enums"));
        return buildDefaultHttpHeaders();
    }
}
