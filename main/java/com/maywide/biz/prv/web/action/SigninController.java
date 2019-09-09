package com.maywide.biz.prv.web.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.maywide.biz.core.filter.SecurityFilter;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.prv.bo.LoginParam;
import com.maywide.biz.prv.service.LoginService;
import com.maywide.core.web.SimpleController;

/**
 * 登录处理
 */
@Results({
	@Result(name = "success", type = "redirect", location="/layout"),
	@Result(name = "login", type = "redirect", location="/pub/signin")
})
public class SigninController extends SimpleController {
    @Autowired
    private LoginService loginService;
    
    public String login() {
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String loginname = getRequiredParameter("j_username");
    	String password = getRequiredParameter("j_password");
    	Long deptid = new Long(getRequiredParameter("j_deptid"));
    	
    	LoginInfo loginInfo = null;
    	try {
    		LoginParam loginParam = new LoginParam();
    		loginParam.setLoginname(loginname);
    		loginParam.setPassword(password);
    		loginParam.setDeptid(deptid);
    		
    		//--TODO
    		//先检查操作员
    		//再取操作员部门，如果只有一个部门，则直接创建登录信息，否则返回选择操作员部门页面
    		
    		loginInfo = loginService.login(loginParam,null);
    		
    		request.getSession().setAttribute(SecurityFilter.LOGIN_INFO, loginInfo);
    		request.getSession().removeAttribute("j_username");
    		return "success";
		} catch (Exception e) {
		    e.printStackTrace();
			request.getSession().setAttribute("msg", e.getMessage());
			request.getSession().setAttribute("j_username", loginname);
			return "login";
		}
    }
    
    public String logout() {
    	HttpServletRequest request = ServletActionContext.getRequest();
    	request.getSession().removeAttribute(SecurityFilter.LOGIN_INFO);
    	return "login";
    }
}
