package com.maywide.biz.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 允许跨域的过滤器，为了解决h5的跨域请求问题
 * @author zyq
 * 
 */
public class CorsFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletResponse res = (HttpServletResponse) response;
		res.setContentType("text/html;charset=UTF-8");
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods",
				"POST, GET, OPTIONS, DELETE");
		res.setHeader("Access-Control-Max-Age", "0");
		res.setHeader(
				"Access-Control-Allow-Headers",
				"Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
		res.setHeader("Access-Control-Allow-Credentials", "true");
		res.setHeader("XDomainRequestAllowed", "1");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}