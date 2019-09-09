package com.maywide.biz.core.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.RequestUtils;

public class SecurityFilter implements Filter {
	protected final Logger log = LoggerFactory.getLogger(getClass());
	private static List<String> arrPathList = null;// 允许通过的路径数组
	private static List<String> arrFileList = null;// 允许通过的路径数组
	private static AntPathMatcher urlMatcher = new AntPathMatcher();
	public final static String LOGIN_INFO = "LOGIN_INFO";
	public final static String TONKE_LOGIN_INFO = "TONKE_LOGIN_INFO";

	private static final String XHR_OBJECT_NAME = "XMLHttpRequest";
    private static final String HEADER_REQUEST_WITH = "x-requested-with";

	public void init(FilterConfig filterConfig) throws ServletException {
		arrPathList = new ArrayList<String>();

		String allPath = filterConfig.getInitParameter("ALLOWPATH");
		String tempArr[] = allPath.split(";");
		for (int i = 0; i < tempArr.length; i++) {
			arrPathList.add(tempArr[i]);
		}

		arrFileList = new ArrayList<String>();
		allPath = filterConfig.getInitParameter("ALLOWFILE");
		tempArr = allPath.split(";");
		for (int i = 0; i < tempArr.length; i++) {
			arrFileList.add(tempArr[i]);
		}
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		
//		if (loginInfo == null) {
			/*if (!checkUserPriv(request)) {
				LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute("login_info");
				String ssoContext = PropertyUtil.getValueFromProperites(
						BizConstant.System.SysConfig, "sso_context");
				
				String loginname = SingleSignOn.getUser(request);
				String deptidstr = SingleSignOn.getDepartment(request);
				
				//String loginname = "GZCYKFA002";
				//String deptidstr = "1";
				
				if (StringUtils.isBlank(loginname)) {
					if (XHR_OBJECT_NAME.equals(request.getHeader(HEADER_REQUEST_WITH))) {
						throw new ServletException("用户登录已过期，请重新登录");
					}
					//response.sendRedirect(ssoContext + "/pages/login/login.jsp");
					response.sendRedirect(request.getContextPath() + "/pub/signin");
					return;
				}
				
				Long deptid = (StringUtils.isBlank(deptidstr)) ? 0 : new Long(deptidstr);
				
				BaseService baseService = SpringContextHolder.getBean(BaseService.class);
				
				try {
					loginInfo = baseService.createLoginInfo(loginname, deptid);
					AuthContextHolder.setLoginInfo(loginInfo);
				} catch (Exception e) {
					log.error("创建登录用户错误", e);
				}
				
				request.getSession().setAttribute("login_info", loginInfo);
				
				SingleSignOn.signIn(request, response, loginname, deptidstr);
			}else{
				LoginInfo loginInfo = (LoginInfo) request.getSession()
						.getAttribute(LOGIN_INFO);
				AuthContextHolder.setLoginInfo(loginInfo);
			}*/
		/*} else {
			AuthContextHolder.setLoginInfo(loginInfo);
		}*/
		

		if (!checkUserPriv(request)) {
			LoginInfo loginInfo = (LoginInfo) request.getSession()
					.getAttribute(LOGIN_INFO);
			
			if (loginInfo == null) {
				if (XHR_OBJECT_NAME.equals(request.getHeader(HEADER_REQUEST_WITH))) {
					throw new ServletException("用户登录已过期，请重新登录");
				}
				
				AuthContextHolder.setLoginInfo(null);
				response.sendRedirect(request.getContextPath() + "/pub/signin");
				return;
			} else {
				AuthContextHolder.setLoginInfo(loginInfo);
			}
		} else {
			// 如果是api路径，则直接从session中取登录信息，但不进行登录跳转--TODO
			LoginInfo loginInfo = (LoginInfo) request.getSession()
					.getAttribute(LOGIN_INFO);
			AuthContextHolder.setLoginInfo(loginInfo);
			String ipAddr = RequestUtils.getRemoteAddr(request);
			AuthContextHolder.setUserIpAddr(ipAddr);
			Enumeration<String> headerNames = request.getHeaderNames(); 
			while(headerNames.hasMoreElements()) {
				String key = headerNames.nextElement();
				String value = request.getHeader(key);
				System.out.println("key:"+key+";value:"+value);
			}
		}

		filterChain.doFilter(servletRequest, servletResponse);

	}

	/**
	 * 校验用户是否拥有权限访问 对于指定的路径或文件不进行校验，直接通过
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return boolean
	 * @throws ServletException
	 */
	private static boolean checkUserPriv(HttpServletRequest request)
			throws ServletException {
		String curPath = request.getRequestURI(); // 得到当前请求的路径
		String curPath2 = request.getServletPath();
		String allowPath = ""; // 允许通过的路径变量

		// 不需进行校验的文件
		for (int i = 0; i < arrFileList.size(); i++) {
			allowPath = (String) arrFileList.get(i);
			if (curPath.endsWith(allowPath)) {
				return true;
			}
		}
		// 不需进行校验的路径
		curPath = request.getServletPath();
		for (int i = 0; i < arrPathList.size(); i++) {
			allowPath = (String) arrPathList.get(i);
			if (urlMatcher.match(allowPath, curPath)) {
				return true;
			}
		}

		return false;
	}

	public void destroy() {

	}

	// moss/assets/plugins/font-awesome/css/font-awesome.min.css

	public static void main(String[] args) {
		String path1 = "/assets/plugins/font-awesome/css/font-awesome.min.css";
		String path2 = path1.substring(path1.indexOf("/"), path1.length());
		System.out.println(path2);
		System.out.println(urlMatcher.match("/assets/**", path1));
	}
}
