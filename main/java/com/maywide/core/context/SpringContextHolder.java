package com.maywide.core.context;

import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import com.maywide.core.service.PersistentService;

/**
 * Spring上下文容器
 */
public class SpringContextHolder {

    private static ApplicationContext applicationContext;
    
    private static ApplicationContext baseApplicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextHolder.applicationContext = applicationContext;
    }
    
    public static PersistentService getBasePersistentService() {
    	return (PersistentService) baseApplicationContext.getBean("persistentService");
    }

	public static void setBaseApplicationContext(
			ApplicationContext baseApplicationContext) {
		SpringContextHolder.baseApplicationContext = baseApplicationContext;
	}
    
    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> requiredType) {
        Assert.notNull(applicationContext);
        return applicationContext.getBean(requiredType);
    }
}
