package com.maywide.biz.core.servlet;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 负责根据SESSION释放全局监控数据
 */
public class SessionListener implements HttpSessionListener {
    /**
     * 暂时没用任何处理
     * @param httpSessionEvent 关联的会话对象
     */
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    }

    /**
     * 释放当前session所关联的客户端监控数据
     * @param httpSessionEvent 关联的会话对象
     */
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        
    }
}
