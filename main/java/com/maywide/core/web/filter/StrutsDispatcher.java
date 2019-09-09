package com.maywide.core.web.filter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.views.freemarker.FreemarkerManager;

import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.location.Location;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class StrutsDispatcher extends Dispatcher {
	private boolean devMode;
	private Map<String, String> initialParams = null;

	private static String DEFUALT_ENCODING = "UTF-8";

	@Inject("struts.devMode")
	public void setDevMode(String mode) {
		this.devMode = "true".equals(mode);
	}

	public StrutsDispatcher(ServletContext servletContext,
			Map<String, String> initParams) {
		super(servletContext, initParams);
		this.initialParams = initParams;
	}

	public void sendError(HttpServletRequest request,
			HttpServletResponse response, ServletContext ctx, int code,
			Exception e) {
		if (this.devMode) {
			response.setContentType("text/html");
			try {
				FreemarkerManager mgr = (FreemarkerManager) getContainer()
						.getInstance(FreemarkerManager.class);

				Configuration config = mgr.getConfiguration(ctx);

				Template template = config
						.getTemplate("/org/apache/struts2/dispatcher/error.ftl");

				List chain = new ArrayList();
				Throwable cur = e;
				chain.add(cur);
				while ((cur = cur.getCause()) != null) {
					chain.add(cur);
				}

				HashMap data = new HashMap();
				data.put("exception", e);
				data.put("unknown", Location.UNKNOWN);
				data.put("chain", chain);
				data.put("locator", new Dispatcher.Locator());
				template.process(data, response.getWriter());
				response.getWriter().close();
			} catch (Exception exp) {
				try {
					response.sendError(code, "Unable to show problem report: "
							+ exp);
				} catch (IOException ex) {
				}
			}
		} else {
			try {
				if (code == 500) {
					request.setAttribute("javax.servlet.error.exception", e);

					request.setAttribute("javax.servlet.jsp.jspException", e);
				}

				if (response.getClass().getName().toLowerCase()
						.indexOf("weblogic") > -1) {
					String characterEncoding = (String) this.initialParams
							.get("encoding");

					characterEncoding = ((characterEncoding == null) || (characterEncoding
							.equals(""))) ? DEFUALT_ENCODING
							: characterEncoding;

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
	}
}