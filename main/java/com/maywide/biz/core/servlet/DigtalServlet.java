package com.maywide.biz.core.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.googlecode.jsonplugin.JSONUtil;
import com.maywide.biz.core.pojo.ResponeBean;
import com.maywide.biz.inter.service.ExternalService;
import com.maywide.core.context.SpringContextHolder;


public class DigtalServlet extends HttpServlet {
	
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private static final String ENCODE_TYPE = "UTF-8";
	
	private static final Logger logger = LoggerFactory.getLogger(DigtalServlet.class);

	private ExternalService exteranlService;
	
	@Override
	public void init() throws ServletException {
		super.init();
		exteranlService = (ExternalService)SpringContextHolder.getBean(ExternalService.class);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		request.setCharacterEncoding(ENCODE_TYPE);
		doExcect(request, response);
	}
	
	private void doExcect(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		ResponeBean bean = new ResponeBean();
		bean.setSuccess("success");
		
		String city = request.getParameter("city");
		String num = request.getParameter("num");
		String gcode = request.getParameter("gcode");
		
		if(StringUtils.isBlank(city)){
			bean.setErrorMsg("city字段不能为空");
		}
		
		if(StringUtils.isBlank(num)){
			bean.setErrorMsg("num字段不能为空");
		}
		
		if(StringUtils.isBlank(gcode)){
			bean.setErrorMsg("gcode字段不能为空");
		}
		try{
			
			String datas = exteranlService.queryGridManager(gcode, city, num);
			bean.setResult(datas);
		}catch(Exception e){
			bean.setErrorMsg(e.getMessage());
		}
		try{
			doPutOut(response, new Gson().toJson(bean));
		}catch(Exception e){
			
		}
	}

	
	private void doPutOut(HttpServletResponse response,String result) throws IOException{
		response.setCharacterEncoding(ENCODE_TYPE);
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		PrintWriter writer = response.getWriter();
		writer.print(result);
	}
	
}
