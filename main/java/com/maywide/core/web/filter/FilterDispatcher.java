package com.maywide.core.web.filter;

/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

public class FilterDispatcher extends StrutsPrepareAndExecuteFilter {
	private static final String EXCLUDE_SUFFIXS_NAME = "excludeSuffixs";
	private static final String[] DEFAULT_EXCLUDE_SUFFIXS = { "services" };
	private static final String[] FILE_EXCLUDE_SUFFIXS = { ".js", ".css",
			".jpg", ".gif" };
	private static String[] excludeSuffixs = null;

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		if (shouldNotFilter((HttpServletRequest) req))
			chain.doFilter(req, res);
		else
			super.doFilter(req, res, chain);
	}

	protected Dispatcher createDispatcher(FilterConfig filterConfig) {
		Map params = new HashMap();
		for (Enumeration e = filterConfig.getInitParameterNames(); e
				.hasMoreElements();) {
			String name = (String) e.nextElement();
			String value = filterConfig.getInitParameter(name);
			params.put(name, value);
		}
		return new StrutsDispatcher(filterConfig.getServletContext(), params);
	}

	protected boolean shouldNotFilter(HttpServletRequest request)
			throws ServletException {
		String path = request.getServletPath();

		for (String suffix : FILE_EXCLUDE_SUFFIXS) {
			if (path.endsWith(suffix)) {
				return true;
			}
		}
		for (String suffix : excludeSuffixs) {
			if (path.indexOf(suffix) > -1) {
				return true;
			}
		}
		return false;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		String excludeSuffixStr = filterConfig
				.getInitParameter("excludeSuffixs");

		if (StringUtils.isNotBlank(excludeSuffixStr)) {
			excludeSuffixs = excludeSuffixStr.split(",");
			for (int i = 0; i < excludeSuffixs.length; ++i)
				excludeSuffixs[i] = "/" + excludeSuffixs[i];
		} else {
			excludeSuffixs = DEFAULT_EXCLUDE_SUFFIXS;
		}
		super.init(filterConfig);
	}
}