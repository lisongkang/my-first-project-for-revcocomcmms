package com.maywide.biz.core.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.maywide.biz.core.entity.AccessLog;
import com.maywide.biz.core.pojo.LoginUser;
import com.maywide.biz.core.pojo.RemoteCallException;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.core.context.SpringContextHolder;
import com.maywide.core.service.PersistentService;

/**
 * 客户端终止连接,释放session信息/运行监控信息
 */
public class UninitServlet extends HttpServlet {
    private static final long serialVersionUID = 3194824403376875804L;
    private static Logger log = LoggerFactory.getLogger(UninitServlet.class);
    private PersistentService baseService = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        // 初始化Spring
        SpringBeanUtil.setWebCTX(config.getServletContext());
        baseService = (PersistentService) SpringContextHolder.getBean(PersistentService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReturnInfo returnInfo = new ReturnInfo();
        AccessLog accessLog = new AccessLog();

        try {
            // 初始化日志记录
            accessLog.setClientIP(request.getRemoteHost());
            accessLog.setCallMethod("/uninit");
            accessLog.setCallTime(new Date());
            accessLog.setRequest("");
            accessLog.setResponse("");

            HttpSession session = request.getSession();

            // 1.获取SESSION中的操作员登录信息,如果没有登录则返回
            LoginUser loginUser = GlobalCacheMgr.getLoginUser();
            if (null == loginUser) {
                throw new RemoteCallException(
                    IErrorDefConstant.ERROR_INVALID_LOGIN_MSG,
                    IErrorDefConstant.ERROR_INVALID_LOGIN_CODE
                );
            }

            // 3.主动销毁SESSION对象
            session.invalidate();

            returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
            returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
        } catch (RemoteCallException Err) {
            log.debug("uninitServlet::doPost", Err);
            returnInfo.setCode(Err.getErrorCode());
            returnInfo.setMessage(Err.getMessage());
        } finally {
            // 记录访问日志
            accessLog.setReturnCode(returnInfo.getCode());
            accessLog.setReturnMsg(returnInfo.getMessage());
            accessLog.setEndTime(new Date());
            
            try {
            	baseService.save(accessLog);
			} catch (Exception e) {
				// TODO: handle exception
			}
        }
    }
}
