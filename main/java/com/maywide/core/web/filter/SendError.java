package com.maywide.core.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendError implements Filter {
	protected final Logger log = LoggerFactory.getLogger(getClass());
	private ServletContext servletContext =null;

	public void init(FilterConfig filterConfig) throws ServletException {
		servletContext = filterConfig.getServletContext();
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		try {
			filterChain.doFilter(servletRequest, servletResponse);
		} catch (Exception e) {
			this.sendError(request, response, servletContext, 500, e);
		}

	}

	public void sendError(HttpServletRequest request,
			HttpServletResponse response, ServletContext ctx, int code,
			Exception e) {

		try {
			if (code == 500) {
				request.setAttribute("javax.servlet.error.exception", e);

				request.setAttribute("javax.servlet.jsp.jspException", e);
			}

			if (response.getClass().getName().toLowerCase().indexOf("weblogic") > -1) {
				String characterEncoding = request.getParameterMap()
						.get("encoding")[0];

				characterEncoding = ((characterEncoding == null) || (characterEncoding
						.equals(""))) ? "utf-8" : characterEncoding;

				response.reset();
				response.resetBuffer();
				response.setContentType("text/html;charset="
						+ characterEncoding);
				response.setCharacterEncoding(characterEncoding);
				response.setStatus(500);
				response.getOutputStream().write(
						ExceptionBuilder.build(e).getBytes());
				response.flushBuffer();
			} else {
				response.sendError(code, e.getMessage());
			}
		} catch (Exception e1) {
		}

	}
	
	public void destroy() {

	}

}
