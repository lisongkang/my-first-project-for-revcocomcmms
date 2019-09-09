package com.maywide.biz.core.servlet;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.servlet.ServletContext;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.maywide.core.service.PersistentService;

public class SpringBeanUtil {
    private static WebApplicationContext webCTX = null;
    
    private static ClassPathXmlApplicationContext context = null;
    
    private static ClassPathXmlApplicationContext dbcontext = null;
    
    private static ClassPathXmlApplicationContext portalcontext = null;
    
    private static Executor threadExcutor = Executors.newCachedThreadPool();

    //小额工程第三方公司数据库

	private static ClassPathXmlApplicationContext nccontext = null;
	//billing数据库
	private static ClassPathXmlApplicationContext billingcontext = null;

	//ods第三方数据库
	private static ClassPathXmlApplicationContext odsContext = null;

    public static Object getBean(final String beanName) {
    	return webCTX.getBean(beanName);
    }

    public static void setWebCTX(ServletContext servletContext) {
    	if (SpringBeanUtil.webCTX == null) {
    		SpringBeanUtil.webCTX = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
    	}
    }
    
    public static void setContext(ClassPathXmlApplicationContext context) {
		SpringBeanUtil.context = context;
	}
	
	public static PersistentService getPersistentService() throws Exception {
		if (context == null) throw new Exception("BI数据源配置错误");
		return (PersistentService) context.getBean("persistentService");
	}
	
	public static void setDBContext(ClassPathXmlApplicationContext context){
		SpringBeanUtil.dbcontext = context;
	}
	
	public static PersistentService getDBPersistentService() throws Exception{
		if (dbcontext == null) throw new Exception("报表数据源配置错误");
		return (PersistentService) dbcontext.getBean("persistentService");
	}
	
	public static void setPortalContext(ClassPathXmlApplicationContext context){
		SpringBeanUtil.portalcontext = context;
	}
	
	public static PersistentService getPortalPersistentService() throws Exception{
		if (portalcontext == null) throw new Exception("portal数据源配置错误");
		return (PersistentService) portalcontext.getBean("persistentService");
	}

	public static void setNccontext(ClassPathXmlApplicationContext nccontext) {
		SpringBeanUtil.nccontext = nccontext;
	}

	public static PersistentService getNcPersistentService() throws Exception {
		if (nccontext == null) throw new Exception("NC数据源配置错误");
		return (PersistentService) nccontext.getBean("persistentService");
	}

	public static void setBillingcontext(ClassPathXmlApplicationContext billingcontext) {
		SpringBeanUtil.billingcontext = billingcontext;
	}

	public static PersistentService getBillingPersistentService() throws Exception {
		if (billingcontext == null) throw new Exception("BILLING数据源配置错误");
		return (PersistentService) billingcontext.getBean("persistentService");
	}
	public static Executor getThreadExcutor() {
		return threadExcutor;
	}


	public static void setOdsContext(ClassPathXmlApplicationContext odsContext) {
		SpringBeanUtil.odsContext = odsContext;
	}

	public static PersistentService getOdsContext() throws Exception {
    	if (odsContext == null) throw new Exception("ods数据源配置错误");
    	return (PersistentService) odsContext.getBean("persistentService");
	}
}
